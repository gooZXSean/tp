package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.commons.util.ToStringBuilder;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.logic.commands.exceptions.CommandException;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.person.Person;

/**
 * Deletes a tenant identified using its displayed index from EstateMate.
 */
public class DeleteTenantCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tenant using the index number displayed in the tenant list.\n"
            + "Parameters: id (must be a positive integer between 1 - 2147483647) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TENANT_SUCCESS = "Deleted tenant successfully: %1$s";

    private final Index targetIndex;

    public DeleteTenantCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TENANT_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTenantCommand)) {
            return false;
        }

        DeleteTenantCommand otherDeleteTenantCommand = (DeleteTenantCommand) other;
        return targetIndex.equals(otherDeleteTenantCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
