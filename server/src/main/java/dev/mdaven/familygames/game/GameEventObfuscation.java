package dev.mdaven.familygames.game;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record GameEventObfuscation(String obfuscated, String unobfuscated, boolean defaultObfuscated,
                                   Collection<UUID> obfuscationExceptions) {

    public String get(UUID playerID) {
        return defaultObfuscated ^ obfuscationExceptions.contains(playerID) ? obfuscated : unobfuscated;
    }

}
