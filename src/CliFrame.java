import java.util.Scanner;

public class CliFrame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI! Type 'help' to see available commands.");

        while (true) {
            System.out.print("CLI> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            // Command parsing
            String[] tokens = input.split("\\s+");
            String command = tokens[0];
            String[] arguments = new String[tokens.length - 1];
            System.arraycopy(tokens, 1, arguments, 0, tokens.length - 1);

            // Command execution
            switch (command) {
                case "pwd":
                    new PwdCommand().execute(arguments);
                    break;
                case "cd":
                    new CdCommand().execute(arguments);
                    break;
                case "ls":
                    new LsCommand().execute(arguments);
                    break;
                case "mkdir":
                    new MkdirCommand().execute(arguments);
                    break;
                case "rmdir":
                    new RmdirCommand().execute(arguments);
                    break;
                case "touch":
                    new TouchCommand().execute(arguments);
                    break;
                case "mv":
                    new MvCommand().execute(arguments);
                    break;
                case "rm":
                    new RmCommand().execute(arguments);
                    break;
                case "cat":
                    new CatCommand().execute(arguments);
                    break;
                case ">":
                case ">>":
                    new RedirectionCommand().execute(arguments, command.equals(">>"));
                    break;
                case "|":
                    new PipeCommand().execute(arguments);
                    break;
                case "exit":
                    new ExitCommand().execute(arguments);
                    return;  // Exit the loop and terminate the program
                case "help":
                    new HelpCommand().execute(arguments);
                    break;
                default:
                    System.out.println("Error: Command not recognized.");
            }
        }
    }
}
