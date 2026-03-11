package dev.mdaven.familygames.game.card;

import java.util.Objects;

public class PokerCard {

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES, SPECIAL
    }

    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, JOKER;
    }

    private final Rank rank;
    private final Suit suit;

    public PokerCard(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokerCard card = (PokerCard) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(rank);
        result = 31 * result + Objects.hashCode(suit);
        return result;
    }

    @Override
    public String toString() {
        if(suit == Suit.SPECIAL) return rank.name().toLowerCase();
        return rank.name().toLowerCase() + " of " + suit.name().toLowerCase();
    }
}
