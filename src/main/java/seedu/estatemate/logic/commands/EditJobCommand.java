package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.logic.Messages.MESSAGE_INVALID_JOB_ID;
import static seedu.estatemate.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.estatemate.commons.util.ToStringBuilder;
import seedu.estatemate.logic.commands.exceptions.CommandException;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.job.Description;

/**
 * Edits the description of an existing job identified by its id.
 */
public class EditJobCommand extends Command {

    public static final String COMMAND_WORD = "ejob";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of the job with the given ID.\n"
            + "Parameters: ID (must be a positive integer between 1 - 2147483647) " + PREFIX_DESCRIPTION
            + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " 5 " + PREFIX_DESCRIPTION + "Repair corridor lights";

    public static final String MESSAGE_SUCCESS = "Edited job #%1$d: %2$s";

    private final int targetId;
    private final Description newDescription;

    /**
     * @param targetId       of the job to edit
     * @param newDescription to edit the job with
     */
    public EditJobCommand(int targetId, Description newDescription) {
        this.targetId = targetId;
        this.newDescription = newDescription;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Ensure id exists
        requireNonNull(model);
        if (!model.getUnfilteredJobList().stream().anyMatch(j -> j.getId() == targetId)) {
            throw new CommandException(MESSAGE_INVALID_JOB_ID);
        }


        model.editJobById(targetId, newDescription);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetId, newDescription));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditJobCommand)) {
            return false;
        }
        EditJobCommand e = (EditJobCommand) other;
        return targetId == e.targetId && newDescription.equals(e.newDescription);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .add("newDescription", newDescription)
                .toString();
    }
}
