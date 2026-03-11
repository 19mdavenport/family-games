package dev.mdaven.familygames.game.card.rummy.sevencard.notification;

import dev.mdaven.familygames.game.card.PokerCard;

public class SevenCardRummyDiscardNotification extends SevenCardRummyNotification {
    private PokerCard card;
    private int playerNum;

    public SevenCardRummyDiscardNotification(PokerCard card, int playerNum) {
        super(Type.DISCARD);
        this.card = card;
        this.playerNum = playerNum;
    }

    public PokerCard getCard() {
        return card;
    }

    public int getPlayerNum() {
        return playerNum;
    }
}
