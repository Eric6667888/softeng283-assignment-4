package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandEditChecker {
  private Command command;
  private CommandRevert commandRevert;

  public void add10Commands() {
    // Player adds multiple commands
    command.addCommand("start");
    command.addCommand("go north");
    command.addCommand("go south");
    command.addCommand("go east");
    command.addCommand("go west");
    command.addCommand("go north");
    command.addCommand("go south");
    command.addCommand("go east");
    command.addCommand("go west");
    command.addCommand("end");
  }

  public void revert10Commands() {
    // Undo all 10 commands one by one
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert(); // 5
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert();
    commandRevert.revert(); // 10
  }

  public void redo10Commands() {
    // Redo all 10 commands one by one
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo(); // 5
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo();
    commandRevert.redo(); // 10
  }

  @BeforeEach
  public void setUp() {
    command = new Command();
    commandRevert = new CommandRevert(command, mock(FeedbackService.class));
    // Any setup code can go here
  }

  @Test
  public void undoCommand_whenNoCommandsAdded() {
    // Attempt to undo a command when none have been added
    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              commandRevert.revert();
            });

    // Check if the exception message is as expected
    assertEquals("No commands to delete", exception.getMessage());
  }

  @Test
  public void undoCommand_singleCommandDeleted() {
    command.addCommand("go north");
    commandRevert.revert();

    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals("", commandString);
  }

  @Test
  public void undoCommand_10CommandsDeleted() {
    // Player adds multiple commands
    add10Commands();
    // Undo all 10 commands one by one
    revert10Commands();

    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals("", commandString);
  }

  @Test
  public void undoCommand_11CommandsDeleted() {
    // Player adds multiple commands
    add10Commands();
    command.addCommand("go north");
    // Undo all 11 commands one by one
    revert10Commands();
    commandRevert.revert();

    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals("", commandString);
  }

  @Test
  public void undoCommand_feedbackCalled() {
    FeedbackService mockFeedbackService = mock(FeedbackService.class);
    commandRevert = new CommandRevert(command, mockFeedbackService);
    command.addCommand("go north");
    commandRevert.revert();
    verify(mockFeedbackService, times(1)).sendFeedback("Undo: go north");
  }

  @Test
  public void redoCommand_whenNoCommandsToRedo() {
    command.addCommand("go north");
    // Attempt to redo a command when none have been undone
    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              commandRevert.redo();
            });

    // Check if the exception message is as expected
    assertEquals("No commands to redo", exception.getMessage());
  }

  @Test
  public void redoCommand_singleCommandredo() {
    command.addCommand("go north");
    commandRevert.revert();
    // Attempt to redo a command with an valid index
    commandRevert.redo();
    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals("go north", commandString);
  }

  @Test
  public void redoCommand_10CommandsRedo() {
    // Player adds multiple commands
    add10Commands();
    command.addCommand("go north");
    // Undo all 10 commands one by one
    revert10Commands();
    // Redo all 10 commands one by one
    redo10Commands();

    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertEquals(
        "start, go north, go south, go east, go west, go north, go south, go east, go west, end, go"
            + " north",
        commandString);
  }

  @Test
  public void redoCommand_11CommandsRedo() {
    // Player adds multiple commands
    add10Commands();
    command.addCommand("go north");
    // Undo all 11 commands one by one
    revert10Commands();
    commandRevert.revert();
    // Redo all 11 commands one by one
    redo10Commands();
    Exception exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              commandRevert.redo();
            });

    String commandString = command.printCommand();
    // Check if the command string is as expected
    assertAll(
        () -> assertEquals("No commands to redo", exception.getMessage()),
        () ->
            assertEquals(
                "start, go north, go south, go east, go west, go north, go south, go east, go west,"
                    + " end",
                commandString));

    // Check if the exception message is as expected

  }

  @Test
  public void redoCommand_feedbackCalled() {
    // Create a mock FeedbackService
    FeedbackService mockFeedbackService = mock(FeedbackService.class);
    commandRevert = new CommandRevert(command, mockFeedbackService);
    command.addCommand("go north");
    commandRevert.revert();
    commandRevert.redo();

    // Verify that sendFeedback was called with the correct message
    verify(mockFeedbackService, times(1)).sendFeedback("Redo: go north");
  }
}
