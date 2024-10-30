//package org.os.commands;
//
//import org.os.interfaces.Command;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//
//public class LsCommand implements Command {
//    public File[] files;
//    public ArrayList<String> fileNames = new ArrayList<>();
//
//    @Override
//    public void execute(String[] args) {
//        File currentDirectory = new File(System.getProperty("user.dir"));
//        this.fileNames.clear(); // Clear previous file names each time command is run
//
//        boolean showAll = false;
//        boolean reverseOrder = false;
//
//        // Process arguments for options
//        for (String arg : args) {
//            if ("-a".equals(arg)) {
//                showAll = true;
//            } else if ("-r".equals(arg)) {
//                reverseOrder = true;
//            }
//        }
//
//        // Get files in the current directory
//        if (showAll) {
//            this.files = currentDirectory.listFiles(); // Include all files, including hidden files
//        } else {
//            this.files = currentDirectory.listFiles(file -> !file.getName().startsWith(".")); // Exclude hidden files
//        }
//
//        // Check if files are found
//        if (this.files == null || this.files.length == 0) {
//            System.out.println("No files found in the current directory.");
//            return;
//        }
//
//        // Sort files based on reverse order option
//        if (reverseOrder) {
//            Arrays.sort(this.files, Comparator.comparing(File::getName).reversed());
//        } else {
//            Arrays.sort(this.files, Comparator.comparing(File::getName));
//        }
//
//        // Store file names in fileNames
//        for (File file : this.files) {
//            this.fileNames.add(file.getName()); // Store each file name
//        }
//
//        for(String name: this.fileNames){
//            System.out.println(name);
//        }
//    }
//}


package org.os.commands;

import org.os.interfaces.Command;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LsCommand implements Command {
    public File[] files;
    public ArrayList<String> fileNames = new ArrayList<>();

    @Override
    public void execute(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        this.fileNames.clear(); // Clear previous file names

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
            this.files = currentDirectory.listFiles(); // Include hidden files
        } else {
            this.files = currentDirectory.listFiles(file -> !file.getName().startsWith(".")); // Exclude hidden files
        }

        // Sort files based on reverse order option
        if (this.files != null) {
            if (reverseOrder) {
                Arrays.sort(this.files, Comparator.comparing(File::getName).reversed());
            } else {
                Arrays.sort(this.files, Comparator.comparing(File::getName));
            }

            // Collect file names
            for (File file : this.files) {
                this.fileNames.add(file.getName());
            }
        }

        // Determine if redirection is needed
        if (args.length == 2 && ">".equals(args[0])) {
            writeFile(args[1], false);
        } else if (args.length == 2 && ">>".equals(args[0])) {
            // Check if the file exists before appending
            File outputFile = new File(args[1]);
            if (outputFile.exists()) {
                writeFile(args[1], true);
            } else {
                System.out.println("No files found to append. Output file does not exist: " + args[1]);
            }
        } else {
            // Print to console if no redirection
            for (String name : this.fileNames) {
                System.out.println(name);
            }
        }
    }

    // Helper method to write file names to a file
    private void writeFile(String outputFile, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, append))) {
            for (String name : this.fileNames) {
                writer.write(name);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + outputFile);
        }
    }
}



