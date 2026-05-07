package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.User;

import java.util.UUID;

public interface UserDAO {
    void create(User user) throws DataAccessException;
    User read(UUID id) throws DataAccessException;
    void update(User user) throws DataAccessException;
    void delete(UUID id) throws DataAccessException;
}
