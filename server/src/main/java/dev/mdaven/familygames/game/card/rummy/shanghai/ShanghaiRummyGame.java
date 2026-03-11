package dev.mdaven.familygames.game.card.rummy.shanghai;

import dev.mdaven.familygames.game.GameTurnException;
import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.PokerDeck;

import java.util.*;

public class ShanghaiRummyGame {
    public static final int MIN_PLAYERS = 3;

    public static final int MAX_PLAYERS = 8;

    private static final int NUM_DECKS = 3;

    private static final int NUM_JOKERS = 8;

    private List<ShanghaiRummyPlayer> players = new ArrayList<>();

    private PokerDeck deck;

    private int turn;

    private boolean hasPlayed;

    private ShanghaiRummyRound round;

//    public ShanghaiRummyGame(List<UUID> playerNames) throws GameTurnException {
//        if (playerNames.size() < MIN_PLAYERS)
//            throw new GameTurnException("Minimum number of players is " + MIN_PLAYERS);
//        if (playerNames.size() > MAX_PLAYERS)
//            throw new GameTurnException("Maximum number of players is " + MIN_PLAYERS);
//        for (UUID playerName : playerNames) {
//            players.add(new ShanghaiRummyPlayer(playerName));
//        }
//        setupRound(ShanghaiRummyRound.SIX);
//    }

    private void setupRound(ShanghaiRummyRound round) {
        this.round = round;
        turn = 0;
        hasPlayed = false;
        resetPlayers();
        deck.setupDeck(NUM_DECKS, NUM_JOKERS);
        dealHands();
    }

    private void resetPlayers() {
        for(ShanghaiRummyPlayer player : players) {
            player.getHand().clear();
            player.setBought(0);
            player.setDownGroups(new ArrayList<>());
            player.setWillBuy(null);
        }
    }

    private void dealHands() {
        for (int i = 0; i < round.dealtCards(); i++) {
            for (int j = 0; j < players.size(); j++) {
                players.get(j).getHand().add(deck.poll());
            }
        }
    }

    public int points(PokerCard card) {
        return switch (card.getRank()) {
            case TWO -> 5;
            case THREE -> 5;
            case FOUR -> 5;
            case FIVE -> 5;
            case SIX -> 5;
            case SEVEN -> 5;
            case EIGHT -> 5;
            case NINE -> 5;
            case TEN -> 10;
            case JACK -> 10;
            case QUEEN -> 10;
            case KING -> 10;
            case ACE -> 20;
            case JOKER -> 50;
        };
    }
}
