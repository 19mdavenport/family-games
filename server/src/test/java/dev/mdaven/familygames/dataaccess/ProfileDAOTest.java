package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Profile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

public abstract class ProfileDAOTest {
    protected ProfileDAO profileDAO;

    protected Profile[] examples = {
        new Profile(UUID.randomUUID(), "username1"),
        new Profile(UUID.randomUUID(), "other player"),
    };

    @BeforeEach
    public void setUp() {
        profileDAO = createDAO();
    }

    protected abstract ProfileDAO createDAO();

    @AfterEach
    public void cleanUp() throws DataAccessException {
        for (Profile profile : examples) {
            if (profileDAO.read(profile.userId()) != null) {
                profileDAO.delete(profile.userId());
            }
        }
    }

    @Test
    public void testCreateReadSuccess() throws DataAccessException {
        for (Profile profile : examples) {
            profileDAO.create(profile);
            Profile result = profileDAO.read(profile.userId());
            Assertions.assertEquals(profile, result, "Read profile not the same as created profile");
        }
    }

    @Test
    public void testCreateDuplicate() throws DataAccessException {
        for (Profile profile : examples) {
            profileDAO.create(profile);

            DataAccessException thrown = Assertions.assertThrows(DataAccessException.class,
                    () -> profileDAO.create(profile));

            Assertions.assertEquals("id/key already exists", thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }

    }

    @Test
    public void testReadNothing() throws DataAccessException {
        for (Profile profile : examples) {
            Assertions.assertNull(profileDAO.read(profile.userId()), "Read profile that hasn't been inserted");
        }
    }

    @Test
    public void testUpdateSuccess() throws DataAccessException {
        profileDAO.create(examples[0]);
        Profile updated = new Profile(examples[0].userId(), "different name");
        profileDAO.update(updated);
        Profile result = profileDAO.read(examples[0].userId());

        Assertions.assertNotEquals(examples[0], result, "Read profile the same as created profile after update");
        Assertions.assertEquals(updated, result, "Read profile not the same as updated profile");
    }

    @Test
    public void testUpdateNothing() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class,
                () -> profileDAO.update(examples[0]),
                "Updated profile that hadn't been created");
    }

    @Test
    public void testDeleteSuccess() throws DataAccessException {
        for (Profile profile : examples) {
            profileDAO.create(profile);

            Profile result = profileDAO.read(profile.userId());
            Assertions.assertEquals(profile, result, "Read profile not the same as created profile");

            profileDAO.delete(profile.userId());

            Assertions.assertNull(profileDAO.read(profile.userId()),
                    "Read profile that has been deleted");
        }
    }

    @Test
    public void testDeleteNothing() throws DataAccessException {
        for (Profile profile : examples) {
            Assertions.assertDoesNotThrow(() -> profileDAO.delete(profile.userId()),
                    "Deleting threw an error");
        }
    }

}
