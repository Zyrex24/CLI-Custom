package org.os.main;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import org.os.commands.*;
import org.os.interfaces.Command;
import java.io.*;

public class CliFrame {
    private static Map<String, Command> commandRegistry = new HashMap<>();

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Our CLI :) ! Type 'help' to see available commands or type 'exit' to quit.");
        registerCommands();

        while (true) {
            String currentDirectory = System.getProperty("user.dir");
            System.out.print(currentDirectory + ">");
            String input = userInput.nextLine().trim();
            if (input.isEmpty()) continue;

            // Check for redirection operators
            boolean append = input.contains(">>");
            boolean overwrite = input.contains(">");
            String filePath = null;
            if (append || overwrite) {
                String[] parts = append ? input.split(">>") : input.split(">");
                input = parts[0].trim();
                filePath = parts[1].trim();
            }

            // Parse the command and arguments
            String[][] parsedInput = ChangeInput(input);
            String command = String.join(" ", parsedInput[0]);
            String[] arguments = parsedInput[1];

            // Execute command and capture output if redirection is specified
            if (commandRegistry.containsKey(command)) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                PrintStream originalOut = System.out;
                try {
                    System.setOut(printStream);  // Temporarily redirect System.out
                    commandRegistry.get(command).execute(arguments);
                    System.out.flush();
                    String output = outputStream.toString();  // Capture the command output

                    // Write output to file if redirection is specified
                    if (filePath != null) {
                        writeToFile(output, filePath, append);
                        System.setOut(originalOut);
                        System.out.println("Content written to " + filePath);
                    } else {
                        System.setOut(originalOut);
                        System.out.print(output);  // Print output to console if no redirection
                    }
                } finally {
                    System.setOut(originalOut);  // Reset System.out
                }
            } else {
                System.out.println("Error: This command is invalid.");
            }
        }
    }

    private static void writeToFile(String content, String filePath, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file - " + filePath);
        }
    }

    private static String[][] ChangeInput(String input) {
        input = input.trim();

        // Special cases like "cd.." or "rm -r"
        if (input.equals("cd..")) {
            return new String[][]{{"cd"}, {".."}};
        } else if (input.startsWith("rm -r")) {
            String argument = input.length() > 5 ? input.substring(5).trim() : "";
            return new String[][]{{"rm -r"}, argument.isEmpty() ? new String[]{} : new String[]{argument}};
        }

        // General case: split by whitespace
        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        String[] arguments = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, arguments, 0, tokens.length - 1);
        return new String[][]{{command}, arguments};
    }

    private static void registerCommands() {
        commandRegistry.put("pwd", new PwdCommand());
        commandRegistry.put("cd", new CdCommand());
        commandRegistry.put("ls", new LsCommand());
        commandRegistry.put("mkdir", new MkdirCommand());
        commandRegistry.put("rmdir", new RmdirCommand());
        commandRegistry.put("touch", new TouchCommand());
        commandRegistry.put("mv", new MvCommand());
        commandRegistry.put("rm", new RmCommand());
        commandRegistry.put("rm -r", new RmRecursiveCommand());
        commandRegistry.put("cat", new CatCommand());
        commandRegistry.put(">", new RedirectionCommand(false));
        commandRegistry.put(">>", new RedirectionCommand(true));
        commandRegistry.put("exit", new ExitCommand());
        commandRegistry.put("help", new HelpCommand());
    }
}
