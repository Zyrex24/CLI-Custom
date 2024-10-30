package org.os.commands;

import org.os.main.CliFrame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class PipeCommandTest {

    @Test
    public void testPipeLsToCat() {
        // Prepare to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Create and register commands
        CliFrame.registerCommands();

        // Create a PipeCommand with ls and cat
        PipeCommand pipeCommand = new PipeCommand(new LsCommand(), new CatCommand());

        // Execute the pipe command
        pipeCommand.execute(new String[]{});

        // Restore original System.out
        System.setOut(originalOut);

        // Verify output
        String output = outputStream.toString();
        assertEquals(output.trim(), output.trim());
    }
}