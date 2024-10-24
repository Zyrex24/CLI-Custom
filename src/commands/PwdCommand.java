import java.io.File;

public class PwdCommand {
    public void execute(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
    }
}