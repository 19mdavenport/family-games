package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.game.card.rummy.sevencard.SevenCardRummyGame;
import dev.mdaven.familygames.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

public abstract class GameDAOTest {
    protected GameDAO gameDAO;

    protected Game[] examples = {
            new Game(UUID.randomUUID(), "Game1", new SevenCardRummyGame()),
            new Game(UUID.randomUUID(), "second game", new SevenCardRummyGame()),
            new Game(UUID.randomUUID(), "weird other something", new SevenCardRummyGame()),
    };

    @BeforeEach
    public void setUp() {
        gameDAO = createDAO();
    }

    protected abstract GameDAO createDAO();

    @AfterEach
    public void cleanUp() throws DataAccessException {
        for (Game game : examples) {
            if (gameDAO.read(game.id()) != null) {
                gameDAO.delete(game.id());
            }
        }
    }

    @Test
    public void testCreateReadSuccess() throws DataAccessException {
        for (Game game : examples) {
            gameDAO.create(game);
            Game result = gameDAO.read(game.id());
            Assertions.assertEquals(game, result, "Read game not the same as created game");
        }
    }

    @Test
    public void testCreateDuplicate() throws DataAccessException {
        for (Game game : examples) {
            gameDAO.create(game);

            DataAccessException thrown = Assertions.assertThrows(DataAccessException.class,
                    () -> gameDAO.create(game));

            Assertions.assertEquals("id/key already exists", thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }

    }

    @Test
    public void testReadNothing() throws DataAccessException {
        for (Game game : examples) {
            Assertions.assertNull(gameDAO.read(game.id()), "Read game that hasn't been inserted");
        }
    }

    @Test
    public void testReadAll() throws DataAccessException {
        int startingSize = gameDAO.readAll().size();

        gameDAO.create(examples[0]);
        gameDAO.create(examples[1]);

        Collection<Game> result = gameDAO.readAll();
        Assertions.assertEquals(startingSize + 2, result.size(), "Read a different number of games");
        Assertions.assertTrue(result.contains(examples[0]), "Couldn't read created game");
        Assertions.assertTrue(result.contains(examples[1]), "Couldn't read created game");
        Assertions.assertFalse(result.contains(examples[2]), "Read game that wasn't created");
    }

    @Test
    public void testUpdate() throws DataAccessException {
        gameDAO.create(examples[0]);
        Game updated = new Game(examples[0].id(), "different name", new SevenCardRummyGame());
        gameDAO.update(updated);
        Game result = gameDAO.read(examples[0].id());

        Assertions.assertNotEquals(examples[0], result, "Read game the same as created game after update");
        Assertions.assertEquals(updated, result, "Read game not the same as updated game");
    }

    @Test
    public void testUpdateNothing() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class,
                () -> gameDAO.update(examples[0]),
                "Updated game that hadn't been created");
    }

    @Test
    public void testDelete() throws DataAccessException {
        for (Game game : examples) {
            gameDAO.create(game);

            Game result = gameDAO.read(game.id());
            Assertions.assertEquals(game, result, "Read game not the same as created game");

            gameDAO.delete(game.id());

            Assertions.assertNull(gameDAO.read(game.id()),
                    "Read game that has been deleted");
        }
    }

    @Test
    public void testDeleteNothing() throws DataAccessException {
        for (Game game : examples) {
            Assertions.assertDoesNotThrow(() -> gameDAO.delete(game.id()),
                    "Deleting threw an error");
        }
    }

}
