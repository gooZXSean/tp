package seedu.estatemate.logic.parser;

import static seedu.estatemate.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.logic.commands.DeleteTenantCommand;
import seedu.estatemate.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteTenantCommand object
 */
public class DeleteTenantCommandParser implements Parser<DeleteTenantCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTenantCommand and returns a
     * DeleteTenantCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteTenantCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty() || !trimmed.matches("\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTenantCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTenantCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX, pe);
        }
    }

}
