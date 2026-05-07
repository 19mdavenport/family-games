package dev.mdaven.familygames.service;

import dev.mdaven.familygames.dataaccess.AuthenticationDAOTest;
import dev.mdaven.familygames.dataaccess.DataAccessException;
import dev.mdaven.familygames.dataaccess.DataAccessProvider;
import dev.mdaven.familygames.dataaccess.SimpleDataAccessProvider;
import dev.mdaven.familygames.dataaccess.memory.MemoryAuthenticationDAO;
import dev.mdaven.familygames.dataaccess.memory.MemoryGameDAO;
import dev.mdaven.familygames.dataaccess.memory.MemoryProfileDAO;
import dev.mdaven.familygames.dataaccess.memory.MemoryUserDAO;
import dev.mdaven.familygames.model.Authentication;
import dev.mdaven.familygames.model.Profile;
import dev.mdaven.familygames.model.User;
import dev.mdaven.familygames.request.RegisterRequest;
import dev.mdaven.familygames.server.ServerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class AuthenticationServiceTest {
    private static final long TOKEN_EXPIRATION_DURATION = 60 * 60 * 1000;
    private AuthenticationService service;
    private DataAccessProvider dataAccessProvider;

    @BeforeEach
    public void setUp() {
        dataAccessProvider = new SimpleDataAccessProvider(new MemoryAuthenticationDAO(), new MemoryProfileDAO(),
                new MemoryUserDAO(), new MemoryGameDAO());
        service = new AuthenticationService(dataAccessProvider, new PlaintextPasswordHashingStrategy(),
                TOKEN_EXPIRATION_DURATION);
    }

    @Test
    public void testRegisterSuccess() throws ServerException {
        RegisterRequest request = new RegisterRequest("email@example.com", "username", "password");
        long before = System.currentTimeMillis();
        Authentication auth = service.register(request);
        long after = System.currentTimeMillis();

        Assertions.assertNotNull(auth);
        Assertions.assertNotNull(auth.token());
        Assertions.assertTrue(auth.createdAt() >= before && auth.createdAt() <= after);

        User user = dataAccessProvider.userDao().read(auth.userId());
        Assertions.assertNotNull(user);
        Assertions.assertEquals(request.email(), user.email());
        Assertions.assertEquals(request.password(), user.password());

        Profile profile = dataAccessProvider.profileDao().read(auth.userId());
        Assertions.assertNotNull(profile);
        Assertions.assertEquals(request.username(), profile.username());
    }

    @Test
    public void testRegisterBadRequest() throws ServerException {
        RegisterRequest[] requests = {
                null,
                new RegisterRequest(null, "username", "password"),
                new RegisterRequest("email@example.com", null, "password"),
                new RegisterRequest("email@example.com", "username", null)
        };
        for (RegisterRequest request : requests) {
            ServerException thrown = Assertions.assertThrows(ServerException.class, () -> {
                service.register(request);
            });

            Assertions.assertEquals(ServerException.Reason.BAD_REQUEST, thrown.getReason());
            Assertions.assertEquals(AuthenticationService.MISSING_INFO_MESSAGE, thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }
    }

    @Test
    public void testRegisterDuplicateEmail() throws ServerException {
        RegisterRequest request = new RegisterRequest("email@example.com", "username", "password");
        service.register(request);
        ServerException thrown = Assertions.assertThrows(ServerException.class, () -> service.register(request));

        Assertions.assertEquals(ServerException.Reason.ALREADY_EXISTS, thrown.getReason());
        Assertions.assertEquals(AuthenticationService.EMAIL_ALREADY_EXISTS_MESSAGE, thrown.getMessage());
        Assertions.assertNull(thrown.getCause());
    }

    @Test
    public void testLoginSuccess() throws ServerException {
        User user = new User(UUID.randomUUID(), "email@example.com", "password");
        dataAccessProvider.userDao().create(user);

        User request = new User(null, user.email(), user.password());
        long before = System.currentTimeMillis();
        Authentication auth = service.login(request);
        long after = System.currentTimeMillis();

        Assertions.assertNotNull(auth);
        Assertions.assertNotNull(auth.token());
        Assertions.assertTrue(auth.createdAt() >= before && auth.createdAt() <= after);
        Assertions.assertEquals(user.id(), auth.userId());
    }

    @Test
    public void testLoginBadRequest() throws ServerException {
        User[] requests = {
                null,
                new User(null, null, "password"),
                new User(null, "email@exmaple.com", null)
        };
        for (User request : requests) {
            ServerException thrown = Assertions.assertThrows(ServerException.class, () -> {
                service.login(request);
            });

            Assertions.assertEquals(ServerException.Reason.BAD_REQUEST, thrown.getReason());
            Assertions.assertEquals(AuthenticationService.MISSING_INFO_MESSAGE, thrown.getMessage());
            Assertions.assertNull(thrown.getCause());
        }
    }

    @Test
    public void testLoginIncorrectInfo() throws ServerException {
        String email = "email@example.com";
        String password = "password";

        User request = new User(null, email, password);
        ServerException thrown = Assertions.assertThrows(ServerException.class, () -> service.login(request));

        Assertions.assertEquals(ServerException.Reason.UNAUTHORIZED, thrown.getReason());
        Assertions.assertEquals(AuthenticationService.INCORRECT_LOGIN_INFO_MESSAGE, thrown.getMessage());
        Assertions.assertNull(thrown.getCause());

        dataAccessProvider.userDao().create(new User(UUID.randomUUID(), email, BCrypt.hashpw(password, BCrypt.gensalt())));
        User secondRequest = new User(null, email, "wrong " + password);
        thrown = Assertions.assertThrows(ServerException.class, () -> service.login(secondRequest));

        Assertions.assertEquals(ServerException.Reason.UNAUTHORIZED, thrown.getReason());
        Assertions.assertEquals(AuthenticationService.INCORRECT_LOGIN_INFO_MESSAGE, thrown.getMessage());
        Assertions.assertNull(thrown.getCause());
    }

    @Test
    public void logoutSuccess() throws ServerException {
        Authentication auth = new Authentication(UUID.randomUUID(), UUID.randomUUID(), System.currentTimeMillis());
        dataAccessProvider.authDao().create(auth);
        service.logout(auth);

        Assertions.assertNull(dataAccessProvider.authDao().read(auth.token()));
    }

    @Test
    public void authenticateSuccess() throws ServerException {
        Authentication auth = new Authentication(UUID.randomUUID(), UUID.randomUUID(), System.currentTimeMillis());
        dataAccessProvider.authDao().create(auth);
        Authentication authenticated = service.authenticate(auth.token());
        Assertions.assertEquals(auth, authenticated);
    }

    @Test
    public void authenticateBadToken() throws ServerException {
        Assertions.assertNull(service.authenticate(UUID.randomUUID()));
    }

    @Test
    public void authenticateOldToken() throws ServerException {
        Authentication auth = new Authentication(UUID.randomUUID(), UUID.randomUUID(),
                System.currentTimeMillis() - 2 * TOKEN_EXPIRATION_DURATION);
        dataAccessProvider.authDao().create(auth);
        Authentication authenticated = service.authenticate(auth.token());
        Assertions.assertNull(authenticated);
    }
}