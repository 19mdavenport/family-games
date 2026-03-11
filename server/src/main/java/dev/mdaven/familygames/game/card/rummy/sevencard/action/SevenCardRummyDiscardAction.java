package dev.mdaven.familygames.game.card.rummy.sevencard.action;

import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;

import java.util.Collection;

public class SevenCardRummyDiscardAction extends SevenCardRummyAction {
    private PokerCard discardCard;

    public SevenCardRummyDiscardAction(PokerCard discardCard) {
        super(Type.DISCARD);
        this.discardCard = discardCard;
    }

    public PokerCard getDiscardCard() {
        return discardCard;
    }
}
