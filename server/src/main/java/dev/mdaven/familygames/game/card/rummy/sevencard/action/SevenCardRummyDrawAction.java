package dev.mdaven.familygames.game.card.rummy.sevencard.action;

public class SevenCardRummyDrawAction extends SevenCardRummyAction {
    private Boolean fromDiscard;

    public SevenCardRummyDrawAction(Boolean fromDiscard) {
        super(Type.DRAW);
        this.fromDiscard = fromDiscard;
    }

    public Boolean isFromDiscard() {
        return fromDiscard;
    }
}
