package seedu.estatemate.logic.commands;

import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.estatemate.testutil.TypicalPersons.getTypicalEstateMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;

public class ListJobCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEstateMate(), new UserPrefs());
        expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());
    }

    @Test
    public void execute_jobListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListJobCommand(), model, String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW,
                expectedModel.getFilteredJobList().size()), expectedModel);
    }

    // no test for filteredJobList here like in ListCommandTest since job list does not use indexing to reference jobs
}
