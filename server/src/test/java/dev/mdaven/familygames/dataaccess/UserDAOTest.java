package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public abstract class UserDAOTest {
    protected UserDAO userDAO;

    protected User[] examples = {
        new User(UUID.randomUUID(), "player1@example.com", "$uper$ecur5"),
        new User(UUID.randomUUID(), "other-player@mdaven.dev", "pa$$w0rd"),
    };

    @BeforeEach
    public void setUp() {
        userDAO = createDAO();
    }

    protected abstract UserDAO createDAO();

    @AfterEach
    public void cleanUp() throws DataAccessException {
        for (User user : examples) {
            if (userDAO.read(user.id()) != null) {
                userDAO.delete(user.id());
            }
        }
    }

    @Test
    public void testCreateReadSuccess() throws DataAccessException {
        for (User user : examples) {
            userDAO.create(user);
            User result = userDAO.read(user.id());
            Assertions.assertEquals(user, result, "Read user not the same as created user");
        }
    }

    @Test
    public void testCreateDuplicate() throws DataAccessException {
        for (User user : examples) {
            userDAO.create(user);

            DataAccessException thrown = Assertions.assertThrows(DataAccessException.class,
                    () -> userDAO.create(user));

            Assertions.assertEquals("id/key already exists", thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }

    }

    @Test
    public void testReadNothing() throws DataAccessException {
        for (User user : examples) {
            Assertions.assertNull(userDAO.read(user.id()), "Read user that hasn't been inserted");
        }
    }

    @Test
    public void testReadEmailSuccess() throws DataAccessException {
        for (User user : examples) {
            userDAO.create(user);
            User result = userDAO.readFromEmail(user.email());
            Assertions.assertEquals(user, result, "Read user not the same as created user");
        }
    }

    @Test
    public void testReadEmailNothing() throws DataAccessException {
        for (User user : examples) {
            Assertions.assertNull(userDAO.readFromEmail(user.email()), "Read user that hasn't been inserted");
        }
    }

    @Test
    public void testUpdateSuccess() throws DataAccessException {
        userDAO.create(examples[0]);
        User updated = new User(examples[0].id(), examples[0].email(), "changed_my_pa$$w0rd");
        userDAO.update(updated);
        User result = userDAO.read(examples[0].id());

        Assertions.assertNotEquals(examples[0], result, "Read user the same as created user after update");
        Assertions.assertEquals(updated, result, "Read user not the same as updated user");
    }

    @Test
    public void testUpdateNothing() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class,
                () -> userDAO.update(examples[0]),
                "Updated user that hadn't been created");
    }

    @Test
    public void testDeleteSuccess() throws DataAccessException {
        for (User user : examples) {
            userDAO.create(user);

            User result = userDAO.read(user.id());
            Assertions.assertEquals(user, result, "Read user not the same as created user");

            userDAO.delete(user.id());

            Assertions.assertNull(userDAO.read(user.id()),
                    "Read user that has been deleted");
        }
    }

    @Test
    public void testDeleteNothing() throws DataAccessException {
        for (User user : examples) {
            Assertions.assertDoesNotThrow(() -> userDAO.delete(user.id()),
                    "Deleting threw an error");
        }
    }

}
