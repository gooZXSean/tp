package seedu.estatemate.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.commons.util.StringUtil;
import seedu.estatemate.logic.parser.exceptions.ParseException;
import seedu.estatemate.model.job.Description;
import seedu.estatemate.model.person.Address;
import seedu.estatemate.model.person.Email;
import seedu.estatemate.model.person.Lease;
import seedu.estatemate.model.person.LeaseAmount;
import seedu.estatemate.model.person.Name;
import seedu.estatemate.model.person.PayDate;
import seedu.estatemate.model.person.Phone;
import seedu.estatemate.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX =
            "Tenant index must be a non-zero unsigned integer between 1 - 2147483647";
    public static final String MESSAGE_INVALID_JOB =
            "Job number must be a non-zero unsigned integer between between 1 - 2147483647";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String lease} into a {@code Lease}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lease} is invalid.
     */
    public static Lease parseLease(String lease) throws ParseException {
        requireNonNull(lease);
        String trimmedLease = lease.trim();
        if (!Lease.isValidLease(trimmedLease)) {
            throw new ParseException(Lease.MESSAGE_CONSTRAINTS);
        }
        return new Lease(trimmedLease);
    }

    /**
     * Parses a {@code String leaseAmount} into an {@code LeaseAmount}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code leaseAmount} is invalid.
     */
    public static LeaseAmount parseLeaseAmount(String leaseAmount) throws ParseException {
        requireNonNull(leaseAmount);
        String trimmedLeaseAmount = leaseAmount.trim();
        if (!LeaseAmount.isValidLeaseAmount(trimmedLeaseAmount)) {
            throw new ParseException(LeaseAmount.MESSAGE_CONSTRAINTS);
        }
        return new LeaseAmount(trimmedLeaseAmount);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String payDate} into a {@code PayDate}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code payDate} is invalid.
     */
    public static PayDate parsePayDate(String payDate) throws ParseException {
        requireNonNull(payDate);
        String trimmedPayDate = payDate.trim();
        if (!PayDate.isValidPayDate(trimmedPayDate)) {
            throw new ParseException(PayDate.MESSAGE_CONSTRAINTS);
        }
        return new PayDate(trimmedPayDate);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String description} into an {@code Description}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code Description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        final String trimmed = description.trim();
        if (!Description.isValidDescription(trimmed)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmed);
    }

    /**
     * Parses a {@code String job index} into an {@code Integer} Leading and trailing whitespaces will be trimmed
     *
     * @return Integer represented by job String
     * @throws ParseException if the returned integer is negative
     */
    public static Integer parseJob(String job) throws ParseException {
        requireNonNull(job);
        String trimmedJob = job.trim();

        // temporary check for non-zero unsigned integer, to change in future
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedJob)) {
            throw new ParseException(MESSAGE_INVALID_JOB);
        }
        return Integer.valueOf(job);
    }

    /**
     * Parses {@code Collection<String> jobs} into a {@code List<Integer>}.
     */
    public static List<Integer> parseJobs(Collection<String> jobs) throws ParseException {
        requireNonNull(jobs);
        final List<Integer> jobList = new ArrayList<>();
        for (String jobIndex : jobs) {
            jobList.add(parseJob(jobIndex));
        }
        return jobList;
    }
}
