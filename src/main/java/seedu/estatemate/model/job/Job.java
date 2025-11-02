package seedu.estatemate.model.job;

import static seedu.estatemate.commons.util.CollectionUtil.requireAllNonNull;

import seedu.estatemate.commons.util.ToStringBuilder;

/**
 * Represents a Job in EstateMate. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Job {
    private final Description description;
    private final int id;
    private boolean isDone;

    /**
     * Every field must be present and not null.
     */
    public Job(Description description, int id) {
        requireAllNonNull(description);
        this.description = description;
        this.id = id;
        this.isDone = false;
    }

    public Description getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public boolean getDone() {
        return isDone;
    }

    /**
     * Returns true if both jobs have the description.
     */
    public boolean isSameJob(Job other) {
        if (other == this) {
            return true;
        }
        return other != null && description.equals(other.description);
    }

    /**
     * Returns true if both jobs have the same id.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Job)) {
            return false;
        }
        Job j = (Job) other;
        return this.id == j.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("description", description)
                .toString();
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
