package dev.mdaven.familygames.game.card.rummy.sevencard.notification;

import dev.mdaven.familygames.game.card.CardGamePlayer;
import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

public class SevenCardRummyLoadNotification extends SevenCardRummyNotification {
    private Deque<PokerCard> discardPile;

    private Deque<PokerCard> drawPile;

    private Integer drawPileSize;

    private boolean drawnOnTurn;

    private List<CardGamePlayer<PokerCard>> players;

    private int turn;

    private Collection<RummyCardGroup> winGroups;

    public SevenCardRummyLoadNotification(Deque<PokerCard> discardPile, Deque<PokerCard> drawPile,
                                          Collection<RummyCardGroup> winGroups, List<CardGamePlayer<PokerCard>> players,
                                          int turn, boolean drawnOnTurn) {
        this(discardPile, drawPile, null, winGroups, players, turn, drawnOnTurn);
    }

    private SevenCardRummyLoadNotification(Deque<PokerCard> discardPile, Deque<PokerCard> drawPile,
                                           Integer drawPileSize, Collection<RummyCardGroup> winGroups,
                                           List<CardGamePlayer<PokerCard>> players, int turn, boolean drawnOnTurn) {
        super(Type.LOAD);
        this.discardPile = discardPile;
        this.drawPile = drawPile;
        this.drawPileSize = drawPileSize;
        this.winGroups = winGroups;
        this.players = players;
        this.turn = turn;
        this.drawnOnTurn = drawnOnTurn;
    }

    public SevenCardRummyLoadNotification(Deque<PokerCard> discardPile, int drawPileSize,
                                          List<CardGamePlayer<PokerCard>> players, int turn, boolean drawnOnTurn) {
        this(discardPile, null, drawPileSize, null, players, turn, drawnOnTurn);
    }

    public Deque<PokerCard> getDiscardPile() {
        return discardPile;
    }

    public Deque<PokerCard> getDrawPile() {
        return drawPile;
    }

    public Integer getDrawPileSize() {
        return drawPileSize;
    }

    public Collection<RummyCardGroup> getWinGroups() {
        return winGroups;
    }

    public List<CardGamePlayer<PokerCard>> getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isDrawnOnTurn() {
        return drawnOnTurn;
    }
}
