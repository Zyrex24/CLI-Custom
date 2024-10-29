package org.os.commands;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class PipeCommandTest {

    private final PipeCommand pipeCommand = new PipeCommand();

    @Test
    public void testSingleCommandEcho() {
        String[] command = {"echo Hello"};

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        pipeCommand.execute(command);

        // Verify output contains "Hello"
        assertTrue(output.toString().trim().contains("Hello"), "Output should contain 'Hello'");
    }

    @Test
    public void testPipeWithGrep() {
        String[] command = {"echo Hello World", "grep Hello"};

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        pipeCommand.execute(command);

        // Verify output contains "Hello"
        assertTrue(output.toString().trim().contains("Hello"), "Output should contain 'Hello'");
    }

    @Test
    public void testPipeWithTransformAndUpperCase() {
        String[] command = {"echo hello", "tr a-z A-Z"};

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        pipeCommand.execute(command);

        // Verify output is uppercase "HELLO"
        assertEquals("HELLO", output.toString().trim(), "Output should be 'HELLO'");
    }

    @Test
    public void testMultiplePipes() {
        String[] command = {"echo hello world", "grep hello", "tr a-z A-Z"};

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        pipeCommand.execute(command);

        // Verify output contains "HELLO"
        assertTrue(output.toString().trim().contains("HELLO"), "Output should contain 'HELLO'");
    }

    @Test
    public void testInvalidCommand() {
        String[] command = {"invalidCommand"};

        // Capture the printed error
        ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorOutput));

        pipeCommand.execute(command);

        // Verify that error output contains some form of exception or error message
        assertTrue(errorOutput.toString().contains("Exception"), "Error output should contain 'Exception'");
    }

    @Test
    public void testEmptyCommandArray() {
        String[] command = {};

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        pipeCommand.execute(command);

        // Verify output is empty
        assertTrue(output.toString().trim().isEmpty(), "Output should be empty for an empty command array");
    }
}
