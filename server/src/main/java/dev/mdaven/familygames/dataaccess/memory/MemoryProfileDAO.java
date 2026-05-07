package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.DataAccessException;
import dev.mdaven.familygames.dataaccess.ProfileDAO;
import dev.mdaven.familygames.model.Profile;

import java.util.UUID;

public class MemoryProfileDAO extends BaseMemoryDAO<Profile> implements ProfileDAO {
    @Override
    protected UUID fromObject(Profile profile) throws DataAccessException {
        return profile.userId();
    }
}
