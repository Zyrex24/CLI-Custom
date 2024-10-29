//package org.os.commands;
//import org.os.interfaces.Command;
//
//import java.io.*;
//import java.util.Scanner;
//
//
//public class CatCommand implements Command {
//    public String fileContent;
//    @Override
//    public void execute(String[] args) {
//        try {
//            if (args.length == 0) {
//                interactiveMode(); // No file provided, enter interactive mode
//            } else if (args.length == 1) {
//                displayFileContent(new File(args[0]));
//            } else {
//                concatenateFiles(args);
//            }
//        } catch (IOException e) {
//            System.out.println("Error: Unable to read or write files. " + e.getMessage());
//        }
//    }
//
//    // Method for interactive mode if no file is provided
//    private void interactiveMode() {
//        System.out.println("Enter text (CTRL + D to finish):");
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            System.out.println(scanner.nextLine());
//        }
//        System.out.println("(End of input)");
//    }
//
//    // Method to display a single file's content
//    private void displayFileContent(File file) throws IOException {
//        if (!file.exists()) {
//            System.out.println("Error: File does not exist - " + file.getName());
//            return;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//        }
//    }
//
//    // Method to concatenate multiple files
//    private void concatenateFiles(String[] args) throws IOException {
//        File destinationFile = new File(args[args.length - 1]);
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, true))) {
//            for (int i = 0; i < args.length - 1; i++) {
//                File sourceFile = new File(args[i]);
//
//                if (!sourceFile.exists()) {
//                    System.out.println("Error: Source file does not exist - " + sourceFile.getName());
//                    continue;
//                }
//
//                try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        writer.write(line);
//                        writer.newLine();
//                    }
//                }
//            }
//            System.out.println("Files concatenated into " + destinationFile.getName());
//        }
//    }
//}
package org.os.commands;

import org.os.interfaces.Command;

import java.io.*;
import java.util.Scanner;

public class CatCommand implements Command {
    public String fileContent; // Variable to store file content

    @Override
    public void execute(String[] args) {
        try {
            if (args.length == 0) {
                interactiveMode(); // No file provided, enter interactive mode
            } else if (args.length == 1) {
                displayFileContent(new File(args[0])); // Display content of single file
            } else {
                // Check for output redirection
                if (args.length == 4 && args[2].equals(">")) {
                    concatenateFiles(new String[]{args[0], args[1], args[3]}); // Concatenate files and redirect output
                } else if (args.length == 3 && args[1].equals(">>")) {
                    appendFileContent(args[0], args[2]); // Append content of first file to second file
                } else {
                    concatenateFiles(args); // Concatenate multiple files
                }
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

    // Method to display a single file's content and store it in the fileContent variable
    private void displayFileContent(File file) throws IOException {
        if (!file.exists()) {
            System.out.println("Error: File does not exist - " + file.getName());
            return;
        }

        StringBuilder contentBuilder = new StringBuilder(); // StringBuilder to accumulate content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator()); // Append line and newline
            }
        }
        fileContent = contentBuilder.toString(); // Store accumulated content in the variable
        System.out.print(fileContent);
    }

    // Method to append contents of one file to another
    private void appendFileContent(String sourceFileName, String destinationFileName) throws IOException {
        File sourceFile = new File(sourceFileName);
        File destinationFile = new File(destinationFileName);

        if (!sourceFile.exists()) {
            System.out.println("Error: Source file does not exist - " + sourceFile.getName());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, true));
             BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line); // Append each line from source to destination
                writer.newLine();
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
                    }
                }
            }
            System.out.println("Files concatenated into " + destinationFile.getName());
        }
    }
}


