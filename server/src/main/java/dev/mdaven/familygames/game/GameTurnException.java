package dev.mdaven.familygames.game;

public class GameTurnException extends Exception {
    public GameTurnException(String message) {
        super(message);
    }

    public GameTurnException(String message, Throwable cause) {
        super(message, cause);
    }
}
