package dev.mdaven.familygames.dataaccess;

public record SimpleDataAccessProvider
        (AuthenticationDAO authDao, ProfileDAO profileDao, UserDAO userDao, GameDAO gameDao)
        implements DataAccessProvider {}
