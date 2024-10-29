package org.os.commands;
import org.os.interfaces.Command;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PipeCommand implements Command {
    private static final Logger logger = Logger.getLogger(PipeCommand.class.getName());

    @Override
    public void execute(String[] args) {
        try {
            String result = executePipeline(args);
            System.out.println(result);    // Print the final output of the pipeline
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while executing pipeline: ", e);
        }
    }

    private String executePipeline(String[] commands) throws IOException, InterruptedException {
        List<Process> processes = new ArrayList<>();
        ProcessBuilder builder;
        Process previousProcess = null;

        for (String command : commands) {
            builder = new ProcessBuilder(command.split(" "));

            if (previousProcess != null) {
                builder.redirectInput(ProcessBuilder.Redirect.PIPE);
            }

            Process process = builder.start();

            // Only redirect if previousProcess exists
            if (previousProcess != null) {
                redirectProcessOutput(previousProcess.getInputStream(), process.getOutputStream());
            }

            processes.add(process);
            previousProcess = process;
        }

        // Collect final output from last process in pipeline
        StringBuilder result = new StringBuilder();
        if (previousProcess != null) {  // Checking to avoid NullPointerException
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(previousProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
        }

        // Close all processes
        for (Process process : processes) {
            process.waitFor();
            process.destroy();
        }

        return result.toString().trim();
    }

    private void redirectProcessOutput(InputStream input, OutputStream output) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }
}
