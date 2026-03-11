package dev.mdaven.familygames.game.card.rummy.sevencard;

import dev.mdaven.familygames.game.GameEvent;
import dev.mdaven.familygames.game.GameEventObfuscation;
import dev.mdaven.familygames.game.GameTurnException;
import dev.mdaven.familygames.game.TurnBasedGameNotificationGroup;
import dev.mdaven.familygames.game.card.CardGamePlayer;
import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.PokerDeck;
import dev.mdaven.familygames.game.card.rummy.sevencard.action.SevenCardRummyAction;
import dev.mdaven.familygames.game.card.rummy.sevencard.action.SevenCardRummyDrawAction;
import dev.mdaven.familygames.game.card.rummy.sevencard.notification.SevenCardRummyDrawNotification;
import dev.mdaven.familygames.game.card.rummy.sevencard.notification.SevenCardRummyEventNotification;
import dev.mdaven.familygames.game.card.rummy.sevencard.notification.SevenCardRummyLoadNotification;
import dev.mdaven.familygames.game.card.rummy.sevencard.notification.SevenCardRummyNotification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.*;

public class SevenCardRummyGameTest {
    private static final String DISCARD_PILE_FIELD_NAME = "discardPile";

    private static final String DRAW_PILE_FIELD_NAME = "drawPile";

    private static final String PLAYER_1_USERNAME = "player1";

    private static final String PLAYER_2_USERNAME = "player2";

    private SevenCardRummyGame game;

    private UUID player1ID;

    private UUID player2ID;


    @BeforeEach
    public void setup() throws GameTurnException {
        game = Mockito.spy(new SevenCardRummyGame());

        player1ID = UUID.randomUUID();
        player2ID = UUID.randomUUID();

        game.addPlayer(player1ID, PLAYER_1_USERNAME);
        game.addPlayer(player2ID, PLAYER_2_USERNAME);
    }

