package dev.mdaven.familygames.game.card.rummy;

import dev.mdaven.familygames.game.card.PokerCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RummyCardGroupTest {

    @Test
    public void validSets() {
        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.SPADES))).validSet());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validSet());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.HEARTS))).validSet());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validSet());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.FIVE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validSet());
    }

    @Test
    public void invalidSets() {
        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.CLUBS))).validSet());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.EIGHT, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.CLUBS))).validSet());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.FOUR, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validSet());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS))).validSet());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.FOUR, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validSet());
    }

    @Test
    public void validRuns() {
        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.EIGHT, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.HEARTS))).validRun());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.THREE, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.FIVE, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.DIAMONDS))).validRun());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.ACE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.FIVE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.THREE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.FOUR, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.SPADES))).validRun());

        Assertions.assertTrue(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.ACE, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.QUEEN, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.JACK, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validRun());
    }

    @Test
    public void invalidRuns() {
        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.EIGHT, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.HEARTS))).validRun());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.CLUBS),
                        new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.SIX, PokerCard.Suit.SPADES))).validRun());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.FIVE, PokerCard.Suit.HEARTS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.ACE, PokerCard.Suit.HEARTS))).validRun());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.JACK, PokerCard.Suit.SPADES),
                        new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.SPADES))).validRun());

        Assertions.assertFalse(new RummyCardGroup(
                List.of(new PokerCard(PokerCard.Rank.ACE, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.KING, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.TWO, PokerCard.Suit.DIAMONDS),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL),
                        new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL))).validRun());
    }

}
