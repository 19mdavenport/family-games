package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Authentication;

import java.util.Collection;
import java.util.UUID;

public interface AuthenticationDAO {
    void create(Authentication auth);
    Authentication read(UUID token);
    void delete(UUID token);
    void deleteOlderThan(long timestamp);
}
