package org.os.commands;
import org.os.interfaces.Command;


public class PwdCommand implements Command {
    String currentDirectory;
    @Override
    public void execute(String[] args){
        this.currentDirectory = System.getProperty("user.dir");
        System.out.println(this.currentDirectory);
    }
}
