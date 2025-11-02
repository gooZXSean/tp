package seedu.estatemate.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.estatemate.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.estatemate.model.job.exceptions.JobNotFoundException;

/**
 * A list of Jobs Supports a minimal set of list operations. Jobs of duplicate description are allowed.
 */
public class UniqueJobList implements Iterable<Job> {

    private final ObservableList<Job> internalList = FXCollections.observableArrayList();
    private final ObservableList<Job> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains the job as the given argument.
     */
    public boolean contains(Job toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameJob);
    }

    /**
     * Returns true if the list contains a job with the given description.
     */
    public boolean containsDescription(Description description) {
        requireNonNull(description);
        return internalList.stream().anyMatch(j -> j.getDescription().equals(description));
    }

    /**
     * Adds a job to the list.
     */
    public void add(Job toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Removes the job from the list. The job must exist in the list.
     */
    public void remove(Job toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobNotFoundException();
        }
    }

    /**
     * Marks the job provided as complete. The job must be a valid job (non-null).
     *
     * @param toMark
     */
    public void mark(Job toMark) {
        requireNonNull(toMark);
        int index = internalList.indexOf(toMark);
        if (index == -1) {
            throw new JobNotFoundException();
        }
        toMark.setDone(true);
    }

    /**
     * Marks the job provided as incomplete. The job must be a valid job (non-null).
     *
     * @param toMark
     */
    public void unmark(Job toMark) {
        requireNonNull(toMark);
        int index = internalList.indexOf(toMark);
        if (index == -1) {
            throw new JobNotFoundException();
        }
        toMark.setDone(false);
    }

    /**
     * Replaces the contents of this list with {@code jobs}. {@code jobs} must not contain duplicate jobs.
     */
    public void setJobs(List<Job> jobs) {
        requireNonNull(jobs);
        internalList.setAll(jobs);
    }

    /**
     * Replaces the {@code target} with {@code editedJob}
     */
    public void setJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        internalList.set(index, editedJob);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Job> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Job> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueJobList)) {
            return false;
        }

        UniqueJobList otherUniqueJobList = (UniqueJobList) other;
        return internalList.equals(otherUniqueJobList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean jobsAreUnique(List<Job> jobs) {
        for (int i = 0; i < jobs.size() - 1; i++) {
            for (int j = i + 1; j < jobs.size(); j++) {
                if (jobs.get(i).isSameJob(jobs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
