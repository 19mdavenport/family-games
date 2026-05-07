package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Game;

import java.util.Collection;
import java.util.UUID;

public interface GameDAO {
    void create(Game game) throws DataAccessException;
    Game read(UUID id) throws DataAccessException;
    Collection<Game> readAll() throws DataAccessException;
    void update(Game game) throws DataAccessException;
    void delete(UUID id) throws DataAccessException;
}
