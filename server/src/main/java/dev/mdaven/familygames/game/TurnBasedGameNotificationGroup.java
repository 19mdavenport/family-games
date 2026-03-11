package dev.mdaven.familygames.game;

import java.util.Collection;

public record TurnBasedGameNotificationGroup<T>(Collection<T> root, Collection<T> all, Collection<T> others) {}
