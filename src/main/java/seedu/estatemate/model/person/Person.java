package seedu.estatemate.model.person;

import static seedu.estatemate.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.estatemate.commons.util.ToStringBuilder;
import seedu.estatemate.model.tag.Tag;

/**
 * Represents a tenant in EstateMate.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Lease lease;
    private final LeaseAmount leaseAmount;
    private final PayDate payDate;
    private final Set<Tag> tags = new HashSet<>();
    private final List<Integer> jobs = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Lease lease, LeaseAmount leaseAmount,
                  PayDate payDate, Set<Tag> tags, List<Integer> jobs) {
        requireAllNonNull(name, phone, email, address, lease, leaseAmount, payDate, tags, jobs);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.lease = lease;
        this.leaseAmount = leaseAmount;
        this.payDate = payDate;
        this.tags.addAll(tags);
        this.jobs.addAll(jobs);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Lease getLease() {
        return lease;
    }

    public LeaseAmount getLeaseAmount() {
        return leaseAmount;
    }

    public PayDate getPayDate() {
        return payDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Integer> getJobs() {
        return Collections.unmodifiableList(jobs);
    }

    /**
     * Checks if a person has a certain job by id.
     * @param jobId to search for
     * @return true if jobs contains jobId, false otherwise
     */
    public boolean hasJobId(Integer jobId) {
        return this.jobs.contains(jobId);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && lease.equals(otherPerson.lease)
                && leaseAmount.equals(otherPerson.leaseAmount)
                && payDate.equals(otherPerson.payDate)
                && tags.equals(otherPerson.tags)
                && jobs.equals(otherPerson.jobs);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, lease, leaseAmount, payDate, tags, jobs);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("lease", lease)
                .add("leaseAmount", leaseAmount)
                .add("payDate", payDate)
                .add("tags", tags)
                .add("jobs", jobs)
                .toString();
    }

}
