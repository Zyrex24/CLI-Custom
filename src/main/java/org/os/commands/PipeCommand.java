package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;


public class PipeCommand implements Command{
    @Override
    public void execute(String[] args){
        if (args.length == 0) {
            System.out.println("Error: Please specify a directory to change into.");
            return;
        }

        File newDirectory = new File(args[0]);
        if (newDirectory.exists() && newDirectory.isDirectory()) {
            System.setProperty("user.dir", newDirectory.getAbsolutePath());
        } else {
            System.out.println("Error: Directory does not exist.");
        }
    }
}
