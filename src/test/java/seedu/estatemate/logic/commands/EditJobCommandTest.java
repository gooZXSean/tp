package seedu.estatemate.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.estatemate.testutil.TypicalPersons.getTypicalEstateMate;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;
import seedu.estatemate.model.job.Description;
import seedu.estatemate.model.job.Job;

/**
 * Integration tests for {@link EditJobCommand} with ModelManager.
 */
public class EditJobCommandTest {

    private Model model;
    private Model expectedModel;

    private int id1;
    private int id2;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEstateMate(), new UserPrefs());
        expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());

        // Seed two jobs with deterministic ids
        id1 = model.nextJobId();
        model.addJob(new Job(new Description("Fix sink leak"), id1));
        id2 = model.nextJobId();
        model.addJob(new Job(new Description("Replace corridor lights"), id2));

        expectedModel.addJob(new Job(new Description("Fix sink leak"), id1));
        expectedModel.addJob(new Job(new Description("Replace corridor lights"), id2));
    }

    @Test
    public void execute_validIdNewDescription_success() {
        Description newDesc = new Description("Repair lobby door");

        EditJobCommand cmd = new EditJobCommand(id1, newDesc);

        // expected state after edit
        expectedModel.editJobById(id1, newDesc);

        assertCommandSuccess(
                cmd,
                model,
                String.format(String.format(EditJobCommand.MESSAGE_SUCCESS, id1, newDesc)),
                expectedModel
        );
    }

    @Test
    public void execute_invalidId_throwsCommandException() {
        int nonExisting = 999999;
        EditJobCommand cmd = new EditJobCommand(nonExisting, new Description("Anything"));
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_JOB_ID);
    }

    @Test
    public void execute_duplicateDescription_allowedAndKeepsOrder() {
        Description duplicate = new Description("Replace corridor lights");
        EditJobCommand cmd = new EditJobCommand(id1, duplicate);

        expectedModel.editJobById(id1, duplicate);

        assertCommandSuccess(
                cmd,
                model,
                String.format(EditJobCommand.MESSAGE_SUCCESS, id1, duplicate),
                expectedModel
        );

        List<Integer> afterIds = model.getFilteredJobList().stream()
                .map(Job::getId)
                .collect(Collectors.toList());
        assertEquals(List.of(id1, id2), afterIds);
    }


    @Test
    public void execute_editPreservesOrderById_success() {
        int id3 = 3;
        model.addJob(new Job(new Description("Replace lift button"), id3));
        expectedModel.addJob(new Job(new Description("Replace lift button"), id3));

        // Check initial order is 1, 2, 3
        List<Integer> initialIds = model.getFilteredJobList().stream()
                .map(Job::getId).collect(Collectors.toList());
        assertEquals(List.of(id1, id2, id3), initialIds,
                "Precondition failed: initial job order should be [1, 2, 3]");

        // Edit id2 which should not change its position in the shown list
        Description newDesc = new Description("Repair lobby door (updated)");
        EditJobCommand cmd = new EditJobCommand(id2, newDesc);

        expectedModel.editJobById(id2, newDesc);

        assertCommandSuccess(
                cmd,
                model,
                String.format(String.format(EditJobCommand.MESSAGE_SUCCESS, id2, newDesc)),
                expectedModel
        );

        // Check if order is correct
        List<Integer> afterIds = model.getFilteredJobList().stream()
                .map(Job::getId).collect(Collectors.toList());
        assertEquals(List.of(id1, id2, id3), afterIds,
                "Editing a job must not change job ordering");
    }

    @Test
    public void equals() {
        EditJobCommand a = new EditJobCommand(3, new Description("A"));
        EditJobCommand b = new EditJobCommand(3, new Description("A"));
        EditJobCommand c = new EditJobCommand(4, new Description("A"));
        EditJobCommand d = new EditJobCommand(3, new Description("B"));

        // same values -> true
        assertTrue(a.equals(b));

        // same object -> true
        assertTrue(a.equals(a));

        // different id -> false
        assertFalse(a.equals(c));

        // different description -> false
        assertFalse(a.equals(d));

        // null -> false
        assertFalse(a.equals(null));

        // different type -> false
        assertFalse(a.equals("not a command"));
    }

    @Test
    public void toStringMethod() {
        EditJobCommand cmd = new EditJobCommand(42, new Description("Z"));
        String expected = EditJobCommand.class.getCanonicalName()
                + "{targetId=42, newDescription=Z}";
        assertEquals(expected, cmd.toString());
    }
}
