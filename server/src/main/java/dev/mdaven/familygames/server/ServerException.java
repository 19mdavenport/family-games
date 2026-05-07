package dev.mdaven.familygames.server;

public class ServerException extends Exception {
    public enum Reason {
        BAD_REQUEST, UNAUTHORIZED, ALREADY_EXISTS, SERVER_ERROR
    }

    private final Reason reason;

    public ServerException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public ServerException(Reason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = reason;
    }

    public ServerException(Reason reason, Throwable cause) {
        super(cause);
        this.reason = reason;
    }

    public Reason getReason() {
        return reason;
    }
}
