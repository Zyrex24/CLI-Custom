package main;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import commands.*;

public class CliFrame {
    private static Map<String, Command> commandRegistry = new HashMap<>();

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Our CLI :) ! Type 'help' to see available commands or type 'exit' to quit.");
        registerCommands();

        while (true) {
            System.out.print("CLI/user> ");
            String input = userInput.nextLine().trim();
            if (input.isEmpty()) continue;

            // Changing input into cmd and args
            String[] changedInput = ChangeInput(input);
            String command = changedInput[0];
            String[] arguments;
            if (changedInput[1].isEmpty()) {
                arguments = new String[]{};
            } else {
                arguments = changedInput[1].split("\\s+");
            }

            // Execute command if it exists, else give error
            if (commandRegistry.containsKey(command)) {
                commandRegistry.get(command).execute(arguments);
            } else {
                System.out.println("Error: This command is invalid.");
            }
        }
    }

    private static String[] ChangeInput(String input) {
        int firstSpaceIndex = input.indexOf(' ');
        if (firstSpaceIndex == -1) {
            return new String[]{input, ""};
        }
        String command = input.substring(0, firstSpaceIndex);
        String args = input.substring(firstSpaceIndex + 1);
        return new String[]{command, args};
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
        commandRegistry.put("cat", new CatCommand());
        commandRegistry.put(">", new RedirectionCommand(false));
        commandRegistry.put(">>", new RedirectionCommand(true));
        commandRegistry.put("|", new PipeCommand());
        commandRegistry.put("exit", new ExitCommand());
        commandRegistry.put("help", new HelpCommand());
    }
}
