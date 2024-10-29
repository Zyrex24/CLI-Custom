package org.os.commands;
import org.junit.jupiter.api.Test;
import org.os.interfaces.Command;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class RmRecursiveCommandTest {
    @Test
    void test_rm_r(){
        RmRecursiveCommand rmRecursiveCommand = new RmRecursiveCommand();
        MkdirCommand mkdirCommand = new MkdirCommand();
        TouchCommand touchCommand = new TouchCommand();
        mkdirCommand.execute(new String[]{"test_rm_r"});
        touchCommand.execute(new String[]{"test_rm_r/test_rm_r_file"});
        assertTrue(new File("test_rm_r").exists());
        assertTrue(new File("test_rm_r/test_rm_r_file").exists());
        rmRecursiveCommand.execute(new String[]{"test_rm_r"});
        assertFalse(new File("test_rm_r").exists());
        assertFalse(new File("test_rm_r/test_rm_r_file").exists());
    }

}