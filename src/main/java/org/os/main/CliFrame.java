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

            // Detect and handle piping if present
            if (input.contains("|")) {
                handlePiping(input);
                continue;
            }

            // Detect and parse redirection arguments (>, >>)
            boolean append = input.contains(">>");
            boolean overwrite = input.contains(">") && !append;
            String redirectFile = null;

            if (overwrite || append) {
                String[] commandAndArgs = append ? input.split(">>") : input.split(">");
                input = commandAndArgs[0].trim();  // Command part
                redirectFile = commandAndArgs[1].trim();  // File path for redirection
            }

            // Execute command
            String[][] parsedInput = ChangeInput(input);
            String command = String.join(" ", parsedInput[0]);
            String[] arguments = parsedInput[1];

            if (commandRegistry.containsKey(command)) {
                Command cmdInstance = commandRegistry.get(command);

                // Redirect output if a redirect file is specified
                if (redirectFile != null) {
                    // Redirect output to file instead of console
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    PrintStream originalOut = System.out;
                    System.setOut(new PrintStream(outputStream));  // Capture command output

                    try {
                        cmdInstance.execute(arguments);
                    } finally {
                        System.setOut(originalOut);  // Restore System.out
                    }

                    String commandOutput = outputStream.toString();
                    writeToFile(commandOutput, redirectFile, append);
                } else {
                    // No redirection; execute and print directly
                    cmdInstance.execute(arguments);
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

    private static void handlePiping(String input) {
        String[] pipedCommands = input.split("\\|");
        if (pipedCommands.length != 2) {
            System.out.println("Error: Only single piping (command1 | command2) is supported.");
            return;
        }

        String firstCommandInput = pipedCommands[0].trim();
        String secondCommandInput = pipedCommands[1].trim();

        // Parse the first and second commands
        String[][] firstParsedInput = ChangeInput(firstCommandInput);
        String[][] secondParsedInput = ChangeInput(secondCommandInput);

        String firstCommand = String.join(" ", firstParsedInput[0]);
        String secondCommand = String.join(" ", secondParsedInput[0]);

        if (commandRegistry.containsKey(firstCommand) && commandRegistry.containsKey(secondCommand)) {
            Command firstCmdInstance = commandRegistry.get(firstCommand);
            Command secondCmdInstance = commandRegistry.get(secondCommand);

            // Execute PipeCommand with the first and second commands
            PipeCommand pipeCommand = new PipeCommand(firstCmdInstance, secondCmdInstance);
            pipeCommand.execute(firstParsedInput[1]);  // Pass arguments of the first command to execute
        } else {
            System.out.println("Error: One or both commands are invalid for piping.");
        }
    }

    public static void registerCommands() {
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
        commandRegistry.put("exit", new ExitCommand());
        commandRegistry.put("help", new HelpCommand());
    }
}
