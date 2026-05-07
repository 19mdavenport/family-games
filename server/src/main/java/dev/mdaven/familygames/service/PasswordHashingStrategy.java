package dev.mdaven.familygames.service;

import org.mindrot.jbcrypt.BCrypt;

public interface PasswordHashingStrategy {
    String hash(String password);
    boolean checkPassword(String plaintext, String hashed);


}
