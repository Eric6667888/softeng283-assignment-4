package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandExecutionCheckerTest {
  private Command command;

  @BeforeEach
  public void setUp() {
    command = new Command();
    // Any setup code can go here
  }

  @Test
  public void printCommand_whenPlayerAsked() {
    // Player asked to print command
    String commandString = command.printCommand();

    // Check if the command string is empty (not null)
    assertEquals("", commandString);
  }

  @Test
  public void printCommand_whenPlayerAddedCommand() {
    // Player adds a command
    command.addCommand("go north");
    String commandString = command.printCommand();

    // Check if the command string is as expected
    assertEquals("go north", commandString);
  }

  @Test
  public void printCommand_whenPlayerAddedMultipleCommands() {
    // Player adds multiple commands
    command.addCommand("go north");
    command.addCommand("go south");
    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals("go north, go south", commandString);
  }

  @Test
  public void getStatus_isCommandStartRunning() {
    // Check if the command is not running initially
    boolean commandStatus = command.getStatus();

    // Command should not be running at the start
    assertFalse(commandStatus);
  }

  @Test
  public void getStatus_isCommandEndRunning() {
    // Player adds a command
    command.addCommand("start");
    command.addCommand("go north");
    command.addCommand("end");
    // Simulate command execution
    command.run();

    // Check if the command is running
    boolean commandStatus = command.getStatus();

    // Command should be running after execution
    assertTrue(commandStatus);
  }

  @Test
  public void getCommands_invalidIndex() {
    command.addCommand("go north");
    // Player tries to get a command with an invalid index
    String commandString = command.getCommands(-1);

    // Check if the returned message is as expected
    assertEquals("Invalid command index", commandString);
  }

  @Test
  public void addCommand_emptyCommand() {
    // Player adds an empty command

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              command.addCommand("");
            });

    assertEquals("Invalid command", exception.getMessage());
  }

  @Test
  public void addCommand_nullCommand() {
    // Player adds a null command

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              command.addCommand(null);
            });

    assertEquals("Invalid command", exception.getMessage());
  }

  @Test
  public void addCommand_invalidCommand() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              command.addCommand("fly");
            });

    assertEquals("Unknown command", exception.getMessage());
  }

  @Test
  public void deleteCommand_emptyCommandList() {
    // Player tries to delete a command from an empty command list

    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.deleteCommand(0);
            });

    assertEquals("No commands to delete", exception.getMessage());
  }

  @Test
  public void deleteCommand_invalidIndex() {
    // Player tries to delete a command with an invalid index
    command.addCommand("go north");

    IndexOutOfBoundsException exception =
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> {
              command.deleteCommand(5);
            });

    assertEquals("Invalid command index", exception.getMessage());
  }

  @Test
  public void deleteCommand_validIndex() {
    // Player deletes a command with a valid index
    command.addCommand("go north");
    command.addCommand("go south");

    // Delete the first command
    command.deleteCommand(0);
    String commandString = command.printCommand();

    // Check if the remaining command is as expected
    assertEquals("go south", commandString);
  }

  @Test
  public void runCommand_emptyCommandList() {
    // Player tries to run command with an empty command list

    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.run();
            });

    assertEquals("No commands to execute", exception.getMessage());
  }

  @Test
  public void runCommand_invalidSequence() {
    // Player adds commands without an end command
    command.addCommand("end");
    command.addCommand("go north");
    command.addCommand("start");

    // Simulate command execution
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.run();
            });

    assertEquals("Invalid command sequence", exception.getMessage());
  }

  @Test
  public void runCommand_noStartCommand() {
    // Player adds commands without a start command
    command.addCommand("go north");
    command.addCommand("end");

    // Simulate command execution
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.run();
            });

    assertEquals("Invalid command sequence", exception.getMessage());
  }

  @Test
  public void runCommand_twiceStartCommand() {
    // Player adds commands with two start commands
    command.addCommand("start");
    command.addCommand("go north");
    command.addCommand("start");
    command.addCommand("end");

    // Simulate command execution
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.run();
            });

    assertEquals("Invalid command sequence", exception.getMessage());
  }

  @Test
  public void runCommand_validCommands() {
    // Player adds valid commands and runs them
    command.addCommand("start");
    command.addCommand("go north");
    command.addCommand("end");

    // Simulate command execution
    command.run();

    // Check if the command is running after execution
    assertTrue(command.getStatus());
  }

  @Test
  public void runCommand_alreadyRunning() {
    // Player adds valid commands and runs them
    command.addCommand("start");
    command.addCommand("go north");
    command.addCommand("end");

    // Simulate command execution
    command.run();

    // Try to run the command again while it's already running
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              command.run();
            });

    assertEquals("Command is already running", exception.getMessage());
  }
}
