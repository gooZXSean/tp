package seedu.estatemate.model.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.estatemate.model.job.exceptions.JobNotFoundException;

public class UniqueJobListTest {

    @Test
    public void add_twoJobsSameDescription_allowed() {
        UniqueJobList list = new UniqueJobList();
        Job j1 = new Job(new Description("Leaking aircon"), 1);
        Job j2 = new Job(new Description("Leaking aircon"), 2);

        list.add(j1);
        list.add(j2);

        assertEquals(2, list.asUnmodifiableObservableList().size());
        assertIterableEquals(List.of(j1, j2), list.asUnmodifiableObservableList());
    }

    @Test
    public void setJob_allowsReplacingWithSameDescriptionAsAnotherJob() {
        UniqueJobList list = new UniqueJobList();
        Job j1 = new Job(new Description("A"), 1);
        Job j2 = new Job(new Description("B"), 2);
        list.add(j1);
        list.add(j2);

        Job j1Edited = new Job(new Description("B"), 1);
        list.setJob(j1, j1Edited);

        assertIterableEquals(List.of(j1Edited, j2), list.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nonExisting_throwsJobNotFoundException() {
        UniqueJobList list = new UniqueJobList();
        Job j1 = new Job(new Description("A"), 1);
        assertThrows(JobNotFoundException.class, () -> list.remove(j1));
    }
}
