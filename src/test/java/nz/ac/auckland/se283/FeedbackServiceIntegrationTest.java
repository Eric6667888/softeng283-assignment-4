package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedbackServiceIntegrationTest {
  private Command command;
  private CommandRevert commandRevert;
  private RealFeedbackService feedbackService;

  @BeforeEach
  public void setUp() {
    command = new Command();
    feedbackService = new RealFeedbackService();
    commandRevert = new CommandRevert(command, feedbackService);
  }

  @Test
  public void undoFeedbackMessage_commandSendsFeedback_success() {
    String message = "go north";
    command.addCommand(message);
    commandRevert.revert();
    String response = commandRevert.sendRealFeedback(message);
    assertEquals(message, response);
  }
}
