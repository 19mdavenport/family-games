package dev.mdaven.familygames.dataaccess.memory;

import dev.mdaven.familygames.dataaccess.GameDAO;
import dev.mdaven.familygames.dataaccess.GameDAOTest;

public class MemoryGameDAOTest extends GameDAOTest {

    @Override
    protected GameDAO createDAO() {
        return new MemoryGameDAO();
    }
}