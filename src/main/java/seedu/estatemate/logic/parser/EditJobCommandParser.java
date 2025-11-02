
package seedu.estatemate.logic.parser;

import static seedu.estatemate.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.estatemate.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.estatemate.logic.commands.EditJobCommand;
import seedu.estatemate.logic.parser.exceptions.ParseException;
import seedu.estatemate.model.job.Description;

/**
 * Parses input arguments and creates a new EditJobCommand object.
 */
public class EditJobCommandParser implements Parser<EditJobCommand> {

    @Override
    public EditJobCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION);
        if (!map.getValue(PREFIX_DESCRIPTION).isPresent() || map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditJobCommand.MESSAGE_USAGE));
        }

        final int id;
        try {
            id = ParserUtil.parseJob(map.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_JOB, pe);
        }

        Description desc = ParserUtil.parseDescription(map.getValue(PREFIX_DESCRIPTION).get());
        return new EditJobCommand(id, desc);
    }
}
