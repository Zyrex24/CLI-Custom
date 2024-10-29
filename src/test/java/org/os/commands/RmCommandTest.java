package org.os.commands;
import org.junit.jupiter.api.Test;
import org.os.interfaces.Command;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class RmCommandTest {
    @Test
    public void testRmFile() {
        RmCommand rmCommand = new RmCommand();
        TouchCommand create_test_file = new TouchCommand();
        create_test_file.execute(new String[]{"test_rm_file"});
        assertTrue(new File("test_rm_file").exists());
        rmCommand.execute(new String[]{"test_rm_file"});
        assertFalse(new File("test_rm_file").exists());
    }

}