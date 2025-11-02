package seedu.estatemate.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.estatemate.commons.exceptions.IllegalValueException;
import seedu.estatemate.model.EstateMate;
import seedu.estatemate.model.ReadOnlyEstateMate;
import seedu.estatemate.model.job.Job;
import seedu.estatemate.model.person.Person;

/**
 * An Immutable EstateMate that is serializable to JSON format.
 */
@JsonRootName(value = "estatemate")
class JsonSerializableEstateMate {

    public static final String MESSAGE_DUPLICATE_TENANT = "Tenants list contains duplicate tenant(s).";
    public static final String MESSAGE_DUPLICATE_JOB = "Jobs list contains duplicate job id(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedJob> jobs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEstateMate} with the given persons and jobs.
     */
    @JsonCreator
    public JsonSerializableEstateMate(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                      @JsonProperty("jobs") List<JsonAdaptedJob> jobs) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (jobs != null) {
            this.jobs.addAll(jobs);
        }
    }

    /**
     * Converts a given {@code ReadOnlyEstateMate} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEstateMate}.
     */
    public JsonSerializableEstateMate(ReadOnlyEstateMate source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        jobs.addAll(source.getJobList().stream().map(JsonAdaptedJob::new).collect(Collectors.toList()));
    }

    /**
     * Converts this EstateMate object into the model's {@code EstateMate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EstateMate toModelType() throws IllegalValueException {
        EstateMate estateMate = new EstateMate();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (estateMate.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TENANT);
            }
            estateMate.addPerson(person);
        }
        for (JsonAdaptedJob jsonAdaptedJob : jobs) {
            Job modelJob = jsonAdaptedJob.toModelType();
            if (estateMate.hasJobId(modelJob.getId())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JOB);
            }
            estateMate.addJob(modelJob);
        }
        return estateMate;
    }

}
