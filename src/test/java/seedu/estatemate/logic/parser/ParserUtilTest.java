package seedu.estatemate.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.estatemate.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.estatemate.testutil.Assert.assertThrows;
import static seedu.estatemate.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.estatemate.logic.parser.exceptions.ParseException;
import seedu.estatemate.model.person.Address;
import seedu.estatemate.model.person.Email;
import seedu.estatemate.model.person.Lease;
import seedu.estatemate.model.person.LeaseAmount;
import seedu.estatemate.model.person.Name;
import seedu.estatemate.model.person.PayDate;
import seedu.estatemate.model.person.Phone;
import seedu.estatemate.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+413612apple";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_LEASE = " ";
    private static final String INVALID_LEASE_AMOUNT = " ";
    private static final String INVALID_PAY_DATE = "2025-13-40";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_LEASE = "2025-01-01 2030-01-02";
    private static final String VALID_LEASE_AMOUNT = "1000.00";
    private static final String VALID_PAY_DATE = "2025-12-12";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseLease_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLease((String) null));
    }

    @Test
    public void parseLease_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLease(INVALID_LEASE));
    }

    @Test
    public void parseLease_validValueWithoutWhitespace_returnsLease() throws Exception {
        Lease expectedLease = new Lease(VALID_LEASE);
        assertEquals(expectedLease, ParserUtil.parseLease(VALID_LEASE));
    }

    @Test
    public void parseLease_validValueWithWhitespace_returnsTrimmedLease() throws Exception {
        String leaseWithWhitespace = WHITESPACE + VALID_LEASE + WHITESPACE;
        Lease expectedLease = new Lease(VALID_LEASE);
        assertEquals(expectedLease, ParserUtil.parseLease(leaseWithWhitespace));
    }

    @Test
    public void parseLeaseAmount_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLeaseAmount((String) null));
    }

    @Test
    public void parseLeaseAmount_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLeaseAmount(INVALID_LEASE_AMOUNT));
    }

    @Test
    public void parseLeaseAmount_validValueWithoutWhitespace_returnsLeaseAmount() throws Exception {
        LeaseAmount expectedLeaseAmount = new LeaseAmount(VALID_LEASE_AMOUNT);
        assertEquals(expectedLeaseAmount, ParserUtil.parseLeaseAmount(VALID_LEASE_AMOUNT));
    }

    @Test
    public void parseLeaseAmount_validValueWithWhitespace_returnsTrimmedLeaseAmount() throws Exception {
        String leaseAmountWithWhitespace = WHITESPACE + VALID_LEASE_AMOUNT + WHITESPACE;
        LeaseAmount expectedLeaseAmount = new LeaseAmount(VALID_LEASE_AMOUNT);
        assertEquals(expectedLeaseAmount, ParserUtil.parseLeaseAmount(leaseAmountWithWhitespace));
    }

    @Test
    public void parsePayDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePayDate((String) null));
    }

    @Test
    public void parsePayDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePayDate(INVALID_PAY_DATE));
    }

    @Test
    public void parsePayDate_validValueWithoutWhitespace_returnsPayDate() throws Exception {
        PayDate expectedPayDate = new PayDate(VALID_PAY_DATE);
        assertEquals(expectedPayDate, ParserUtil.parsePayDate(VALID_PAY_DATE));
    }

    @Test
    public void parsePayDate_validValueWithWhitespace_returnsTrimmedPayDate() throws Exception {
        String payDateWithWhitespace = WHITESPACE + VALID_PAY_DATE + WHITESPACE;
        PayDate expectedPayDate = new PayDate(VALID_PAY_DATE);
        assertEquals(expectedPayDate, ParserUtil.parsePayDate(payDateWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
