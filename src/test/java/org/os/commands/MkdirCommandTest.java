package org.os.commands;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

class MkdirCommandTest {
    @Test
    public void mkdir_test() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        mkdirCommand.execute(new String[]{"testing"});
        File file = new File("testing");
        assertTrue(file.exists());
    }

}