package nz.ac.auckland.se283;

import java.util.ArrayList;

public class CommandRevert {
  private Command command;
  private ArrayList<String> commandRevertString;
  private final FeedbackService feedbackService;
  private final int MAX_REVERT = 10;

  public CommandRevert(Command command, FeedbackService feedbackService) {
    this.command = command;
    this.commandRevertString = new ArrayList<>();
    this.feedbackService = feedbackService;
  }

  public void revert() {
    // Only allow to revert from the back of the list
    // Validate the index
    String commandToUndo = command.getCommands(command.getCommandSize() - 1);
    if (commandToUndo.equals("No commands to get")) {
      throw new IllegalStateException("No commands to delete");
    }
    if (commandToUndo.equals("Invalid command index")) {
      throw new IndexOutOfBoundsException("Invalid command index");
    }
    // Store the command to be undone for potential redo
    commandRevertString.add(commandToUndo);
    if (commandRevertString.size() > MAX_REVERT) {
      commandRevertString.remove(0); // Remove the oldest command if exceeding max limit
    }
    // Remove the command from the main command list
    command.deleteCommand(command.getCommandSize() - 1);
    // Provide feedback that the command was undone
    feedbackService.sendFeedback("Undo: " + commandToUndo);
  }

  public void redo() {
    if (commandRevertString.isEmpty()
        || commandRevertString.size() == 0
        || commandRevertString == null) {
      throw new IllegalStateException("No commands to redo");
    }
    // Redo the last undone command
    String commandToRedo = commandRevertString.get(commandRevertString.size() - 1);
    command.addCommand(commandToRedo);
    commandRevertString.remove(commandRevertString.size() - 1);
    // Provide feedback that the command was redone
    feedbackService.sendFeedback("Redo: " + commandToRedo);
  }
}
