package dev.mdaven.familygames.model;

import dev.mdaven.familygames.game.TurnBasedGame;

import java.util.UUID;

public record Game (UUID id, String name, TurnBasedGame<?, ?, ?> game) {}
