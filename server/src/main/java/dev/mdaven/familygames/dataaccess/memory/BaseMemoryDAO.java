package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.DataAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseMemoryDAO<T> {
    protected final Map<UUID, T> storage = new HashMap<>();

    public void create(T entity) throws DataAccessException {
        UUID key = fromObject(entity);
        if (storage.containsKey(key)) {
            throw new DataAccessException("id/key already exists");
        }
        storage.put(key, entity);
    }

    public void delete(UUID id) throws DataAccessException {
        storage.remove(id);
    }

    public T read(UUID id) throws DataAccessException {
        return storage.get(id);
    }

    public void update(T entity) throws DataAccessException {
        UUID key = fromObject(entity);
        if (!storage.containsKey(key)) {
            throw new DataAccessException("id/entity does not yet exist");
        }
        storage.put(key, entity);
    }

    protected abstract UUID fromObject(T entity) throws DataAccessException;
}
