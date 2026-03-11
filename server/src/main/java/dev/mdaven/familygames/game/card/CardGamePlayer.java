package dev.mdaven.familygames.game.card;

import dev.mdaven.familygames.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardGamePlayer<T> extends Player {
    private List<T> hand = new ArrayList<>();


    public CardGamePlayer(UUID name, String username) {
        super(name, username);
    }

    public List<T> getHand() {
        return hand;
    }

    public void setHand(List<T> hand) {
        this.hand = hand;
    }
}
