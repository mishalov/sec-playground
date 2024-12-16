package org.example;

import org.example.dto.PrivateUser;
import org.example.utils.Identity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Security {
    private DataBase db;
    private final ArrayList<Identity> identities = new ArrayList<>();

    public Security(DataBase db) {
        this.db = db;
    }

    public void reset() {
        identities.clear();
    }

    public Identity authenticate(String username, String password) {
        try {
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(30);
            PrivateUser user = db.authenticate(username, encrypt(password));

            if (user == null) {
                return null;
            }

            Identity identity = new Identity(expirationDate, user.getId());
            identities.add(identity);
            return identity;
        } catch (NoSuchAlgorithmException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String password) throws NoSuchAlgorithmException {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

        return
            sha256hex;
    }

    public Identity checkToken(String token) {
        for (Identity identity : identities) {
            if (identity.isValid(token)) {
                return identity;
            }
        }
        return null;
    }

    public String getTokenFromHeader(String header) {
        if (header == null) {
            return null;
        }
        String[] parts = header.split(" ");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }
}
