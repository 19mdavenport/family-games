package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Profile;

import java.util.UUID;

public interface ProfileDAO {
    void create(Profile profile);
    Profile read(UUID userId);
    void update(Profile profile);
    void delete(UUID userId);
}
