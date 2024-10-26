package org.os.commands;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PwdCommandTest {
    @Test
    public void pwdShouldPrintCurrentDirectory() {
        // Given
        PwdCommand pwdCommand = new PwdCommand();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        try {
            // When
            pwdCommand.execute(new String[]{});

            // Then
            String expectedOutput = System.getProperty("user.dir") + System.lineSeparator();
            assertEquals(expectedOutput, outputStream.toString());
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}
