package seedu.estatemate.logic.parser;

import static seedu.estatemate.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.estatemate.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.commands.MarkJobCommand;

public class MarkJobCommandParserTest {

    private final MarkJobCommandParser parser = new MarkJobCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1", new MarkJobCommand(1));
    }

    @Test
    public void parse_outOfRange_failure() {
        assertParseFailure(parser, "2147483648", ParserUtil.MESSAGE_INVALID_JOB);
    }
}
