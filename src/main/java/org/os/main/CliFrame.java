package org.os.main;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import org.os.commands.*;
import org.os.interfaces.Command;

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

            // Changing input into cmd and args
            String[][] parsedInput = ChangeInput(input);
            String[] commandParts = parsedInput[0];   // The command (could be single part or multi-part like "rm -r")
            String command = String.join(" ", commandParts);  // Join parts to form the complete command string
            String[] arguments = parsedInput[1];  // Arguments array remains as is

            // Execute command if it exists, else give error
            if (commandRegistry.containsKey(command)) {
                commandRegistry.get(command).execute(arguments);
            } else {
                System.out.println("Error: This command is invalid.");
            }
        }
    }

    private static String[][] ChangeInput(String input) {
        input = input.trim();

        // Handle specific special cases
        if (input.equals("cd..")) {
            return new String[][]{{"cd"}, {".."}};
        } else if (input.startsWith("rm -r")) {
            // Treat "rm -r" as a single command
            String argument = input.length() > 5 ? input.substring(5).trim() : "";  // Get text after "rm -r", if any
            return new String[][]{{"rm -r"}, argument.isEmpty() ? new String[]{} : new String[]{argument}};
        }

        // General case: split by whitespace
        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        String[] arguments = new String[tokens.length - 1];

        System.arraycopy(tokens, 1, arguments, 0, tokens.length - 1);
        return new String[][]{{command}, arguments};
    }


    // All available commands
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
//        commandRegistry.put(">", new RedirectionCommand(false));
//        commandRegistry.put(">>", new RedirectionCommand(true));
//        commandRegistry.put("|", new PipeCommand());
        commandRegistry.put("exit", new ExitCommand());
        commandRegistry.put("help", new HelpCommand());
    }
}

