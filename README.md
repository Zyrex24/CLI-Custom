# Custom CLI Project

## Project Overview
This is a Java-based custom command-line interface (CLI) application simulating a file system command environment. It includes implementations of common commands (`ls`, `cat`, `mv`, `rm`, `mkdir`, etc.) with support for redirection (`>`, `>>`) and piping (`|`). The project leverages Java I/O and file manipulation techniques, making it a versatile simulation of a Unix-like terminal environment.

## Key Features

- **Custom Command Support**: Implements essential commands including `ls`, `cat`, `pwd`, `mv`, `rm`, `mkdir`, `rmdir`, `touch`, and more.
- **Redirection Support**: Handles both overwrite (`>`) and append (`>>`) redirections to output files.
- **Piping**: Allows output of one command to be passed as input to another using the `|` operator.
- **Interactive Mode for `cat`**: Allows users to enter text directly when no file is provided.
- **Argument Parsing**: Supports flags like `-r` and `-a` in the `ls` command to display files in reverse order or include hidden files.
- **Command Validation and Error Handling**: Each command includes checks for valid inputs and provides appropriate error messages.
- **JUnit Testing**: Includes extensive unit tests covering command functionality, redirection, and edge cases.

## Project Structure

The project directory structure is as follows:

```
.
├── src
│   └── main
│       └── java
│           └── org
│               └── os
│                   ├── main
│                   │   └── CliFrame.java
│                   ├── commands
│                   │   ├── CatCommand.java
│                   │   ├── LsCommand.java
│                   │   ├── PwdCommand.java
│                   │   ├── MvCommand.java
│                   │   ├── RmCommand.java
│                   │   ├── MkdirCommand.java
│                   │   ├── RmdirCommand.java
│                   │   ├── TouchCommand.java
│                   │   ├── HelpCommand.java
│                   │   └── ExitCommand.java
│                   └── interfaces
│                       └── Command.java
└── test
    └── org
        └── os
            ├── commands
               ├── CatCommandTest.java
               ├── RmRecursiveCommandTest.java
               ├── RmdirCommandTest.java
               ├── MvCommandTest.java
               ├── RmCommandTest.java
               ├── CdCommandTest.java
               ├── TouchCommandTest.java
               ├── MkdirCommandTest.java
               ├── LsCommandTest.java
               ├── PwdCommandTest.java
               ├── HelpCommandTest.java
               └── RedirectionCommandTest.java
```
            
## Getting Started

1. Clone the Repository:

```
git clone --single-branch --branch master https://github.com/Zyrex24/CLI-Custom.git
cd CLI-Custom
```

Input File Format
Commands are executed through CLI input; however, certain commands require additional inputs. For example, the cat command expects either a file name as an argument or redirection syntax.

Example Command Format
To list files in the current directory:
```
ls
```
To concatenate a file:
```
cat file.txt > output.txt
```
## Output Format

- Standard Output: Results of commands are printed directly to the console by default.
  
- Redirection Output: If redirection is specified, output is written to the specified file:

```
ls > output.txt  # Writes directory listing to output.txt
cat file1.txt >> output.txt  # Appends content of file1.txt to output.txt
```

## Contributors

### Phase 1: Core Implementation of the CLI
- Member 1: Core Framework & Simple Commands
- Member 2: Directory Commands (mkdir, rmdir, ls)
- Member 3: File Commands (touch, mv, rm, cat)
- Member 4: Internal Commands & Testing Setup

### Phase 2: Advanced Features and Finalization
- Member 1: Redirection (>, >>)
- Member 2: Piping (|)
- Member 3: Enhanced Error Handling & Command Validation
- Member 4: Final Testing and Code Cleanup

## License

This project is licensed under the Apache License - see the LICENSE file for details.
