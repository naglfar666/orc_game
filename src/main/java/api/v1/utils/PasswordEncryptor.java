package api.v1.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public abstract class PasswordEncryptor {

    private PasswordEncryptor() {
        throw new IllegalStateException("Don't try to initiate abstract class sucker");
    }

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String password, String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }
}
