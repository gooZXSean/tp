package seedu.estatemate.logic.commands;

import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.estatemate.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.estatemate.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.estatemate.testutil.TypicalPersons.getTypicalEstateMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEstateMate(), new UserPrefs());
        expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, String.format(
                Messages.MESSAGE_TENANTS_LISTED_OVERVIEW, model.getFilteredPersonList().size()), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model,
                String.format(Messages.MESSAGE_TENANTS_LISTED_OVERVIEW, 7),
                expectedModel);
    }
}
