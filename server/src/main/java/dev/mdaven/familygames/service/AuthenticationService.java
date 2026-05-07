package dev.mdaven.familygames.service;

import dev.mdaven.familygames.dataaccess.DataAccessProvider;
import dev.mdaven.familygames.dataaccess.UserDAO;
import dev.mdaven.familygames.model.Authentication;
import dev.mdaven.familygames.model.Profile;
import dev.mdaven.familygames.model.User;
import dev.mdaven.familygames.request.RegisterRequest;
import dev.mdaven.familygames.server.ServerException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class AuthenticationService {
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Email already registered";
    public static final String INCORRECT_LOGIN_INFO_MESSAGE = "Email or password incorrect";
    public static final String MISSING_INFO_MESSAGE = "Missing required information";

    private final DataAccessProvider dataAccessProvider;
    private final PasswordHashingStrategy hasher;
    private final long tokenExpirationDurationMs;

    public AuthenticationService(DataAccessProvider dataAccessProvider, PasswordHashingStrategy hasher,
                                 long tokenExpirationDurationMs) {
        this.dataAccessProvider = dataAccessProvider;
        this.hasher = hasher;
        this.tokenExpirationDurationMs = tokenExpirationDurationMs;
    }

    public Authentication register(RegisterRequest request) throws ServerException {
        if (request == null || request.email() == null || request.password() == null || request.username() == null) {
            throw new ServerException(ServerException.Reason.BAD_REQUEST, MISSING_INFO_MESSAGE);
        }

        UserDAO userDAO = dataAccessProvider.userDao();

        if (userDAO.readFromEmail(request.email()) != null) {
            throw new ServerException(ServerException.Reason.ALREADY_EXISTS, EMAIL_ALREADY_EXISTS_MESSAGE);
        }

        UUID newUserId = UUID.randomUUID();
        while (userDAO.read(newUserId) != null) {
            newUserId = UUID.randomUUID();
        }

        String hashedPassword = hasher.hash(request.password());
        User user = new User(newUserId, request.email(), hashedPassword);
        userDAO.create(user);

        Profile profile = new Profile(newUserId, request.username());
        dataAccessProvider.profileDao().create(profile);

        return generateAuthentication(newUserId);
    }

    public Authentication login(User user) throws ServerException {
        if (user == null || user.email() == null || user.password() == null) {
            throw new ServerException(ServerException.Reason.BAD_REQUEST, MISSING_INFO_MESSAGE);
        }

        User dbUser = dataAccessProvider.userDao().readFromEmail(user.email());
        if (dbUser == null || !hasher.checkPassword(user.password(), dbUser.password())) {
            throw new ServerException(ServerException.Reason.UNAUTHORIZED, INCORRECT_LOGIN_INFO_MESSAGE);
        }

        return generateAuthentication(dbUser.id());
    }

    public void logout(Authentication auth) throws ServerException {
        dataAccessProvider.authDao().delete(auth.token());
    }

    public Authentication authenticate(UUID token) throws ServerException {
        Authentication auth = dataAccessProvider.authDao().read(token);
        return auth != null && auth.createdAt() + tokenExpirationDurationMs > System.currentTimeMillis() ? auth : null;
    }

    private Authentication generateAuthentication(UUID userId) throws ServerException {
        Authentication auth = new Authentication(UUID.randomUUID(), userId, System.currentTimeMillis());
        dataAccessProvider.authDao().create(auth);
        return auth;
    }
}
