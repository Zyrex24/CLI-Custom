package org.os.commands;

import org.os.interfaces.Command;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LsCommand implements Command {
    public File[] files;
    public ArrayList<String> fileNames = new ArrayList<>();

    @Override
    public void execute(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        this.fileNames.clear(); // Clear previous file names each time command is run

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
            this.files = currentDirectory.listFiles(); // Include all files, including hidden files
        } else {
            this.files = currentDirectory.listFiles(file -> !file.getName().startsWith(".")); // Exclude hidden files
        }

        // Check if files are found
        if (this.files == null || this.files.length == 0) {
            System.out.println("No files found in the current directory.");
            return;
        }

        // Sort files based on reverse order option
        if (reverseOrder) {
            Arrays.sort(this.files, Comparator.comparing(File::getName).reversed());
        } else {
            Arrays.sort(this.files, Comparator.comparing(File::getName));
        }

        // Store file names in fileNames
        for (File file : this.files) {
            this.fileNames.add(file.getName()); // Store each file name
        }

        // Output the file names
        System.out.println(this.fileNames); // Print fileNames as output
    }
}
