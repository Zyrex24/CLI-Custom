package org.os.commands;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelpCommandTest {
    @Test
    public void HelpCommandShouldListAvaiableCommands() {
        HelpCommand helpCommand = new HelpCommand();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        try {
            // Execute the help command
            helpCommand.execute(new String[]{});

            // Expected help text
            String expectedOutput = "Available commands:\n" +
                    "pwd - Print working directory\n" +
                    "cd - Change directory\n" +
                    "ls - List directory contents\n" +
                    "mkdir - Make directory\n" +
                    "rmdir - Remove directory\n" +
                    "touch - Create file\n" +
                    "mv - Move file\n" +
                    "rm - Remove file\n" +
                    "cat - Concatenate files\n";

            // Normalize line endings and trim
            String actualOutput = outputStream.toString().replace("\r\n", "\n").trim();
            expectedOutput = expectedOutput.trim();

            // Assert the output is as expected
            assertEquals(expectedOutput, actualOutput);
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}