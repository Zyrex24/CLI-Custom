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
    public void testCdToCurrentDirectory() {
        CdCommand cdCommand = new CdCommand();
        cdCommand.execute(new String[]{"."});

        assertEquals(initialDir, System.getProperty("user.dir"));
    }
}
