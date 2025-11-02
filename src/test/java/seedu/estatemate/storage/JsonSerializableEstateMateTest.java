package seedu.estatemate.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.estatemate.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.estatemate.commons.exceptions.IllegalValueException;
import seedu.estatemate.commons.util.JsonUtil;
import seedu.estatemate.model.EstateMate;
import seedu.estatemate.testutil.TypicalPersons;

public class JsonSerializableEstateMateTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableEstateMateTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsEstateMate.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonEstateMate.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonEstateMate.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableEstateMate dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableEstateMate.class).get();
        EstateMate estateMateFromFile = dataFromFile.toModelType();
        EstateMate typicalPersonsEstateMate = TypicalPersons.getTypicalEstateMate();
        assertEquals(estateMateFromFile, typicalPersonsEstateMate);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEstateMate dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableEstateMate.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableEstateMate dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableEstateMate.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableEstateMate.MESSAGE_DUPLICATE_TENANT,
                dataFromFile::toModelType);
    }

}
