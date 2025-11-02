package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.estatemate.commons.util.ToStringBuilder;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.job.JobContainsKeywordsPredicate;

/**
 * Finds and lists all jobs whose description contains any of the argument keywords. Keyword matching is case
 * insensitive.
 */
public class FindJobCommand extends Command {

    public static final String COMMAND_WORD = "fjob";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all jobs whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " electrical plumbing renovation";

    private final JobContainsKeywordsPredicate predicate;

    public FindJobCommand(JobContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredJobList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW,
                        model.getFilteredJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindJobCommand)) {
            return false;
        }

        FindJobCommand otherFindJobCommand = (FindJobCommand) other;
        return predicate.equals(otherFindJobCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
