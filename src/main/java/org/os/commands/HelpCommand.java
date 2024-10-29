package org.os.commands;
import org.os.interfaces.Command;

public class HelpCommand implements Command {
    public String helpMessage;

    @Override
    public void execute(String[] args) {
        this.helpMessage = "Available commands:\n" +
                "cd <directory> - Change the current working directory\n" +
                "ls - List the contents of the current directory\n" +
                "mkdir <directory> - Create a new directory\n" +
                "rm <file> - Delete a file\n" +
                "rm -r <directory> - Delete a directory and all of its contents\n" +
                "pwd - Print the current working directory\n" +
                "exit - Exit the shell\n" +
                "help - Display this help message";
        System.out.println(this.helpMessage);
    }
}
