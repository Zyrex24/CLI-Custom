package org.os.commands;
import org.os.interfaces.Command;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.out.println("Available commands:");
        System.out.println("pwd - Print working directory");
        System.out.println("cd - Change directory");
        System.out.println("ls - List directory contents");
        System.out.println("mkdir - Make directory");
        System.out.println("rmdir - Remove directory");
        System.out.println("touch - Create file");
        System.out.println("mv - Move file");
        System.out.println("rm - Remove file");
        System.out.println("cat - Concatenate files");
    }
}
