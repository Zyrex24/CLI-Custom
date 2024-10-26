package org.os.commands;

import org.os.interfaces.Command;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class LsCommand implements Command {
    @Override
    public void execute(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File[] files;

        // Flags for options
        boolean showAll = false;
        boolean reverseOrder = false;

        // Process arguments for options
        for (String arg : args) {
            if ("-a".equals(arg)) {
                showAll = true;
            } else if ("-r".equals(arg)) {
                reverseOrder = true;
            }
        }

        // Get files in the current directory
        if (showAll) {
            // Include all files, including hidden files
            files = currentDirectory.listFiles();
        } else {
            // Exclude hidden files
            files = currentDirectory.listFiles(file -> !file.getName().startsWith("."));
        }

        // Check if files are found
        if (files == null || files.length == 0) {
            System.out.println("No files found in the current directory.");
            return;
        }

        // Sort files based on reverse order option
        if (reverseOrder) {
            Arrays.sort(files, Comparator.comparing(File::getName).reversed());
        } else {
            Arrays.sort(files, Comparator.comparing(File::getName));
        }

        // Print file names
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}
