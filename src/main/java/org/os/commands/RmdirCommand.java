package org.os.commands;

import org.os.interfaces.Command;
import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class RmdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Please specify a directory to remove.");
            return;
        }

        String currentDir = System.getProperty("user.dir");
        File directory = new File(currentDir, args[0]);

        if (!directory.exists()) {
            System.out.println("Error: Directory does not exist.");
            return;
        }

        // Check if the directory is empty
        if (directory.list().length > 0) {
            System.out.println("Warning: The directory is not empty. Do you want to proceed with deletion? (y/n)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim();

            if (!response.equalsIgnoreCase("y")) {
                System.out.println("Deletion operation canceled.");
                return;
            }
        }

        // Try to delete the directory
        try {
            deleteDirectory(directory);
            System.out.println("Directory " + directory.getName() + " has been removed.");
        } catch (Exception e) {
            System.out.println("Error: Unable to remove directory. " + e.getMessage());
        }
    }

    // Helper method to delete a directory and its contents
    private void deleteDirectory(File directory) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // Recursive call to delete subdirectories
                } else {
                    Files.delete(file.toPath()); // Delete file
                }
            }
        }
        Files.delete(directory.toPath()); // Delete the empty directory itself
    }
}