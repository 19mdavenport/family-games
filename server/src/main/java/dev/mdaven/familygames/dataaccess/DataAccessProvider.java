package dev.mdaven.familygames.dataaccess;

import dev.mdaven.familygames.model.Authentication;

import java.util.UUID;

public interface DataAccessProvider {
    AuthenticationDAO authDao();
    GameDAO gameDao();
    ProfileDAO profileDao();
    UserDAO userDao();
}
