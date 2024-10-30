package org.os.commands;

import org.os.interfaces.Command;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PwdCommand implements Command {
    String currentDirectory;

    @Override
    public void execute(String[] args) {
        this.currentDirectory = System.getProperty("user.dir");

        // Check for redirection
        if (args.length == 2 && ">".equals(args[0])) {
            writeFile(args[1], false);
        } else if (args.length == 2 && ">>".equals(args[0])) {
            writeFile(args[1], true);
        } else {
            // Print to console if no redirection
            System.out.println(this.currentDirectory);
        }
    }

    // Helper method to write the current directory to a file
    private void writeFile(String outputFile, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, append))) {
            writer.write(this.currentDirectory);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + outputFile);
        }
    }
}
