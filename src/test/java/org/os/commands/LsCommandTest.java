package org.os.commands;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;


public class LsCommandTest {

    @Test
    public void testLsCommandVisibleFiles() {
        LsCommand lsCommand = new LsCommand();
        String[] args = new String[]{};
        lsCommand.execute(args);
        for (File file : lsCommand.files) {
            if (file.toPath().startsWith(".")) {
                assertFalse(file.isHidden());

            }
        }
    }

    @Test
    public void FilesAreReversed() {
        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{}); // Execute without reverse
        File[] temp = lsCommand.files.clone();

        lsCommand.execute(new String[]{"-r"}); // Execute with reverse
        File[] temp2 = lsCommand.files;

        assertEquals(temp.length, temp2.length, "Array lengths should match for reversed comparison");

        // Check if `temp2` is the reverse of `temp`
        boolean isReversedOfOriginal = true;
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i].getName().equals(temp2[temp2.length - 1 - i].getName())) {
                isReversedOfOriginal = false;
                break;
            }
        }

        assertTrue(isReversedOfOriginal, "The files array should be the reverse of the original order");
    }
}

