package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.estatemate.testutil.Assert.assertThrows;
import static seedu.estatemate.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.estatemate.commons.core.GuiSettings;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.logic.commands.exceptions.CommandException;
import seedu.estatemate.model.EstateMate;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.ReadOnlyEstateMate;
import seedu.estatemate.model.ReadOnlyUserPrefs;
import seedu.estatemate.model.job.Description;
import seedu.estatemate.model.job.Job;
import seedu.estatemate.model.person.Person;
import seedu.estatemate.testutil.PersonBuilder;

public class AddTenantCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTenantCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddTenantCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddTenantCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddTenantCommand addTenantCommand = new AddTenantCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
                AddTenantCommand.MESSAGE_DUPLICATE_TENANT, () -> addTenantCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddTenantCommand addAliceCommand = new AddTenantCommand(alice);
        AddTenantCommand addBobCommand = new AddTenantCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddTenantCommand addAliceCommandCopy = new AddTenantCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddTenantCommand addTenantCommand = new AddTenantCommand(ALICE);
        String expected = AddTenantCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addTenantCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEstateMateFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEstateMateFilePath(Path estateMateFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEstateMate getEstateMate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEstateMate(ReadOnlyEstateMate newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getFilteredJobList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Job> getUnfilteredJobList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int nextJobId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteJobById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markJobById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unmarkJobById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addJob(Job job) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredJobList(Predicate<Job> predicate) {
            requireNonNull(predicate);
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getJobDescriptionById(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Integer> getJobIdsForPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasJobWithDescription(Description description) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editJobById(int id, Description newDescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Boolean isJobCompleted(int jobId) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyEstateMate getEstateMate() {
            return new EstateMate();
        }
    }

}
