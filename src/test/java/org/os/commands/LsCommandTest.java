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
    public void testLsCommandWithAllFiles() {
        LsCommand lsCommand = new LsCommand();
        String[] args = new String[]{"-a"};
        lsCommand.execute(args);
        File temp = new File(System.getProperty("user.dir"));
        File[] allFiles = temp.listFiles();
        boolean to_assert = allFiles.length == lsCommand.files.length;
        for(int i = 0; i < allFiles.length; i++) {
            if (!allFiles[i].getName().equals(lsCommand.files[i].getName())) {
                to_assert = false;
                break;
            }
        }
        assertTrue(to_assert);

    }
// I will be right back in 15 mins - Ahmed
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



//package org.os.commands;
//
//import org.junit.jupiter.api.*;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.PrintStream;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LsCommandTest {
//    private final String testDir = "test_ls_dir";
//
//    @BeforeEach
//    public void setup() {
//        new File(testDir).mkdir();
//        new File(testDir + "/file1.txt").createNewFile();
//        new File(testDir + "/file2.txt").createNewFile();
//    }
//
//    @AfterEach
//    public void cleanup() {
//        new File(testDir + "/file1.txt").delete();
//        new File(testDir + "/file2.txt").delete();
//        new File(testDir).delete();
//    }
//
//    @Test
//    public void testLs() {
//        LsCommand lsCommand = new LsCommand();
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        System.setProperty("user.dir", new File(testDir).getAbsolutePath());
//        lsCommand.execute(new String[]{});
//
//        assertTrue(outContent.toString().contains("file1.txt"));
//        assertTrue(outContent.toString().contains("file2.txt"));
//    }
//}
