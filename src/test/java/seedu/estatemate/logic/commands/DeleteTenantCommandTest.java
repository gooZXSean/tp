package seedu.estatemate.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.estatemate.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.estatemate.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.estatemate.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.estatemate.testutil.TypicalPersons.getTypicalEstateMate;

import org.junit.jupiter.api.Test;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;
import seedu.estatemate.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTenantCommandTest {

    private Model model = new ModelManager(getTypicalEstateMate(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTenantCommand deleteTenantCommand = new DeleteTenantCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTenantCommand.MESSAGE_DELETE_TENANT_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteTenantCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTenantCommand deleteCommand = new DeleteTenantCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteTenantCommand deleteTenantCommand = new DeleteTenantCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteTenantCommand.MESSAGE_DELETE_TENANT_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteTenantCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEstateMate().getPersonList().size());

        DeleteTenantCommand deleteTenantCommand = new DeleteTenantCommand(outOfBoundIndex);

        assertCommandFailure(deleteTenantCommand, model, Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteTenantCommand deleteFirstTenantCommand = new DeleteTenantCommand(INDEX_FIRST_PERSON);
        DeleteTenantCommand deleteSecondTenantCommand = new DeleteTenantCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstTenantCommand.equals(deleteFirstTenantCommand));

        // same values -> returns true
        DeleteTenantCommand deleteFirstTenantCommandCopy = new DeleteTenantCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstTenantCommand.equals(deleteFirstTenantCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstTenantCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstTenantCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstTenantCommand.equals(deleteSecondTenantCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTenantCommand deleteTenantCommand = new DeleteTenantCommand(targetIndex);
        String expected = DeleteTenantCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTenantCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
