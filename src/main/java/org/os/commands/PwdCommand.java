package org.os.commands;
import org.os.interfaces.Command;


public class PwdCommand implements Command {
    @Override
    public void execute(String[] args){
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
    }
}
