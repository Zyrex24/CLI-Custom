package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;

public class TouchCommand implements Command{
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: touch requires exactly one argument.");
            return;
        }

        // Get the current working directory
        String currentDir = System.getProperty("user.dir");
        // Combine the current directory with the provided path
        File newFile = new File(currentDir, args[0]);

        try {
            if (newFile.exists()) {
                newFile.setLastModified(System.currentTimeMillis());
                System.out.println("File modified: " + newFile.getName());
            } else {
                newFile.createNewFile();
                System.out.println("File created: " + newFile.getName());
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to create or modify the file.");
        }
    }
}






//if (args.length == 0) {
//        System.out.println("Error: Please specify a file to create.");
//            return;
//                    }
//
//File newFile = new File(args[0]);
//        try {
//                if (newFile.createNewFile()) {
//
//        } else {
//        System.out.println("Error: File already exists.");
//            }
//                    } catch (Exception e) {
//        System.out.println("Error: An error occurred.");
//        }