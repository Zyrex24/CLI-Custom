package org.os.commands;
import org.os.interfaces.Command;

import java.io.*;
import java.util.Scanner;


public class CatCommand implements Command {
    @Override
    public void execute(String[] args) {
        try {
            if (args.length == 0) {
                interactiveMode(); // No file provided, enter interactive mode
            } else if (args.length == 1) {
                displayFileContent(new File(args[0]));
            } else {
                concatenateFiles(args);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read or write files. " + e.getMessage());
        }
    }

    // Method for interactive mode if no file is provided
    private void interactiveMode() {
        System.out.println("Enter text (CTRL + D to finish):");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        System.out.println("(End of input)");
    }

    // Method to display a single file's content
    private void displayFileContent(File file) throws IOException {
        if (!file.exists()) {
            System.out.println("Error: File does not exist - " + file.getName());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // Method to concatenate multiple files
    private void concatenateFiles(String[] args) throws IOException {
        File destinationFile = new File(args[args.length - 1]);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, true))) {
            for (int i = 0; i < args.length - 1; i++) {
                File sourceFile = new File(args[i]);

                if (!sourceFile.exists()) {
                    System.out.println("Error: Source file does not exist - " + sourceFile.getName());
                    continue;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Files concatenated into " + destinationFile.getName());
        }
    }
}
