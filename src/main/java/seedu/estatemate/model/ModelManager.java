package seedu.estatemate.model;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.estatemate.commons.core.GuiSettings;
import seedu.estatemate.commons.core.LogsCenter;
import seedu.estatemate.model.job.Description;
import seedu.estatemate.model.job.Job;
import seedu.estatemate.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EstateMate estateMate;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Job> filteredJobs;

    /**
     * Initializes a ModelManager with the given estateMate and userPrefs.
     */
    public ModelManager(ReadOnlyEstateMate estateMate, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(estateMate, userPrefs);

        logger.fine("Initializing with address book: " + estateMate + " and user prefs " + userPrefs);

        this.estateMate = new EstateMate(estateMate);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.estateMate.getPersonList());
        this.filteredJobs = new FilteredList<>(this.estateMate.getJobList());
    }

    public ModelManager() {
        this(new EstateMate(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getEstateMateFilePath() {
        return userPrefs.getEstateMateFilePath();
    }

    @Override
    public void setEstateMateFilePath(Path estateMateFilePath) {
        requireNonNull(estateMateFilePath);
        userPrefs.setEstateMateFilePath(estateMateFilePath);
    }

    //=========== EstateMate ================================================================================

    @Override
    public ReadOnlyEstateMate getEstateMate() {
        return estateMate;
    }

    @Override
    public void setEstateMate(ReadOnlyEstateMate estateMate) {
        this.estateMate.resetData(estateMate);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return estateMate.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        estateMate.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        estateMate.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_TENANTS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        estateMate.setPerson(target, editedPerson);
    }

    // === JOBS ===
    @Override
    public ObservableList<Job> getFilteredJobList() {
        return filteredJobs;
    }

    @Override
    public ObservableList<Job> getUnfilteredJobList() {
        return estateMate.getJobList();
    }

    @Override
    public void updateFilteredJobList(Predicate<Job> predicate) {
        requireNonNull(predicate);
        filteredJobs.setPredicate(predicate);
    }

    @Override
    public void addJob(Job job) {
        requireNonNull(job);
        estateMate.addJob(job);
        // Show all by default after a mutation (matches AB3 pattern)
        updateFilteredJobList(Model.PREDICATE_SHOW_ALL_JOBS);
    }

    @Override
    public void deleteJobById(int id) {
        estateMate.removeJobById(id);

        // Also remove reference to job from all tenants
        for (Person p : estateMate.getPersonList()) {
            List<Integer> newJobs = new ArrayList<>(p.getJobs());
            boolean changed = newJobs.removeIf(j -> j == id);
            if (changed) {
                Person updated = new Person(
                        p.getName(),
                        p.getPhone(),
                        p.getEmail(),
                        p.getAddress(),
                        p.getLease(),
                        p.getLeaseAmount(),
                        p.getPayDate(),
                        p.getTags(),
                        newJobs
                );
                setPerson(p, updated);
            }
        }

        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TENANTS);
        updateFilteredJobList(Model.PREDICATE_SHOW_ALL_JOBS);

        // Also remove reference to job from all tenants
        for (Person p : estateMate.getPersonList()) {
            List<Integer> newJobs = new ArrayList<>(p.getJobs());
            boolean changed = newJobs.removeIf(j -> j == id);
            if (changed) {
                Person updated = new Person(
                        p.getName(),
                        p.getPhone(),
                        p.getEmail(),
                        p.getAddress(),
                        p.getLease(),
                        p.getLeaseAmount(),
                        p.getPayDate(),
                        p.getTags(),
                        newJobs
                );
                setPerson(p, updated);
            }
        }

        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_TENANTS);
        updateFilteredJobList(Model.PREDICATE_SHOW_ALL_JOBS);
    }

    @Override
    public int nextJobId() {
        return estateMate.nextJobId();
    }

    @Override
    public void markJobById(int id) {
        estateMate.markJobById(id);
    }

    @Override
    public void unmarkJobById(int id) {
        estateMate.unmarkJobById(id);
    }

    @Override
    public String getJobDescriptionById(int jobId) {
        for (Job job : getUnfilteredJobList()) {
            if (job.getId() == jobId) {
                return job.getDescription().toString();
            }
        }
        return null;
    }

    @Override
    public Boolean isJobCompleted(int jobId) {
        for (Job job : estateMate.getJobList()) {
            if (job.getId() == jobId) {
                return job.getDone();
            }
        }
        return false;
    }

    @Override
    public List<Integer> getJobIdsForPerson(Person person) {
        return new ArrayList<>(person.getJobs());
    }

    @Override
    public boolean hasJobWithDescription(Description description) {
        requireNonNull(description);
        return estateMate.hasJobWithDescription(description);
    }

    @Override
    public void editJobById(int id, Description newDescription) {
        Job existing = estateMate.getJobList().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
        if (existing == null) {
            return;
        }
        boolean done = Boolean.TRUE.equals(isJobCompleted(id));
        Job updated = new Job(newDescription, id);
        updated.setDone(done);
        estateMate.setJobById(id, updated);
        updateFilteredJobList(Model.PREDICATE_SHOW_ALL_JOBS);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedEstateMate}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager otherModelManager)) {
            return false;
        }

        return estateMate.equals(otherModelManager.estateMate)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
