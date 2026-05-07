package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Profile;

import java.util.UUID;

public interface ProfileDAO {
    void create(Profile profile) throws DataAccessException;
    Profile read(UUID userId) throws DataAccessException;
    void update(Profile profile) throws DataAccessException;
    void delete(UUID userId) throws DataAccessException;
}
