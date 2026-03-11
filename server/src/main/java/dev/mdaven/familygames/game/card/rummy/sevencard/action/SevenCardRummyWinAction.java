package dev.mdaven.familygames.game.card.rummy.sevencard.action;

import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;

import java.util.Collection;

public class SevenCardRummyWinAction extends SevenCardRummyAction {
    private Collection<RummyCardGroup> winGroups;

    public SevenCardRummyWinAction(Collection<RummyCardGroup> winGroups) {
        super(Type.WIN);
        this.winGroups = winGroups;
    }

    public Collection<RummyCardGroup> getWinGroups() {
        return winGroups;
    }
}
