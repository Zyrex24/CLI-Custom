package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class RmCommand implements Command{
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: rm requires at least one argument.");
            return;
        }

        for (String arg : args) {
            // Get the current working directory
            String currentDir = System.getProperty("user.dir");
            File file = new File(currentDir, arg);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Deleted: " + file.getName());
                } else {
                    System.out.println("Error: Could not delete " + file.getName());
                }
            } else {
                System.out.println("File not found: " + file.getName());
            }
        }
    }
}

