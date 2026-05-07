package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Authentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public abstract class AuthenticationDAOTest {
    protected static final long TIME_DIFFERENCE = 1000;

    protected final long startTime = System.currentTimeMillis();

    protected AuthenticationDAO authenticationDAO;

    protected Authentication[] examples = {
            new Authentication(UUID.randomUUID(), UUID.randomUUID(), startTime),
            new Authentication(UUID.randomUUID(), UUID.randomUUID(), startTime - TIME_DIFFERENCE),
            new Authentication(UUID.randomUUID(), UUID.randomUUID(), startTime - (2 * TIME_DIFFERENCE))
    };

    @BeforeEach
    public void setUp() {
        authenticationDAO = createDAO();
    }

    protected abstract AuthenticationDAO createDAO();

    @AfterEach
    public void cleanUp() throws DataAccessException {
        for (Authentication auth : examples) {
            if (authenticationDAO.read(auth.token()) != null) {
                authenticationDAO.delete(auth.token());
            }
        }
    }

    @Test
    public void testCreateReadSuccess() throws DataAccessException {
        for (Authentication auth : examples) {
            authenticationDAO.create(auth);
            Authentication result = authenticationDAO.read(auth.token());
            Assertions.assertEquals(auth, result, "Read authentication not the same as created auth");
        }
    }

    @Test
    public void testCreateDuplicate() throws DataAccessException {
        for (Authentication auth : examples) {
            authenticationDAO.create(auth);

            DataAccessException thrown = Assertions.assertThrows(DataAccessException.class,
                    () -> authenticationDAO.create(auth));

            Assertions.assertNotNull(thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }

    }

    @Test
    public void testReadNothing() throws DataAccessException {
        for (Authentication auth : examples) {
            Assertions.assertNull(authenticationDAO.read(auth.token()),
                    "Read authentication that hasn't been inserted");
        }
    }

    @Test
    public void testDeleteSuccess() throws DataAccessException {
        for (Authentication auth : examples) {
            authenticationDAO.create(auth);

            Authentication result = authenticationDAO.read(auth.token());
            Assertions.assertEquals(auth, result, "Read authentication not the same as created auth");

            authenticationDAO.delete(auth.token());

            Assertions.assertNull(authenticationDAO.read(auth.token()),
                    "Read authentication that has been deleted");
        }
    }

    @Test
    public void testDeleteNothing() throws DataAccessException {
        for (Authentication auth : examples) {
            Assertions.assertDoesNotThrow(() -> authenticationDAO.delete(auth.token()),
                    "Deleting threw an error");
        }
    }

    @Test
    public void testDeleteOlderThan() throws DataAccessException {
        for (Authentication auth : examples) {
            authenticationDAO.create(auth);
        }

        authenticationDAO.deleteOlderThan(startTime - TIME_DIFFERENCE);

        Assertions.assertEquals(examples[0], authenticationDAO.read(examples[0].token()),
                "Couldn't read authentication from after deletion time");
        Assertions.assertEquals(examples[1], authenticationDAO.read(examples[1].token()),
                "Couldn't read authentication from after deletion time");
        Assertions.assertNull(authenticationDAO.read(examples[2].token()),
                "Read authentication from before deletion time");

        authenticationDAO.deleteOlderThan(startTime - TIME_DIFFERENCE + 1);

        Assertions.assertEquals(examples[0], authenticationDAO.read(examples[0].token()),
                "Couldn't read authentication from after deletion time");
        Assertions.assertNull(authenticationDAO.read(examples[1].token()),
                "Read authentication from before deletion time");
        Assertions.assertNull(authenticationDAO.read(examples[2].token()),
                "Read authentication from before deletion time");
    }
}
