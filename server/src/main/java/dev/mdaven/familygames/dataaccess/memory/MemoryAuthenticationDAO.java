package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.AuthenticationDAO;
import dev.mdaven.familygames.dataaccess.DataAccessException;
import dev.mdaven.familygames.model.Authentication;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthenticationDAO extends BaseMemoryDAO<Authentication> implements AuthenticationDAO {

    @Override
    public void deleteOlderThan(long timestamp) throws DataAccessException {
        Iterator<Map.Entry<UUID, Authentication>> it = storage.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Authentication> entry = it.next();
            if (entry.getValue().createdAt() < timestamp) {
                it.remove();
            }
        }
    }

    @Override
    protected UUID fromObject(Authentication auth) throws DataAccessException {
        return auth.userId();
    }
}
