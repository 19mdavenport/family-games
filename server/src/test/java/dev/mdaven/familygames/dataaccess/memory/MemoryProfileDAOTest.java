package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.ProfileDAO;
import dev.mdaven.familygames.dataaccess.ProfileDAOTest;

public class MemoryProfileDAOTest extends ProfileDAOTest {

    @Override
    protected ProfileDAO createDAO() {
        return new MemoryProfileDAO();
    }
}