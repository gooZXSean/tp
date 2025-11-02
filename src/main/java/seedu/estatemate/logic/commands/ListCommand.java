package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.model.Model.PREDICATE_SHOW_ALL_TENANTS;

import seedu.estatemate.logic.Messages;
import seedu.estatemate.model.Model;

/**
 * Lists all tenants in EstateMate to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TENANTS);
        return new CommandResult(
                String.format(Messages.MESSAGE_TENANTS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
