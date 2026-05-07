package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.AuthenticationDAO;
import dev.mdaven.familygames.dataaccess.AuthenticationDAOTest;

public class MemoryAuthenticationDAOTest extends AuthenticationDAOTest {
    @Override
    protected AuthenticationDAO createDAO() {
        return new MemoryAuthenticationDAO();
    }
}
