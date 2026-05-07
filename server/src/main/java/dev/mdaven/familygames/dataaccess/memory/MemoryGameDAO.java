package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.DataAccessException;
import dev.mdaven.familygames.dataaccess.GameDAO;
import dev.mdaven.familygames.model.Game;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MemoryGameDAO extends BaseMemoryDAO<Game> implements GameDAO {
    @Override
    public Collection<Game> readAll() throws DataAccessException {
        return Collections.unmodifiableCollection(storage.values());
    }

    @Override
    protected UUID fromObject(Game game) throws DataAccessException {
        return game.id();
    }
}
