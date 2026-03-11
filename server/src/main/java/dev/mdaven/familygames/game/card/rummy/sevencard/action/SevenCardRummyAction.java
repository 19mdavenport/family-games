package dev.mdaven.familygames.game.card.rummy.sevencard.action;

public class SevenCardRummyAction {
    private Type type;

    public enum Type {
        LOAD, DRAW, DISCARD, WIN
    }

    public SevenCardRummyAction(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
