package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Authentication;

import java.util.Collection;
import java.util.UUID;

public interface AuthenticationDAO {
    void create(Authentication auth) throws DataAccessException;
    Authentication read(UUID token) throws DataAccessException;
    void delete(UUID token) throws DataAccessException;
    void deleteOlderThan(long timestamp) throws DataAccessException;
}
