package dev.mdaven.familygames.game.card.rummy.shanghai;

import dev.mdaven.familygames.game.card.CardGamePlayer;
import dev.mdaven.familygames.game.card.PokerCard;
import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShanghaiRummyPlayer extends CardGamePlayer<PokerCard> {
    private int bought;

    private List<RummyCardGroup> downGroups = new ArrayList<>();

    private int points;

    private Boolean willBuy;

    protected ShanghaiRummyPlayer(UUID playerID, String username) {
        super(playerID, username);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public List<RummyCardGroup> getDownGroups() {
        return downGroups;
    }

    public void setDownGroups(List<RummyCardGroup> downGroups) {
        this.downGroups = downGroups;
    }

    public int getPoints() {
        return points;
    }

    public Boolean getWillBuy() {
        return willBuy;
    }

    public void setWillBuy(Boolean willBuy) {
        this.willBuy = willBuy;
    }

}
