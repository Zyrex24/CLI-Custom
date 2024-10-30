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
        if(outputFile.exists()) {
            rmCommand.execute(new String[]{outputFileName});
        }

    }

    @Test
    public void test_write_with_cat_ls_pwd() throws IOException {

        // Setup: Create test files using TouchCommand
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[]{"test_for_ls.txt"});
        touchCommand.execute(new String[]{"test_for_pwd.txt"});
        touchCommand.execute(new String[]{"test_for_cat.txt"});

        // Test CatCommand with overwrite redirection
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[]{"test_touch.txt", ">", "test_for_cat.txt"});
        String expectedCatContent = Files.readString(new File("test_touch.txt").toPath()).trim();
        String actualCatContent = Files.readString(new File("test_for_cat.txt").toPath()).trim();
        assertEquals(expectedCatContent, actualCatContent);

        // Test LsCommand with overwrite redirection
        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{">", "test_for_ls.txt"});
        String expectedLsContent = String.join(System.lineSeparator(), lsCommand.fileNames).trim();
        String actualLsContent = Files.readString(new File("test_for_ls.txt").toPath()).trim();
        assertEquals(expectedLsContent, actualLsContent);

        // Test PwdCommand with overwrite redirection
        PwdCommand pwdCommand = new PwdCommand();
        pwdCommand.execute(new String[]{">", "test_for_pwd.txt"});
        String expectedPwdContent = pwdCommand.currentDirectory.trim();
        String actualPwdContent = Files.readString(new File("test_for_pwd.txt").toPath()).trim();
        assertEquals(expectedPwdContent, actualPwdContent);

        // Cleanup: Remove the test files
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{"test_for_cat.txt"});
        rmCommand.execute(new String[]{"test_for_ls.txt"});
        rmCommand.execute(new String[]{"test_for_pwd.txt"});
    }


    @Test
    public void test_append_with_cat_ls_pwd() throws IOException {

        // Setup: Create test files using TouchCommand
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[]{"test_for_ls.txt"});
        touchCommand.execute(new String[]{"test_for_pwd.txt"});
        touchCommand.execute(new String[]{"test_for_cat.txt"});

        // Test CatCommand with append redirection
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[]{"test_touch.txt", ">>", "test_for_cat.txt"});
        String expectedCatContent = Files.readString(new File("test_touch.txt").toPath()).trim();
        String actualCatContent = Files.readString(new File("test_for_cat.txt").toPath()).trim();
        assertTrue(actualCatContent.endsWith(expectedCatContent)); // Verify appended content

        // Test LsCommand with append redirection
        LsCommand lsCommand = new LsCommand();
        lsCommand.execute(new String[]{">>", "test_for_ls.txt"});
        String expectedLsContent = String.join(System.lineSeparator(), lsCommand.fileNames).trim();
        String actualLsContent = Files.readString(new File("test_for_ls.txt").toPath()).trim();
        assertTrue(actualLsContent.endsWith(expectedLsContent)); // Verify appended content

        // Test PwdCommand with append redirection
        PwdCommand pwdCommand = new PwdCommand();
        pwdCommand.execute(new String[]{">>", "test_for_pwd.txt"});
        String expectedPwdContent = pwdCommand.currentDirectory.trim();
        String actualPwdContent = Files.readString(new File("test_for_pwd.txt").toPath()).trim();
        assertTrue(actualPwdContent.endsWith(expectedPwdContent)); // Verify appended content

        // Cleanup: Remove the test files
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{"test_for_cat.txt"});
        rmCommand.execute(new String[]{"test_for_ls.txt"});
        rmCommand.execute(new String[]{"test_for_pwd.txt"});
    }


    @Test
    public void test_append_non_existing_file() throws IOException {
        LsCommand lsCommand = new LsCommand();
        assertFalse(new File ("i_dont_exist.txt").exists());
        lsCommand.execute(new String[]{">>", "i_dont_exist.txt"});
        assertFalse(new File ("i_dont_exist.txt").exists());
    }
}
