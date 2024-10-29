package org.os.commands;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class TouchCommandTest {
    @Test
    public void touch_create_test(){
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[]{"test_touch.txt"});
        File file = new File("test_touch.txt");
        assertTrue(file.exists());
    }
    @Test
    public void touch_timeStampUpdate_test(){
        TouchCommand touchCommand = new TouchCommand();
        touchCommand.execute(new String[]{"test_touch_time.txt"});
        long timeStamp = new File("test_touch_time.txt").lastModified();
        try{
            Thread.sleep(1000);// sleep for 1 second
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        touchCommand.execute(new String[]{"test_touch_time.txt"});
        long newTimeStamp = new File("test_touch_time.txt").lastModified();
        assertNotEquals(timeStamp, newTimeStamp);
    }

}