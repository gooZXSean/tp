package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.estatemate.model.EstateMate;
import seedu.estatemate.model.Model;

/**
 * Clears all EstateMate data.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EstateMate has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEstateMate(new EstateMate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
