package org.os.commands;

import org.os.interfaces.Command;
import java.io.*;

public class CatCommand implements Command {
    public String fileContent;

    @Override
    public void execute(String[] args) {
        try {
            if (args.length == 0) {
                interactiveMode();
            } else if (args.length == 1 && args[0].startsWith("__PIPE__")) {
                System.out.print(args[0].substring("__PIPE__".length()));
            } else if (args.length == 1) {
                displayFileContent(new File(System.getProperty("user.dir"), args[0]));
            } else {
                handleRedirection(args);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read or write files. " + e.getMessage());
        }
    }

    private void handleRedirection(String[] args) throws IOException {
        boolean isAppend = false;
        int outputArgIndex = -1;

        // Determine if redirection is required and where
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">")) {
                outputArgIndex = i + 1; // The next argument is the output file
                break;
            } else if (args[i].equals(">>")) {
                isAppend = true;
                outputArgIndex = i + 1; // The next argument is the output file
                break;
            }
        }

        if (outputArgIndex > 0 && outputArgIndex < args.length) {
            String outputFileName = args[outputArgIndex];
            if (isAppend) {
                concatenateFilesWithAppend(args, outputFileName);
            } else {
                concatenateFilesWithOverwrite(args, outputFileName);
            }
        } else {
            concatenateFilesWithoutRedirection(args);
        }
    }

    private void concatenateFilesWithAppend(String[] args, String destinationFileName) throws IOException {
        File destinationFile = new File(System.getProperty("user.dir"), destinationFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, true))) {
            for (int i = 0; i < args.length - 1; i++) {
                if (i == args.length - 2) continue; // Skip the last arg which is the output file name
                writeFileContent(args[i], writer);
            }
            System.out.println("Files concatenated (appended) into " + destinationFile.getName());
        }
    }

    private void concatenateFilesWithOverwrite(String[] args, String destinationFileName) throws IOException {
        File destinationFile = new File(System.getProperty("user.dir"), destinationFileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile, false))) {
            for (int i = 0; i < args.length - 1; i++) {
                if (i == args.length - 2) continue; // Skip the last arg which is the output file name
                writeFileContent(args[i], writer);
            }
            System.out.println("Files concatenated (overwritten) into " + destinationFile.getName());
        }
    }

    private void concatenateFilesWithoutRedirection(String[] args) throws IOException {
        for (String fileName : args) {
            displayFileContent(new File(System.getProperty("user.dir"), fileName));
        }
    }

    private void writeFileContent(String fileName, BufferedWriter writer) throws IOException {
        File file = new File(System.getProperty("user.dir"), fileName);
        if (!file.exists()) {
            System.out.println("Error: Source file does not exist - " + file.getName());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }
    }

    private void interactiveMode() {
        System.out.println("Enter text (CTRL + D to finish):");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error in interactive mode: " + e.getMessage());
        }
        System.out.println("(End of input)");
    }

    private void displayFileContent(File file) throws IOException {
        if (!file.exists()) {
            System.out.println("Error: File does not exist - " + file.getName());
            return;
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        }
        fileContent = contentBuilder.toString();
        System.out.print(fileContent);
    }
}
