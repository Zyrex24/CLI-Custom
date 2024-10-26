package org.os.commands;

import org.os.interfaces.Command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RmRecursiveCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: rm -r requires at least one argument.");
            return;
        }

        for (String arg : args) {
            File file = new File(arg);
            try {
                deleteRecursively(file);
                System.out.println("Deleted: " + file.getName());
            } catch (IOException e) {
                System.out.println("Error: Could not delete " + file.getName());
            }
        }
    }

    private void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteRecursively(subFile);
            }
        }
        Files.delete(file.toPath());
    }
}
