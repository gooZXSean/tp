package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.estatemate.commons.util.ToStringBuilder;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.logic.commands.exceptions.CommandException;
import seedu.estatemate.model.Model;

/**
 * Deletes a Job identified using its displayed id number from EstateMate.
 */
public class DeleteJobCommand extends Command {

    public static final String COMMAND_WORD = "djob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job identified by the id number displayed in the job list.\n"
            + "Parameters: id (must be a positive integer between 1 - 2147483647) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_JOB_SUCCESS = "Deleted job successfully: #%d";

    private final Integer targetId;

    public DeleteJobCommand(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.getUnfilteredJobList().stream().anyMatch(j -> j.getId() == targetId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_ID);
        }
        model.deleteJobById(targetId);
        return new CommandResult(String.format(MESSAGE_DELETE_JOB_SUCCESS, targetId));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteJobCommand)) {
            return false;
        }

        DeleteJobCommand otherDeleteJobCommand = (DeleteJobCommand) other;
        return targetId.equals(otherDeleteJobCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}

