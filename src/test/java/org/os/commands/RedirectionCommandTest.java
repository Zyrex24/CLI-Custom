//package org.os.commands;
//import org.os.interfaces.Command;
//import java.io.*;
//import java.nio.file.Files;
//import java.util.Scanner;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class RedirectionCommandTest {
//    @Test
//    public void re_dir_creates_file_if_it_does_not_exist() {
//        RedirectionCommand redirectionCommand = new RedirectionCommand(false);
//        redirectionCommand.execute(new String[]{"Hello World", "test_for_redirection.txt"});
//        File file = new File("test_for_redirection.txt");
//        assertTrue(file.exists());
//        new RmCommand().execute(new String[]{"test_for_redirection.txt"});
//    }
//    @Test
//    public void re_dir_overrites_the_content_of_existing_file() throws IOException {
//        RedirectionCommand redirectionCommand = new RedirectionCommand(false);
//        File file = new File("test_touch.txt");
//        assertTrue(file.exists());
//        String old_content = Files.readString(file.toPath()).trim();
//        FileWriter writer = new FileWriter(file);
//        writer.write("this is the new content");
//        String new_content = Files.readString(file.toPath()).trim();
//        writer.close();
//        redirectionCommand.execute(new String[]{new_content, "test_touch.txt"});
//        assertEquals(new_content, Files.readString(file.toPath()).trim());
//
//
//
//
//
//    }
//
//}


package org.os.commands;
import org.os.interfaces.Command;
import java.io.File;
import org.junit.jupiter.api.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.sql.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RedirectionCommandTest {
    private final String outputFileName = "test_output.txt";
    private final File outputFile = new File(outputFileName);

    @AfterEach
    public void cleanup() {
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{outputFile.getName()});
    }

    @Test
    public void testOverwriteWithSingleLineEcho() throws IOException {
        RedirectionCommand command = new RedirectionCommand(false);  // Overwrite mode
        command.execute(new String[]{"Hello, World!", outputFileName});

        String content = Files.readString(outputFile.toPath()).trim();
        assertEquals("Hello, World!", content);
    }

    @Test
    public void testAppendWithSingleLineEcho() throws IOException {
        RedirectionCommand command = new RedirectionCommand(false);  // Overwrite mode
        command.execute(new String[]{"First line", outputFileName});

        RedirectionCommand appendCommand = new RedirectionCommand(true);  // Append mode
        appendCommand.execute(new String[]{" Second line", outputFileName});

        String content = Files.readString(outputFile.toPath()).trim();
        assertEquals("First line Second line", content);
    }

    @Test
    public void testOverwriteWithLsCommand() throws IOException {
        LsCommand lsCommand = new LsCommand();
        ArrayList<String> names = new ArrayList<>();
        lsCommand.execute(new String[]{});
        names = lsCommand.fileNames;
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{outputFileName});
        RedirectionCommand command = new RedirectionCommand(false);
        command.execute(new String[]{names.toString(), outputFileName});
        String content = Files.readString(outputFile.toPath()).trim();
        assertEquals(names.toString(), content);
    }

    @Test
    public void testAppendWithLsCommand() throws IOException {
        LsCommand lsCommand = new LsCommand();
        ArrayList<String> names = new ArrayList<>();
        lsCommand.execute(new String[]{});
        names = lsCommand.fileNames;
        String fileName = "test_touch.txt";
        File file = new File(fileName);
        assert(file.length() >0);
        String old_content = Files.readString(file.toPath()).trim();
        RedirectionCommand command = new RedirectionCommand(true);
        command.execute(new String[]{names.toString(), fileName});
        String content = Files.readString(file.toPath()).trim();
        assertEquals(old_content + names.toString(), content);
    }

    @Test
    public void testMultipleAppends() throws IOException {
        RedirectionCommand command = new RedirectionCommand(true);  // Append mode

        command.execute(new String[]{"Line 1", outputFileName});
        command.execute(new String[]{" Line 2", outputFileName});
        command.execute(new String[]{" Line 3", outputFileName});

        String content = Files.readString(outputFile.toPath()).trim();
        assertEquals("Line 1 Line 2 Line 3", content);
    }

    @Test
    public void testOverwriteNonExistentFile() throws IOException {
        RedirectionCommand command = new RedirectionCommand(false);  // Overwrite mode
        command.execute(new String[]{"Creating new file content", outputFileName});

        assertTrue(outputFile.exists(), "Output file should be created");
        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals("Creating new file content", content);
    }

    @Test
    public void testAppendNonExistentFile() throws IOException {
        RedirectionCommand command = new RedirectionCommand(true);  // Append mode
        command.execute(new String[]{"First entry in new file", outputFileName});

        assertTrue(outputFile.exists(), "Output file should be created");
        String content = Files.readString(outputFile.toPath()).trim();
        assertEquals("First entry in new file", content);
    }

//    @Test
//    public void testInvalidCommandWithRedirection() throws IOException {
//        RedirectionCommand command = new RedirectionCommand(false);  // Overwrite mode
//        command.execute(new String[]{"invalidCommand", "teeest.txt"});
//        File nonExistentFile = new File("teeest.txt");
//
//        assertFalse(nonExistentFile.exists(), "Output file should not be created");
//
//    }
}
