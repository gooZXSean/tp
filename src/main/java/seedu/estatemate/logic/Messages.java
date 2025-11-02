package seedu.estatemate.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.estatemate.logic.parser.Prefix;
import seedu.estatemate.model.job.Job;
import seedu.estatemate.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TENANT_DISPLAYED_INDEX = "Invalid tenant index detected."
            + "Tenant index must exist and must be a positive integer between 1 - 2147483647";
    public static final String MESSAGE_JOBS_LISTED_OVERVIEW = "Listed %1$d jobs(s).";
    public static final String MESSAGE_TENANTS_LISTED_OVERVIEW = "Listed %1$d tenant(s).";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_JOB_ID = "Invalid job number detected. "
                    + "Job number must exist and must be a positive integer between 1 - 2147483647";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName()).append("; Phone: ").append(person.getPhone()).append("; Email: ")
                .append(person.getEmail()).append("; Address: ").append(person.getAddress()).append("; Lease: ")
                .append(person.getLease()).append("; Lease Amount: ").append(person.getLeaseAmount())
                .append("; Pay Date: ").append(person.getPayDate()).append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; Jobs: ");
        person.getJobs().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatJob(Job job) {
        return job.getDescription() + "; Description: ";
    }
}
