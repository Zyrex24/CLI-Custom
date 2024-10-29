package org.os.commands;

import org.os.interfaces.Command;
import java.io.*;
import java.util.Scanner;

public class RedirectionCommand implements Command {
    private boolean append;

    public RedirectionCommand(boolean append) {
        this.append = append; // True for >> (append), False for > (overwrite)
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Redirection requires text and a target file.");
            return;
        }

        String inputText = args[0];
        File targetFile = new File(args[1]);

        // If the input is a directory path, perform directory listing
        if (new File(inputText).isDirectory()) {
            listDirectory(new File(inputText), targetFile);
        } else {
            // Otherwise, write the input text to the target file (either overwrite or append)
            writeTextToFile(inputText, targetFile);
        }
    }

    private void writeTextToFile(String text, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            BufferedReader reader = new BufferedReader(new StringReader(text));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            System.out.println("Content written to " + file.getPath());
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file - " + file.getPath());
        }
    }

    private void listDirectory(File dir, File outputFile) {
        if (!dir.isDirectory()) {
            System.out.println("Error: " + dir.getPath() + " is not a directory.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, append))) {
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("No files found in directory " + dir.getPath());
                return;
            }

            for (File file : files) {
                writer.write(file.getName());
                writer.newLine();
            }
            System.out.println("Directory listing written to " + outputFile.getPath());
        } catch (IOException e) {
            System.out.println("Error: Unable to write directory listing to file - " + outputFile.getPath());
        }
    }
}
