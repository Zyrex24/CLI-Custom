Command Line Interface (CLI) Project
Overview
This project is a custom Command Line Interface (CLI), similar to Unix/Linux shells. The CLI supports commands like navigation (cd, pwd), file operations (ls, mkdir, rm, touch), and redirection (>, >>, |). It also includes custom internal commands like help and exit.

Features
Implemented Commands:

pwd: Displays the current working directory.
cd [directory]: Changes the current directory.
ls [-a|-r]: Lists files in the current directory, supporting hidden files (-a) and recursive listing (-r).
mkdir [directory]: Creates a new directory.
rmdir [directory]: Removes an empty directory.
touch [file]: Creates an empty file.
mv [source] [destination]: Moves or renames files and directories.
rm [file]: Removes a file.
cat [file]: Displays the contents of a file.
> [file]: Redirects output to a file (overwrites the file).
>> [file]: Appends output to a file.
[command1] | [command2]: Pipes the output of one command as the input to another.
help: Displays a list of available commands and their usage.
exit: Terminates the CLI session.


Usage

Start the CLI:
Navigate to the project directory in your terminal.

Run the CLI using:
java main.CliFrame

Execute Commands:
The CLI will prompt you to enter commands. Type any of the supported commands listed above.

View Available Commands:
Type help to view the list of commands and their usage.

Exit:
Type exit to quit the CLI.
