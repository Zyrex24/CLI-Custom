package org.os.commands;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class RmdirCommandTest {

    @Test
    public void test_remove_empty_dir() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        RmdirCommand rmdirCommand = new RmdirCommand();
        String[] args = new String[]{"test_dir"};
        mkdirCommand.execute(args);
        assertTrue(new File("test_dir").exists());
        rmdirCommand.execute(args);
        assertFalse(new File("test_dir").exists());
    }

    @Test
    public void test_remove_non_empty_dir() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        TouchCommand touchCommand = new TouchCommand();
        String[] args = new String[]{"test_nonEmpty_dir"};
        mkdirCommand.execute(args);
        assertTrue(new File("test_nonEmpty_dir").exists());

        String[] args2 = new String[]{"test_nonEmpty_dir/test_file"};
        touchCommand.execute(args2);
        assertTrue(new File("test_nonEmpty_dir/test_file").exists());

        // Use a Scanner with simulated input
        Scanner mockScanner = new Scanner("y\n"); // Simulate user input "y"
        RmdirCommand rmdirCommand = new RmdirCommand(mockScanner);

        // Call execute with confirmation
        String[] rmdirArgs = new String[]{"test_nonEmpty_dir", "y", "NoWarning"};
        rmdirCommand.execute(rmdirArgs);

        // Check that the directory does not exist after the command
        assertFalse(new File("test_nonEmpty_dir").exists());
    }
}
