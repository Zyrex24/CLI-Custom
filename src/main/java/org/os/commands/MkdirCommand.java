package org.os.commands;

import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Please specify a directory to create.");
            return;
        }

        // Get the current working directory
        String currentDir = System.getProperty("user.dir");

        // Combine the current directory with the provided path
        File newDirectory = new File(currentDir, args[0]);

        try {
            if (newDirectory.exists()) {
                System.out.println("Error: Directory already exists.");
            } else {
                // Create the directory, including any necessary parent directories
                if (newDirectory.mkdirs()) {
                    System.out.println("Directory named " + newDirectory.getPath() + " has been created.");
                } else {
                    System.out.println("Error: Unable to create directory " + newDirectory.getPath() + ".");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
