package seedu.estatemate.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.estatemate.logic.parser.CliSyntax.PREFIX_JOB;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.logic.commands.LinkCommand;
import seedu.estatemate.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns an LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LinkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (!jobPrefixPresent(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_JOB);

        // parse tenant index
        final Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_INDEX, pe);
        }

        // parse job number
        final Integer jobNumber;
        try {
            jobNumber = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());
        } catch (ParseException pe) {
            throw new ParseException(ParserUtil.MESSAGE_INVALID_JOB, pe);
        }

        return new LinkCommand(index, jobNumber);
    }

    private static boolean jobPrefixPresent(String args) {
        return args.contains(PREFIX_JOB.toString());
    }
}