    private static void setFieldValue(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    @Test
    @DisplayName("Start Game")
    public void startGameSuccess() throws NoSuchFieldException, IllegalAccessException {
        game.startGame();

        Deque<PokerCard> drawPile = (Deque<PokerCard>) getFieldValue(game, DRAW_PILE_FIELD_NAME);
        Deque<PokerCard> discardPile = (Deque<PokerCard>) getFieldValue(game, DISCARD_PILE_FIELD_NAME);

        Assertions.assertEquals(40, drawPile.size());
        Assertions.assertTrue(discardPile.isEmpty());

        Set<PokerCard> allCards = new HashSet<>();
        allCards.addAll(drawPile);

        for (CardGamePlayer<PokerCard> player : game.getPlayers()) {
            Assertions.assertEquals(7, player.getHand().size());
            allCards.addAll(player.getHand());
        }

        Assertions.assertEquals(53, allCards.size());
        for (PokerCard.Suit suit : PokerCard.Suit.values()) {
            if (suit == PokerCard.Suit.SPECIAL) continue;
            for (PokerCard.Rank rank : PokerCard.Rank.values()) {
                if (rank == PokerCard.Rank.JOKER) continue;
                PokerCard card = new PokerCard(rank, suit);
                Assertions.assertTrue(allCards.contains(card), "Did not contain " + card);
            }
        }

        Assertions.assertTrue(allCards.contains(new PokerCard(PokerCard.Rank.JOKER, PokerCard.Suit.SPECIAL)));

        Assertions.assertNull(getFieldValue(game, "winGroups"));
    }

    @Test
    @DisplayName("Start Game without player")
    public void startGameWithoutPlayer() throws GameTurnException {
        game.getPlayers().clear();
        game.addPlayer(player1ID, PLAYER_1_USERNAME);

        Assertions.assertThrows(IllegalStateException.class, () -> game.startGame());
    }

    @Test
    @DisplayName("Load Game")
    public void loadGameSuccess() throws GameTurnException, NoSuchFieldException, IllegalAccessException {
        Deque<PokerCard> fakeDrawPile =
                new LinkedList<>(List.of(new PokerCard(PokerCard.Rank.SEVEN, PokerCard.Suit.HEARTS)));
        setFieldValue(game, DRAW_PILE_FIELD_NAME, fakeDrawPile);
        setFieldValue(game, DISCARD_PILE_FIELD_NAME, new LinkedList<>(List.of()));

        TurnBasedGameNotificationGroup<SevenCardRummyNotification> notificationGroup =
                game.gameAction(new SevenCardRummyAction(SevenCardRummyAction.Type.LOAD), player1ID);

        Assertions.assertTrue(notificationGroup.all().isEmpty());
        Assertions.assertTrue(notificationGroup.others().isEmpty());
        Assertions.assertEquals(1, notificationGroup.root().size());

        SevenCardRummyLoadNotification loadNotification =
                Assertions.assertInstanceOf(SevenCardRummyLoadNotification.class,
                        notificationGroup.root().iterator().next());
        Assertions.assertEquals(fakeDrawPile.size(), loadNotification.getDrawPileSize());
        Assertions.assertNull(loadNotification.getDrawPile());
        Assertions.assertTrue(loadNotification.getDiscardPile().isEmpty());
        Assertions.assertEquals(game.getPlayers().size(), loadNotification.getPlayers().size());
        Assertions.assertEquals(game.getPlayers().getFirst().getHand(), loadNotification.getPlayers().getFirst().getHand());
        Assertions.assertTrue(loadNotification.getPlayers().getLast().getHand().isEmpty());
    }

    @Test
    @DisplayName("Draw From Discard Success")
    public void drawDiscardSuccess() throws NoSuchFieldException, IllegalAccessException, GameTurnException {
        long initialTimestamp = System.currentTimeMillis();

        Deque<PokerCard> fakeDrawPile = Mockito.mock(Deque.class);
        Mockito.when(fakeDrawPile.isEmpty()).thenReturn(false);
        setFieldValue(game, DRAW_PILE_FIELD_NAME, fakeDrawPile);

        PokerCard topDiscardCard = new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.SPADES);
        PokerCard bottomDiscardCard = new PokerCard(PokerCard.Rank.QUEEN, PokerCard.Suit.DIAMONDS);
        Deque<PokerCard> discardPile = new LinkedList<>(List.of(topDiscardCard, bottomDiscardCard));
        setFieldValue(game, DISCARD_PILE_FIELD_NAME, discardPile);

        TurnBasedGameNotificationGroup<SevenCardRummyNotification>
                notifications = game.gameAction(new SevenCardRummyDrawAction(true), player1ID);

        Assertions.assertTrue(notifications.root().isEmpty());
        Assertions.assertTrue(notifications.others().isEmpty());

        Collection<SevenCardRummyNotification> all = notifications.all();
        Assertions.assertEquals(2, all.size());

        Iterator<SevenCardRummyNotification> iterator = all.iterator();
        SevenCardRummyNotification first = iterator.next();
        SevenCardRummyNotification second = iterator.next();

        SevenCardRummyDrawNotification drawNotification;
        SevenCardRummyEventNotification eventNotification;
        if(first instanceof SevenCardRummyDrawNotification dn) {
            drawNotification = dn;
            eventNotification = Assertions.assertInstanceOf(SevenCardRummyEventNotification.class, second);
        } else {
            drawNotification = Assertions.assertInstanceOf(SevenCardRummyDrawNotification.class, second);
            eventNotification = Assertions.assertInstanceOf(SevenCardRummyEventNotification.class, first);
        }

        Assertions.assertEquals(topDiscardCard, drawNotification.getCard());
        Assertions.assertTrue(drawNotification.isFromDiscard());
        Assertions.assertEquals(0, drawNotification.getPlayerNum());

        GameEvent firstEvent = eventNotification.getEvent();
        Assertions.assertEquals(0, firstEvent.eventID());
        long endTimestamp = System.currentTimeMillis();
        Assertions.assertTrue(firstEvent.timestamp() >= initialTimestamp);
        Assertions.assertTrue(firstEvent.timestamp() <= endTimestamp);
        Assertions.assertTrue(firstEvent.obfuscations().isEmpty());
        Assertions.assertEquals(PLAYER_1_USERNAME + " draws " + topDiscardCard + " from the discard pile",
                firstEvent.base());

        List<PokerCard> actualDiscardPile = (List<PokerCard>) getFieldValue(game, DISCARD_PILE_FIELD_NAME);
        Assertions.assertEquals(1, actualDiscardPile.size());
        Assertions.assertFalse(actualDiscardPile.contains(topDiscardCard));
        Assertions.assertEquals(bottomDiscardCard, actualDiscardPile.getFirst());

        Mockito.verify(fakeDrawPile, Mockito.times(1)).isEmpty();
        Mockito.verifyNoMoreInteractions(fakeDrawPile);
    }

    @Test
    @DisplayName("Draw From Draw Success")
    public void drawDrawSuccess() throws NoSuchFieldException, IllegalAccessException, GameTurnException {
        long initialTimestamp = System.currentTimeMillis();

        PokerCard topCard = new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.SPADES);
        PokerCard bottomCard = new PokerCard(PokerCard.Rank.QUEEN, PokerCard.Suit.DIAMONDS);
        Deque<PokerCard> drawPile = new LinkedList<>(List.of(topCard, bottomCard));
        setFieldValue(game, DRAW_PILE_FIELD_NAME, drawPile);

