package org.os.commands;
import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class CdCommandTest {
    private final String initialDir = System.getProperty("user.dir");
    private final String testDir = "test_cd_dir";

    @BeforeEach
    public void setup() {
        new File(testDir).mkdir();
    }

    @AfterEach
    public void cleanup() {
        new File(testDir).delete();
        System.setProperty("user.dir", initialDir);  // Reset to initial directory
    }

    @Test
    public void testCdToValidDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[]{testDir});
        assertEquals(new File(testDir).getAbsolutePath(), System.getProperty("user.dir"));
    }

    @Test
    public void testCdToNonExistentDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[]{"nonexistent_directory"});
        assertEquals(initialDir, System.getProperty("user.dir"));
    }

    @Test
    public void testCdToParentDirectory() {
        CdCommand cdCommand = new CdCommand();
        System.setProperty("user.dir", new File(testDir).getAbsolutePath());  // Start in testDir
        cdCommand.execute(new String[]{".."});

        String expectedDir = new File(testDir).getParentFile().getAbsolutePath();
        assertEquals(expectedDir, System.getProperty("user.dir"));
    }

    @Test
    public void testCdToMultipleParentDirectories() {
        CdCommand cdCommand = new CdCommand();
        System.setProperty("user.dir", new File(testDir).getAbsolutePath());
        cdCommand.execute(new String[]{"../.."});

        String expectedDir = new File(testDir).getParentFile().getParentFile().getAbsolutePath();
        assertEquals(expectedDir, System.getProperty("user.dir"));
    }

    @Test
    public void testCdToAbsolutePath() {
        CdCommand cdCommand = new CdCommand();
        String absolutePath = new File(testDir).getAbsolutePath();

        cdCommand.execute(new String[]{absolutePath});
        assertEquals(absolutePath, System.getProperty("user.dir"));
    }

    @Test
    public void testCdToCurrentDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[]{"."});

        assertEquals(initialDir, System.getProperty("user.dir"));
    }
}
