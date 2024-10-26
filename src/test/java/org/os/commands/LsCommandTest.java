package org.os.commands;
import org.junit.jupiter.api.Test;
import org.os.interfaces.Command;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class LsCommandTest{
    @Test
    public void testLsCommand() {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File[] files = currentDirectory.listFiles();
        assert files != null;
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

}