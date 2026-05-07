package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.UserDAO;
import dev.mdaven.familygames.dataaccess.UserDAOTest;

public class MemoryUserDAOTest extends UserDAOTest {

    @Override
    protected UserDAO createDAO() {
        return new MemoryUserDAO();
    }
}