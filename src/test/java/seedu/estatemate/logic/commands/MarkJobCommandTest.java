package seedu.estatemate.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.EstateMate;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;
import seedu.estatemate.model.job.Description;
import seedu.estatemate.model.job.Job;

/**
 * Contains integration tests (interaction with the ModelManager) and unit tests for
 * {@code MarkJobCommand}.
 */
public class MarkJobCommandTest {

    private static final Description DUMMY_DESC = new Description("Fix broken window pane");
    private static final int VALID_JOB_ID = 1;
    private static final int INVALID_JOB_ID = 99;

    private static final String MESSAGE_ALREADY_DONE = "Job is already marked done!";

    /**
     * Helper method to create a fresh ModelManager initialized with a specific job.
     * This simulates adding the job to the initial state of the application.
     */
    private Model getModelManagerWithJob(Job job) {
        EstateMate estateMate = new EstateMate();
        estateMate.addJob(job);
        return new ModelManager(estateMate, new UserPrefs());
    }

    @Test
    public void execute_validIdUnmarkedJob_success() { //Valid id + Job is unmarked
        Job initialJob = new Job(DUMMY_DESC, VALID_JOB_ID); //test job
        Model actualModel = getModelManagerWithJob(initialJob);

        Job expectedMarkedJob = new Job(DUMMY_DESC, VALID_JOB_ID); //Comparison job
        expectedMarkedJob.setDone(true);
        Model expectedModel = getModelManagerWithJob(expectedMarkedJob);

        MarkJobCommand markJobCommand = new MarkJobCommand(VALID_JOB_ID);

        String expectedMessage = String.format(MarkJobCommand.MESSAGE_SUCCESS, VALID_JOB_ID);

        assertCommandSuccess(markJobCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidId_throwsCommandException() { //Invalid id
        Job dummyJob = new Job(DUMMY_DESC, VALID_JOB_ID);
        Model model = getModelManagerWithJob(dummyJob);

        MarkJobCommand markJobCommand = new MarkJobCommand(INVALID_JOB_ID);

        assertCommandFailure(markJobCommand, model, Messages.MESSAGE_INVALID_JOB_ID);
    }

    @Test
    public void equals() {
        MarkJobCommand markFirstCommand = new MarkJobCommand(VALID_JOB_ID);
        MarkJobCommand markSecondCommand = new MarkJobCommand(INVALID_JOB_ID);

        assertTrue(markFirstCommand.equals(markFirstCommand));

        MarkJobCommand markFirstCommandCopy = new MarkJobCommand(VALID_JOB_ID);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        assertFalse(markFirstCommand.equals(1));

        assertFalse(markFirstCommand.equals(null));

        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        MarkJobCommand markJobCommand = new MarkJobCommand(VALID_JOB_ID);
        String expected = MarkJobCommand.class.getCanonicalName() + "{targetId=" + VALID_JOB_ID + "}";
        assertEquals(expected, markJobCommand.toString());
    }
}
