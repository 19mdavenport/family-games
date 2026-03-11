package dev.mdaven.familygames.game;

import java.util.List;

public record GameEvent (int eventID, long timestamp, String base, List<GameEventObfuscation> obfuscations) {

}
