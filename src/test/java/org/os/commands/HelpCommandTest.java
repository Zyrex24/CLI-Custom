package org.os.commands;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpCommandTest {
    @Test
    public void HelpCommandShouldListAvaiableCommands() {
HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute(new String[]{});
        String expected = "Available commands:\n" +
                "cd <directory> - Change the current working directory\n" +
                "ls - List the contents of the current directory\n" +
                "mkdir <directory> - Create a new directory\n" +
                "rm <file> - Delete a file\n" +
                "rm -r <directory> - Delete a directory and all of its contents\n" +
                "pwd - Print the current working directory\n" +
                "exit - Exit the shell\n" +
                "help - Display this help message";
        assertEquals(expected, helpCommand.helpMessage);
    }
}