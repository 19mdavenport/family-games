package dev.mdaven.familygames.game.card.rummy.sevencard.notification;

import dev.mdaven.familygames.game.GameEvent;

public class SevenCardRummyEventNotification extends SevenCardRummyNotification {
    private GameEvent event;

    public SevenCardRummyEventNotification(GameEvent event) {
        super(Type.NOTIFICATION);
        this.event = event;
    }

    public GameEvent getEvent() {
        return event;
    }
}
