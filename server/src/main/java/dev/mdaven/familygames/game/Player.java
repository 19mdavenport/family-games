package dev.mdaven.familygames.game;

import java.util.UUID;

public class Player {
    private UUID playerID;
    private String username;

    protected Player(UUID playerID, String username) {
        this.playerID = playerID;
        this.username = username;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public String getUsername() {
        return username;
    }
}
