package org.os.commands;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class MvCommandTest {
    private final String srcDir = "src_test_dir";
    private final String destDir = "dest_test_dir";
    private final String srcFileName = "testfile.txt";
    private final String renamedFileName = "renamedfile.txt";
    private final File srcFile = new File(srcDir, srcFileName);
    private final File renamedFile = new File(srcDir, renamedFileName);
    private final File destFile = new File(destDir, srcFileName);

    @BeforeEach
    public void setup() throws IOException {
        new File(srcDir).mkdir();
        new File(destDir).mkdir();
        srcFile.createNewFile();
    }

    @AfterEach
    public void cleanup() {
        srcFile.delete();
        renamedFile.delete();
        destFile.delete();
        new File(srcDir).delete();
        new File(destDir).delete();
    }

    @Test
    public void testRename() {
        MvCommand mvCommand = new MvCommand();
        mvCommand.execute(new String[]{srcFile.getPath(), renamedFile.getPath()});
        assertFalse(srcFile.exists(), "Source file should no longer exist after rename.");
        assertTrue(renamedFile.exists(), "File should exist with the new name after rename.");
    }

    @Test
    public void testMoveFileToDifferentDirectory() {
        MvCommand mvCommand = new MvCommand();
        mvCommand.execute(new String[]{srcFile.getPath(), destDir});
        assertFalse(srcFile.exists(), "Source file should no longer exist after move.");
        assertTrue(destFile.exists(), "File should exist in the destination directory after move.");
    }
}
