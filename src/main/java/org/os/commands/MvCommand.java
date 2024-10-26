package org.os.commands;

import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class MvCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: mv requires a source and a destination.");
            return;
        }

        try {
            // Resolve destination based on current directory
            File currentDirectory = new File(System.getProperty("user.dir"));
            File destination = new File(currentDirectory, args[args.length - 1]).getCanonicalFile();
            File[] sources = new File[args.length - 1];

            // Resolve each source based on current directory
            for (int i = 0; i < args.length - 1; i++) {
                sources[i] = new File(currentDirectory, args[i]).getCanonicalFile();

            }

            // Handle moving files to a directory or renaming a single file
            if (destination.isDirectory()) {
                moveFilesToDirectory(destination, sources);
            } else if (sources.length == 1) {
                renameOrMoveSingleFile(sources[0], destination);
            } else {
                System.out.println("Error: mv cannot move multiple files to a single destination that is not a directory.");
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to move or rename files. " + e.getMessage());
        }
    }

    // Method to move multiple files to a directory
    private void moveFilesToDirectory(File destination, File[] sources) throws IOException {
        if (!destination.exists() && !destination.mkdirs()) {
            System.out.println("Error: Unable to create destination directory - " + destination.getPath());
            return;
        }

        for (File source : sources) {
            if (!source.exists()) {
                System.out.println("Error: Source file does not exist - " + source.getName());
                continue;
            }

            Path sourcePath = source.toPath();
            Path destinationPath = destination.toPath().resolve(sourcePath.getFileName());

            if (destinationPath.toFile().exists()) {
                System.out.println("File already exists in destination. Overwrite " + destinationPath + "? (y/n)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine().trim();
                if (!response.equalsIgnoreCase("y")) {
                    System.out.println("Skipping file: " + source.getName());
                    continue;
                }
            }

            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + source.getName() + " to " + destination.getPath());
        }
    }

    // Method to handle renaming or moving a single file
    private void renameOrMoveSingleFile(File source, File destination) throws IOException {
        if (!source.exists()) {
            System.out.println("Error: Source file does not exist - " + source.getName());
            return;
        }

        File destinationParent = destination.getParentFile();
        if (destinationParent != null && !destinationParent.exists() && !destinationParent.mkdirs()) {
            System.out.println("Error: Unable to create destination path - " + destinationParent.getPath());
            return;
        }

        if (!destination.exists()) {
            Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Renamed or moved: " + source.getPath() + " to " + destination.getPath());
        } else {
            System.out.println("File already exists. Overwrite? (y/n)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim();
            if (response.equalsIgnoreCase("y")) {
                Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File overwritten: " + destination.getPath());
            } else {
                System.out.println("Move or rename operation canceled.");
            }
        }
    }
}
