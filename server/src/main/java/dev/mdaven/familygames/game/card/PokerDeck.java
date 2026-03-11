package dev.mdaven.familygames.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PokerDeck extends LinkedList<PokerCard> {

    public void setupDeck(int numDecks, int numJokers) {
        clear();
        List<PokerCard> cards = new ArrayList<>();
        for (PokerCard.Suit suit : PokerCard.Suit.values()) {
            if (suit == PokerCard.Suit.SPECIAL) continue;
            for (PokerCard.Rank rank : PokerCard.Rank.values()) {
                if (rank == PokerCard.Rank.JOKER) continue;
                for (int i = 0; i < numDecks; i++) {
                    cards.add(new PokerCard(rank, suit));
                }
            }
        }
        for (int i = 0; i < numJokers; i++) {
            cards.add(new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL));
        }
        Collections.shuffle(cards);
        addAll(cards);
    }

}
