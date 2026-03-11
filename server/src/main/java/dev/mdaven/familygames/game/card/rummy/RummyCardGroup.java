package dev.mdaven.familygames.game.card.rummy;

import dev.mdaven.familygames.game.card.PokerCard;

import java.util.ArrayList;
import java.util.List;

public class RummyCardGroup {

    private final List<PokerCard> cards;

    public RummyCardGroup(List<PokerCard> cards) {

        this.cards = cards;
    }

    public boolean validSet() {
        if(cards.size() < 3) return false;
        List<PokerCard> noJokers = withoutJokers();
        if(noJokers.size() < 2) return true;

        PokerCard.Rank rank = noJokers.get(0).getRank();
        return noJokers.stream().allMatch((card) -> card.getRank() == rank);
    }

    public boolean validRun() {
        if(cards.size() < 4) return false;
        List<PokerCard> noJokers = withoutJokers();
        if(noJokers.size() < 2) return true;
        int numJokers = cards.size() - noJokers.size();

        PokerCard.Suit suit = cards.get(0).getSuit();
        if(noJokers.stream().anyMatch((card) -> card.getSuit() != suit)) return false;

        noJokers.sort((o1, o2) -> o1.getRank().ordinal() - o2.getRank().ordinal());
        if(noJokers.get(0).getRank() == PokerCard.Rank.ACE &&
                noJokers.getLast().getRank().ordinal() - PokerCard.Rank.ACE.ordinal() > numJokers) {
            PokerCard ace = noJokers.removeFirst();
            noJokers.addLast(ace);
        }

        int at = noJokers.get(0).getRank().ordinal();
        for(int i = 0; i < noJokers.size(); i++) {
            while(noJokers.get(i).getRank().ordinal() > at) {
                numJokers--;
                at++;
            }
            at++;
        }

        return numJokers >= 0;
    }

    private List<PokerCard> withoutJokers() {
        List<PokerCard> noJokers = new ArrayList<>(cards);
        noJokers.removeIf((card) -> card.getRank() == PokerCard.Rank.JOKER);
        return noJokers;
    }

    public List<PokerCard> getCards() {
        return cards;
    }
}
