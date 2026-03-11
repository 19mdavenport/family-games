package dev.mdaven.familygames.game;

import dev.mdaven.familygames.game.card.CardGamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class TurnBasedGame<P extends CardGamePlayer, A, N> {
    boolean hasStarted = false;
    boolean isOver = false;

    private List<P> players = new ArrayList<P>();

    private int turn = 0;

    private List<GameEvent> events = new ArrayList<GameEvent>();
    private int nextEventID = 0;

    public void addPlayer(UUID playerID, String username) throws GameTurnException {
        if (hasStarted) {
            throw new GameTurnException("Game already started");
        }
        if (players.size() >= getMaxPlayers()) {
            throw new GameTurnException("Game is full");
        }

        players.add(createPlayer(playerID, username));
    }

    protected void advanceTurn() {
        turn = (turn + 1) % players.size();
    }

    protected GameEvent newEvent(String base, List<GameEventObfuscation> obfuscations) {
        GameEvent event = new GameEvent(nextEventID++, System.currentTimeMillis(), base, obfuscations);
        events.add(event);
        return event;
    }

    public boolean isStarted() {
        return hasStarted;
    }

    public void markStarted() {
        hasStarted = true;
    }

    public boolean isOver() {
        return isOver;
    }

    public void markOver() {
        isOver = true;
    }

    public List<P> getPlayers() {
        return players;
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    public int getTurn() {
        return turn;
    }

    public abstract TurnBasedGameNotificationGroup<N> gameAction(A action, UUID playerID) throws GameTurnException;
    protected abstract int getMaxPlayers();
    protected abstract P createPlayer(UUID playerID, String username);
}
