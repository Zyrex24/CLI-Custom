package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;
public class CdCommand implements Command {
    @Override
    public void execute(String[] args){
        if (args.length == 0) {
            System.out.println("Error: Please specify a directory to change into.");
            return;
        }

        String targetPath = args[0];
        File currentDirectory = new File(System.getProperty("user.dir"));
        File newDirectory;

        if ("..".equals(targetPath)) {
            // Move up one directory
            newDirectory = currentDirectory.getParentFile();
            if (newDirectory == null) {
                System.out.println("Error: Already at the root directory.");
                return;
            }
        } else {
            // Navigate to specified directory
            newDirectory = new File(currentDirectory, targetPath);
        }

        if (newDirectory.exists() && newDirectory.isDirectory()) {
            System.setProperty("user.dir", newDirectory.getAbsolutePath());
        } else {
            System.out.println("Error: Directory does not exist.");
        }
    }
}
