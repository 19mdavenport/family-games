package dev.mdaven.familygames.game.card.rummy.sevencard.notification;

public class SevenCardRummyNotification {
    public enum Type {
        LOAD, DRAW, DISCARD, WIN, NOTIFICATION
    }

    private Type type;

    public SevenCardRummyNotification(Type type) {
        this.type = type;
    }
}
