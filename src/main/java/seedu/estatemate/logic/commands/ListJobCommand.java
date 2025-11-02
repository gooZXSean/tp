package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;

/**
 * Lists all jobs to the user.
 */
public class ListJobCommand extends Command {

    public static final String COMMAND_WORD = "ljob";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW,
                        model.getFilteredJobList().size()));
    }
}
