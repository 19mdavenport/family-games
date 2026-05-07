package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.server.ServerException;

public class DataAccessException extends ServerException {
    public DataAccessException(String message) {
        super(Reason.SERVER_ERROR, message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(Reason.SERVER_ERROR, message, cause);
    }
}
