package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.DataAccessException;
import dev.mdaven.familygames.dataaccess.UserDAO;
import dev.mdaven.familygames.model.User;

import java.util.UUID;

public class MemoryUserDAO extends BaseMemoryDAO<User> implements UserDAO {
    @Override
    public User readFromEmail(String email) {
        for (User user : storage.values()) {
            if(user.email().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    protected UUID fromObject(User user) throws DataAccessException {
        return user.id();
    }
}
