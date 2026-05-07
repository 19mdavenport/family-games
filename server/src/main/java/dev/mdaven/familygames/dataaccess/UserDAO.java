package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.User;

import java.util.UUID;

public interface UserDAO {
    void create(User user);
    User retrieve(UUID id);
    void update(User user);
    void delete(UUID id);
}
