package seedu.estatemate.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.estatemate.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tenant's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String lease;
    private final String leaseAmount;
    private final String payDate;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<Integer> jobs = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("lease") String lease,
                             @JsonProperty("lease amount") String leaseAmount,
                             @JsonProperty("pay date") String payDate,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("jobs") List<Integer> jobs) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.lease = lease;
        this.leaseAmount = leaseAmount;
        this.payDate = payDate;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (jobs != null) {
            this.jobs.addAll(jobs);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        lease = source.getLease().value;
        leaseAmount = source.getLeaseAmount().value;
        payDate = source.getPayDate().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        jobs.addAll(source.getJobs());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (lease == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Lease.class.getSimpleName()));
        }
        if (!Lease.isValidLease(lease)) {
            throw new IllegalValueException(Lease.MESSAGE_CONSTRAINTS);
        }
        final Lease modelLease = new Lease(lease);

        if (leaseAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LeaseAmount.class.getSimpleName()));
        }
        if (!LeaseAmount.isValidLeaseAmount(leaseAmount)) {
            throw new IllegalValueException(LeaseAmount.MESSAGE_CONSTRAINTS);
        }
        final LeaseAmount modelLeaseAmount = new LeaseAmount(leaseAmount);

        if (payDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PayDate.class.getSimpleName()));
        }
        if (!PayDate.isValidPayDate(payDate)) {
            throw new IllegalValueException(PayDate.MESSAGE_CONSTRAINTS);
        }
        final PayDate modelPayDate = new PayDate(payDate);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<Integer> modelJobs = new ArrayList<>(jobs);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelLease, modelLeaseAmount,
                modelPayDate, modelTags, modelJobs);
    }

}
