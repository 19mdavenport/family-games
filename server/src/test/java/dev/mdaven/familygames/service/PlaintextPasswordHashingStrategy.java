package dev.mdaven.familygames.service;

public class PlaintextPasswordHashingStrategy implements PasswordHashingStrategy {
    @Override
    public String hash(String password) {
        return password;
    }

    @Override
    public boolean checkPassword(String plaintext, String hashed) {
        return plaintext.equals(hashed);
    }
}
