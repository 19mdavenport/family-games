package dev.mdaven.familygames.model;

import java.util.UUID;

public record Authentication (UUID token, UUID userId, long createdAt) {}
