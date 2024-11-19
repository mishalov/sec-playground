package org.example.utils;

import org.example.dto.PrivateUser;

import java.time.LocalDateTime;

public class Identity {
    private LocalDateTime expirationDate;
    final private int userId;
    private String token;

    public Identity(
        LocalDateTime expirationDate,
        int userId
    ) {
        this.expirationDate = expirationDate;
        this.userId = userId;

        LocalDateTime now = LocalDateTime.now();
        this.token = String.valueOf((this.userId + this.expirationDate.toString() + now.hashCode()).hashCode());
    }


    public String getToken() {
        return this.token;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expirationDate);
    }

    public boolean isValid(String token) {
        return this.token.equals(token) && !isExpired();
    }

    public int getUserId() {
        return this.userId;
    }
}
