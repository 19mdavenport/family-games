package dev.mdaven.familygames.service;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordHashingStrategy implements PasswordHashingStrategy {
    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
