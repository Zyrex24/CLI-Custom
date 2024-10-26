package org.os.commands;
import org.os.interfaces.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args){
        System.exit(0);
    }
}
