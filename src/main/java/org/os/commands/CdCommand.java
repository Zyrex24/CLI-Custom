package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;


public class CdCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Please specify a directory to change into.");
            return;
        }

        String targetPath = args[0];
        File currentDirectory = new File(System.getProperty("user.dir"));
        File newDirectory;

        try {
            // Handle parent directory navigation (e.g., ".." or "../../someDir")
            if ("..".equals(targetPath) || targetPath.startsWith("..")) {
                newDirectory = new File(currentDirectory, targetPath).getCanonicalFile();
                if (!newDirectory.exists() || !newDirectory.isDirectory()) {
                    System.out.println("Error: Invalid directory path - " + targetPath);
                    return;
                }
            } else {
                // Navigate to specified directory relative to current directory
                newDirectory = new File(currentDirectory, targetPath).getCanonicalFile();
            }

            if (newDirectory.exists() && newDirectory.isDirectory()) {
                System.setProperty("user.dir", newDirectory.getAbsolutePath());
                System.out.println("Changed directory to " + newDirectory.getPath());
            } else {
                System.out.println("Error: Directory does not exist - " + targetPath);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to resolve the path - " + e.getMessage());
        }
    }
}
