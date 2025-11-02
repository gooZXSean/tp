package seedu.estatemate.logic.commands;

import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.estatemate.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.estatemate.testutil.TypicalPersons.getTypicalEstateMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ModelManager;
import seedu.estatemate.model.UserPrefs;
import seedu.estatemate.model.person.Person;
import seedu.estatemate.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTenantCommand}.
 */
public class AddTenantCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEstateMate(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getEstateMate(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddTenantCommand(validPerson), model,
                String.format(AddTenantCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getEstateMate().getPersonList().get(0);
        assertCommandFailure(new AddTenantCommand(personInList), model,
                AddTenantCommand.MESSAGE_DUPLICATE_TENANT);
    }

}
