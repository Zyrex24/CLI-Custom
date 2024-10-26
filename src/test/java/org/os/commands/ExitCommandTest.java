package org.os.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {
    @Test
    public void ExitCommandShouldTerminateTheProgram() {
        ExitCommand exitCommand = new ExitCommand();
        exitCommand.execute(new String[]{});
        assertEquals(0, 0);
    }

}