        Deque<PokerCard> fakeDiscardPile = Mockito.mock(Deque.class);
        Mockito.when(fakeDiscardPile.isEmpty()).thenReturn(false);
        setFieldValue(game, DISCARD_PILE_FIELD_NAME, fakeDiscardPile);

        TurnBasedGameNotificationGroup<SevenCardRummyNotification>
                notifications = game.gameAction(new SevenCardRummyDrawAction(false), player1ID);

        Assertions.assertEquals(1, notifications.root().size());
        SevenCardRummyDrawNotification rootNotification = Assertions.assertInstanceOf(SevenCardRummyDrawNotification.class,
                notifications.root().iterator().next());
        Assertions.assertEquals(topCard, rootNotification.getCard());
        Assertions.assertEquals(0, rootNotification.getPlayerNum());
        Assertions.assertFalse(rootNotification.isFromDiscard());


        Assertions.assertEquals(1, notifications.others().size());
        SevenCardRummyDrawNotification otherNotification =
                Assertions.assertInstanceOf(SevenCardRummyDrawNotification.class,
                        notifications.others().iterator().next());
        Assertions.assertNull(otherNotification.getCard());
        Assertions.assertEquals(0, otherNotification.getPlayerNum());
        Assertions.assertFalse(otherNotification.isFromDiscard());


        Assertions.assertEquals(1, notifications.all().size());
        SevenCardRummyEventNotification allNotification =
                Assertions.assertInstanceOf(SevenCardRummyEventNotification.class,
                        notifications.all().iterator().next());

        GameEvent firstEvent = allNotification.getEvent();
        Assertions.assertEquals(0, firstEvent.eventID());
        long endTimestamp = System.currentTimeMillis();
        Assertions.assertTrue(firstEvent.timestamp() >= initialTimestamp);
        Assertions.assertTrue(firstEvent.timestamp() <= endTimestamp);
        Assertions.assertEquals(1, firstEvent.obfuscations().size());
        GameEventObfuscation obfuscation = firstEvent.obfuscations().getFirst();
        Assertions.assertEquals(1, obfuscation.obfuscationExceptions().size());
        Assertions.assertEquals(player1ID, obfuscation.obfuscationExceptions().iterator().next());
        Assertions.assertEquals(topCard.toString(), obfuscation.unobfuscated());
        Assertions.assertEquals("a card", obfuscation.obfuscated());
        Assertions.assertEquals(PLAYER_1_USERNAME + " draws %s from the draw pile",
                firstEvent.base());

        List<PokerCard> actualDrawPile = (List<PokerCard>) getFieldValue(game, DRAW_PILE_FIELD_NAME);
        Assertions.assertEquals(1, actualDrawPile.size());
        Assertions.assertFalse(actualDrawPile.contains(topCard));
        Assertions.assertEquals(bottomCard, actualDrawPile.getFirst());

        Mockito.verifyNoInteractions(fakeDiscardPile);
    }

    @Test
    @DisplayName("Draw Out of Turn")
    public void drawOutOfTurn() throws NoSuchFieldException, IllegalAccessException, GameTurnException {
        setFieldValue(game, DRAW_PILE_FIELD_NAME,
                new LinkedList<>(List.of(new PokerCard(PokerCard.Rank.EIGHT, PokerCard.Suit.CLUBS))));
        setFieldValue(game, DISCARD_PILE_FIELD_NAME,
                new LinkedList<>(List.of(new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.DIAMONDS))));

        Assertions.assertThrows(GameTurnException.class,
                () -> game.gameAction(new SevenCardRummyDrawAction(false), player2ID));
    }

    @Test
    @DisplayName("Draw Twice")
    public void drawTwice() throws NoSuchFieldException, IllegalAccessException, GameTurnException {
        setFieldValue(game, DRAW_PILE_FIELD_NAME,
                new LinkedList<>(List.of(new PokerCard(PokerCard.Rank.EIGHT, PokerCard.Suit.CLUBS))));
        setFieldValue(game, DISCARD_PILE_FIELD_NAME,
                new LinkedList<>(List.of(new PokerCard(PokerCard.Rank.NINE, PokerCard.Suit.DIAMONDS))));

        game.gameAction(new SevenCardRummyDrawAction(false), player1ID);
        Assertions.assertThrows(GameTurnException.class,
                () -> game.gameAction(new SevenCardRummyDrawAction(false), player1ID));
    }

}