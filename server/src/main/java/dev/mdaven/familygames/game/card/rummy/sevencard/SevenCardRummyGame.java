package dev.mdaven.familygames.game.card.rummy.sevencard;

import dev.mdaven.familygames.game.*;
import dev.mdaven.familygames.game.card.CardGamePlayer;
import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.PokerDeck;
import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;
import dev.mdaven.familygames.game.card.rummy.sevencard.action.*;
import dev.mdaven.familygames.game.card.rummy.sevencard.notification.*;

import java.util.*;

public class SevenCardRummyGame
        extends TurnBasedGame<CardGamePlayer<PokerCard>, SevenCardRummyAction, SevenCardRummyNotification> {

    private Deque<PokerCard> discardPile;

    private Deque<PokerCard> drawPile;

    private boolean drawnOnTurn = false;

    private Collection<RummyCardGroup> winGroups = null;

    public void startGame() {
        if(getPlayers().size() != 2) {
            throw new IllegalStateException("Must have exactly 2 players");
        }
        drawPile = new PokerDeck();
        ((PokerDeck) drawPile).setupDeck(1, 2); //TODO: numJokers could be configurable
        discardPile = new LinkedList<>();

        List<List<PokerCard>> hands = new ArrayList<>();
        for(int i = 0; i < getPlayers().size(); i++) {
            hands.add(new ArrayList<>());
        }
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < getPlayers().size(); j++) {
                hands.get(j).add(drawPile.removeFirst());
            }
        }
        for(int i = 0; i < getPlayers().size(); i++) {
            getPlayers().get(i).setHand(hands.get(i));
        }
    }

    @Override
    public TurnBasedGameNotificationGroup<SevenCardRummyNotification> gameAction(SevenCardRummyAction action,
                                                                                 UUID playerID)
            throws GameTurnException {
        if (action.getType() == null) {
            throw new GameTurnException("Invalid action type");
        }
        return switch (action.getType()) {
            case LOAD -> load(playerID);
            case DRAW -> draw((SevenCardRummyDrawAction) action, playerID);
            case DISCARD -> discard((SevenCardRummyDiscardAction) action, playerID);
            case WIN -> win((SevenCardRummyWinAction) action, playerID);
        };
    }

    private TurnBasedGameNotificationGroup<SevenCardRummyNotification> load(UUID playerID) {
        TurnBasedGameNotificationGroup<SevenCardRummyNotification> notifications = baseNotificationGroup();
        notifications.root().add(currentState(playerID));
        return notifications;
    }

    private SevenCardRummyLoadNotification currentState(UUID playerID) {
        if (isOver()) {
            return new SevenCardRummyLoadNotification(discardPile, drawPile, winGroups, getPlayers(), getTurn(), drawnOnTurn);
        } else {
            List<CardGamePlayer<PokerCard>> players = new ArrayList<>();
            for (CardGamePlayer<PokerCard> player : getPlayers()) {
                players.add(player.getPlayerID().equals(playerID) ? player :
                        new CardGamePlayer<>(player.getPlayerID(), player.getUsername()));
            }

            return new SevenCardRummyLoadNotification(discardPile, drawPile.size(), players, getTurn(), drawnOnTurn);
        }
    }

    private static TurnBasedGameNotificationGroup<SevenCardRummyNotification> baseNotificationGroup() {
        return new TurnBasedGameNotificationGroup<>(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private TurnBasedGameNotificationGroup<SevenCardRummyNotification> draw(SevenCardRummyDrawAction action,
                                                                            UUID playerID) throws GameTurnException {
        CardGamePlayer<PokerCard> currentTurnPlayer = checkTurn(playerID);
        if (drawnOnTurn) {
            throw new GameTurnException("You've already drawn this turn");
        }

        if (action.isFromDiscard() == null) {
            throw new GameTurnException("Must specify from discard or draw pile");
        }

        TurnBasedGameNotificationGroup<SevenCardRummyNotification> notifications = baseNotificationGroup();

        if (action.isFromDiscard()) {
            PokerCard drawnCard = discardPile.removeFirst();
            notifications.all().add(new SevenCardRummyDrawNotification(drawnCard, getTurn(), true));
            String notificationText =
                    String.format("%s draws %s from the discard pile", currentTurnPlayer.getUsername(), drawnCard);
            notifications.all().add(new SevenCardRummyEventNotification(newEvent(notificationText, List.of())));
        } else {
            PokerCard drawnCard = drawPile.removeFirst();

            notifications.root().add(new SevenCardRummyDrawNotification(drawnCard, getTurn(), false));
            notifications.others().add(new SevenCardRummyDrawNotification(null, getTurn(), false));

            GameEventObfuscation obfuscation = new GameEventObfuscation("a card", drawnCard.toString(), true,
                    List.of(currentTurnPlayer.getPlayerID()));
            notifications.all().add(new SevenCardRummyEventNotification(
                    newEvent(currentTurnPlayer.getUsername() + " draws %s from the draw pile", List.of(obfuscation))));
        }

        if (drawPile.isEmpty()) {
            LinkedList<PokerCard> newDrawPile = new LinkedList<>(discardPile);
            Collections.shuffle(newDrawPile);
            drawPile = newDrawPile;
            discardPile.clear();
            notifications.all().add(new SevenCardRummyEventNotification(
                    newEvent("Draw pile empty, discard pile reshuffled in", List.of())));
        }

        drawnOnTurn = true;
        return notifications;
    }

    private CardGamePlayer<PokerCard> checkTurn(UUID playerID) throws GameTurnException {
        CardGamePlayer<PokerCard> currentTurnPlayer = getPlayers().get(getTurn());
        if (!playerID.equals(currentTurnPlayer.getPlayerID())) {
            throw new GameTurnException("Player ID mismatch");
        }
        return currentTurnPlayer;
    }

    private TurnBasedGameNotificationGroup<SevenCardRummyNotification> discard(SevenCardRummyDiscardAction action,
                                                                               UUID playerID) throws GameTurnException {
        CardGamePlayer<PokerCard> currentTurnPlayer = checkTurn(playerID);

        if (!drawnOnTurn) {
            throw new GameTurnException("You haven't drawn this turn");
        }

        PokerCard discardCard = action.getDiscardCard();
        if (discardCard == null) {
            throw new GameTurnException("Must include a discarded card");
        }

        int cardIndex = currentTurnPlayer.getHand().indexOf(discardCard);
        if (cardIndex == -1) {
            throw new GameTurnException("Your hand doesn't contain this card");
        }
        currentTurnPlayer.getHand().remove(cardIndex);
        discardPile.addFirst(discardCard);

        TurnBasedGameNotificationGroup<SevenCardRummyNotification> notifications = baseNotificationGroup();
        notifications.all().add(new SevenCardRummyDiscardNotification(discardCard, getTurn()));
        notifications.all().add(new SevenCardRummyEventNotification(
                newEvent(currentTurnPlayer.getUsername() + " has discarded " + discardCard, List.of())));

        advanceTurn();
        drawnOnTurn = false;
        return notifications;
    }

    private TurnBasedGameNotificationGroup<SevenCardRummyNotification> win(SevenCardRummyWinAction action,
                                                                           UUID playerID) throws GameTurnException {
        CardGamePlayer<PokerCard> currentTurnPlayer = checkTurn(playerID);
        if (action.getWinGroups() == null || action.getWinGroups().size() != 2) {
            throw new GameTurnException("Must include exactly two groups");
        }

        List<PokerCard> copyHand = new LinkedList<>(currentTurnPlayer.getHand());
        for (RummyCardGroup group : action.getWinGroups()) {
            List<PokerCard> groupCards = group.getCards();
            if (groupCards.size() < 3) {
                throw new GameTurnException("Each group must contain at least three cards");
            }

            if (!(group.validRun() || group.validSet())) {
                throw new GameTurnException("Each group must be a valid set or run");
            }

            for (PokerCard card : groupCards) {
                int cardIndex = copyHand.indexOf(card);
                if (cardIndex == -1) {
                    throw new GameTurnException("Your hand doesn't contain all cards");
                }
                copyHand.remove(cardIndex);
            }
        }
        currentTurnPlayer.setHand(copyHand);
        winGroups = action.getWinGroups();

        TurnBasedGameNotificationGroup<SevenCardRummyNotification> notifications = baseNotificationGroup();
        notifications.all().add(new SevenCardRummyNotification(SevenCardRummyNotification.Type.WIN));
        notifications.all().add(new SevenCardRummyEventNotification(
                newEvent(currentTurnPlayer.getUsername() + " has won!", List.of())));

        markOver();
        List<GameEvent> oldEvents = new ArrayList<>(getEvents());
        getEvents().clear();
        for (GameEvent event : oldEvents) {
            String base = String.format(event.base(),
                    event.obfuscations().stream().map(GameEventObfuscation::unobfuscated).toArray());
            getEvents().add(new GameEvent(event.eventID(), event.timestamp(), base, List.of()));
        }
        return notifications;
    }

    @Override
    protected int getMaxPlayers() {
        return 2;
    }

    @Override
    protected CardGamePlayer<PokerCard> createPlayer(UUID playerID, String username) {
        return new CardGamePlayer<>(playerID, username);
    }

}
