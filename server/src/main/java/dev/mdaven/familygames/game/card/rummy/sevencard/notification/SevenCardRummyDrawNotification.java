package dev.mdaven.familygames.game.card.rummy.sevencard.notification;

import dev.mdaven.familygames.game.card.PokerCard;

public class SevenCardRummyDrawNotification extends SevenCardRummyNotification {
    private PokerCard card;
    private boolean fromDiscard;
    private int playerNum;

    public SevenCardRummyDrawNotification(PokerCard card, int playerNum, boolean fromDiscard) {
        super(Type.DRAW);
        this.card = card;
        this.playerNum = playerNum;
        this.fromDiscard = fromDiscard;
    }

    public PokerCard getCard() {
        return card;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public boolean isFromDiscard() {
        return fromDiscard;
    }
}
