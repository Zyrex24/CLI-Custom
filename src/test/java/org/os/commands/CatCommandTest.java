package org.os.commands;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CatCommandTest {
    @Test
    public void test_output_file_content() throws IOException{
        File file = new File("test_for_cat.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("Hello World");
        writer.close();

        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[]{"test_for_cat.txt"});

        assertEquals("Hello World".trim(), catCommand.fileContent.trim());
    }

    @Test
    public void test_concat_2_files() throws IOException {
        TouchCommand files_creator = new TouchCommand();
        files_creator.execute(new String[]{"test_for_cat1.txt"});
        files_creator.execute(new String[]{"test_for_cat2.txt"});
        files_creator.execute(new String[]{"test_for_cat3.txt"});

        // Write content to the first two files
        try (FileWriter writer1 = new FileWriter("test_for_cat1.txt");
             FileWriter writer2 = new FileWriter("test_for_cat2.txt")) {
            writer1.write("Hello");
            writer2.write("World");
        }

        // Execute the CatCommand
        CatCommand catCommand = new CatCommand();
        catCommand.execute(new String[]{"test_for_cat1.txt", "test_for_cat2.txt", ">", "test_for_cat3.txt"});

        // Verify the content in the output file
        String content1 = Files.readString(new File("test_for_cat1.txt").toPath()).trim();
        String content2 = Files.readString(new File("test_for_cat2.txt").toPath()).trim();
        String content3 = Files.readString(new File("test_for_cat3.txt").toPath()).trim();
        assertEquals(content1 + content2, content3); // Check the concatenated content

        // Cleanup
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{"test_for_cat1.txt"});
        rmCommand.execute(new String[]{"test_for_cat2.txt"});
        rmCommand.execute(new String[]{"test_for_cat3.txt"});
    }


    @Test
    public void test_append_file_to_another()throws IOException{
        TouchCommand files_creator = new TouchCommand();
        files_creator.execute(new String[]{"test_for_cat1.txt"});
        files_creator.execute(new String[]{"test_for_cat2.txt"});
        FileWriter writer1 = new FileWriter("test_for_cat1.txt");
        FileWriter writer2 = new FileWriter("test_for_cat2.txt");
        writer1.write("Hello");
        writer2.write("World");
        writer1.close();
        writer2.close();
        CatCommand catCommand = new CatCommand();
        String old_content = Files.readString(new File("test_for_cat2.txt").toPath()).trim();
        catCommand.execute(new String[]{"test_for_cat1.txt", ">>", "test_for_cat2.txt"});
        File [] files = {new File("test_for_cat1.txt"), new File("test_for_cat2.txt")};
        String content1 = Files.readString(files[0].toPath()).trim();
        String content2 = Files.readString(files[1].toPath()).trim();
        assertEquals(old_content + content1, content2);
        RmCommand rmCommand = new RmCommand();
        rmCommand.execute(new String[]{"test_for_cat1.txt"});
        rmCommand.execute(new String[]{"test_for_cat2.txt"});
    }
}