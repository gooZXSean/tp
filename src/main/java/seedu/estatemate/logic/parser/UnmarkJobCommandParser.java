package seedu.estatemate.logic.parser;

import static seedu.estatemate.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.estatemate.logic.commands.UnmarkJobCommand;
import seedu.estatemate.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkJobCommand object
 */
public class UnmarkJobCommandParser implements Parser<UnmarkJobCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkJobCommand
     * and returns a UnmarkJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnmarkJobCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty() || !trimmed.matches("\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkJobCommand.MESSAGE_USAGE));
        }
        try {
            Integer jobNumber = ParserUtil.parseJob(trimmed);
            return new UnmarkJobCommand(jobNumber);
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_JOB, pe);
        }
    }
}
