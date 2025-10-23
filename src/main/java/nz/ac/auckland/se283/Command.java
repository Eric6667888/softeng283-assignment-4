package nz.ac.auckland.se283;

import java.util.ArrayList;

public class Command {
  private ArrayList<String> commandString;
  private boolean isRunning = false;

  public Command() {
    this.commandString = new ArrayList<>();
  }

  public String getCommands(int index) {
    if (this.commandString.isEmpty() || this.commandString == null) {
      return "No commands to get";
    }
    if (index < 0 || index >= this.commandString.size()) {
      return "Invalid command index";
    }
    return this.commandString.get(index);
  }

  public void addCommand(String command) {
    // Validate the command input
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Invalid command");
    }
    if (command.equals("go north")
        || command.equals("go south")
        || command.equals("go east")
        || command.equals("go west")
        || command.equals("start")
        || command.equals("end")) {
      // Valid command
    } else {
      throw new IllegalArgumentException("Unknown command");
    }

    this.commandString.add(command);
  }

  public void deleteCommand(int index) {
    if (this.commandString.isEmpty() || this.commandString == null) {
      throw new IllegalStateException("No commands to delete");
    }
    if (index < 0 || index >= this.commandString.size()) {
      throw new IndexOutOfBoundsException("Invalid command index");
    }

    this.commandString.remove(index);
  }

  public int getCommandSize() {
    return this.commandString.size();
  }

  public String printCommand() {
    // If no commands have been added, return an empty string
    if (this.commandString.isEmpty()) return "";

    // Join all commands with a comma and space
    StringBuilder commands = new StringBuilder();
    for (int i = 0; i < this.commandString.size(); i++) {
      commands.append(this.commandString.get(i));
      if (i != this.commandString.size() - 1) {
        commands.append(", ");
      }
    }
    return commands.toString();
  }

  public boolean getStatus() {
    return isRunning;
  }

  public void run() {
    if (this.commandString.isEmpty()) {
      throw new IllegalStateException("No commands to execute");
    }
    if (isRunning) {
      throw new IllegalStateException("Command is already running");
    }
    int startCount = 0;
    int endCount = 0;
    for (String command : this.commandString) {
      if (command.equals("start")) {
        startCount++;
      } else if (command.equals("end")) {
        endCount++;
      }
    }
    if (startCount == 1
        && endCount == 1
        && this.commandString.indexOf("start") < this.commandString.indexOf("end")) {
      // Valid sequence
    } else {
      throw new IllegalStateException("Invalid command sequence");
    }
    isRunning = true;
    // Simulate command execution
    // After execution, the command should be marked as not running
  }
}
