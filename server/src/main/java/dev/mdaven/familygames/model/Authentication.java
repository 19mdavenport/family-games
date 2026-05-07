package dev.mdaven.familygames.model;

import java.util.UUID;

public record Authentication (String token, UUID userId, long createdAt) {}
