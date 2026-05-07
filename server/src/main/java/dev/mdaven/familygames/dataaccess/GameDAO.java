package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Game;

import java.util.Collection;
import java.util.UUID;

public interface GameDAO {
    void create(Game game);
    Game retrieve(UUID id);
    Collection<Game> retrieveAll();
    void update(Game game);
    void delete(UUID id);
}
