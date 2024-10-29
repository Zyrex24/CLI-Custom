package org.os.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PwdCommandTest {
    @Test
    public void pwd_test() {
        PwdCommand pwdCommand = new PwdCommand();
        pwdCommand.execute(new String[]{});
        String expected = System.getProperty("user.dir");
        assertEquals(expected, pwdCommand.currentDirectory);
    }
}
