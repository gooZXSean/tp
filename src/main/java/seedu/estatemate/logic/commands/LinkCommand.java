package seedu.estatemate.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.estatemate.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.estatemate.model.Model.PREDICATE_SHOW_ALL_TENANTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.estatemate.commons.core.index.Index;
import seedu.estatemate.logic.Messages;
import seedu.estatemate.logic.commands.exceptions.CommandException;
import seedu.estatemate.model.Model;
import seedu.estatemate.model.person.Address;
import seedu.estatemate.model.person.Email;
import seedu.estatemate.model.person.Lease;
import seedu.estatemate.model.person.LeaseAmount;
import seedu.estatemate.model.person.Name;
import seedu.estatemate.model.person.PayDate;
import seedu.estatemate.model.person.Person;
import seedu.estatemate.model.person.Phone;
import seedu.estatemate.model.tag.Tag;

/**
 * Links a Job (identified by jub number) to a Tenant (identified by index)
 */
public class LinkCommand extends Command {
    public static final String COMMAND_WORD = "link";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a job to a tenant. "
            + "Parameters: INDEX (must be a positive integer between 1 - 2147483647) "
            + PREFIX_JOB + "JOB NUMBER (must be a positive integer between 1 - 2147483647) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_JOB + "1 ";

    public static final String MESSAGE_LINK_JOB_SUCCESS = "Job #%1$s linked to %2$s";
    public static final String MESSAGE_ALREADY_LINKED_JOB = "This job is already linked to this tenant!";

    private final Index index;
    private final Integer jobId;

    /**
     * Creates a LinkCommand to link the specified {@code Job} to {@code Person} via index
     * @param index
     * @param job
     */
    public LinkCommand(Index index, Integer job) {
        requireAllNonNull(index, job);
        this.index = index;
        this.jobId = job;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TENANT_DISPLAYED_INDEX);
        }

        if (!model.getUnfilteredJobList().stream().anyMatch(j -> j.getId() == jobId)) {
            // fall back to whole list if filtered list doesn't contain it
            throw new CommandException(Messages.MESSAGE_INVALID_JOB_ID);
        }

        Person personToLink = lastShownList.get(index.getZeroBased());

        // Check for target already having added the target job
        if (personToLink.hasJobId(jobId)) {
            throw new CommandException(MESSAGE_ALREADY_LINKED_JOB);
        }

        Person linkedPerson = createPersonWithJob(personToLink, jobId);
        model.setPerson(personToLink, linkedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_TENANTS);
        return new CommandResult(String.format(MESSAGE_LINK_JOB_SUCCESS, jobId, linkedPerson.getName()));
    }

    private static Person createPersonWithJob(Person personToEdit, Integer job) {
        assert personToEdit != null;

        Name originalName = personToEdit.getName();
        Phone originalPhone = personToEdit.getPhone();
        Email originalEmail = personToEdit.getEmail();
        Address originalAddress = personToEdit.getAddress();
        Lease originalLease = personToEdit.getLease();
        LeaseAmount originalLeaseAmount = personToEdit.getLeaseAmount();
        PayDate originalPayDate = personToEdit.getPayDate();
        Set<Tag> originalTags = personToEdit.getTags();
        List<Integer> newJobs = new ArrayList<>(personToEdit.getJobs());
        newJobs.add(job);

        return new Person(originalName, originalPhone, originalEmail, originalAddress, originalLease,
                originalLeaseAmount, originalPayDate, originalTags, newJobs);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand e = (LinkCommand) other;
        return index.equals(e.index)
                && jobId.equals(e.jobId);
    }
}
