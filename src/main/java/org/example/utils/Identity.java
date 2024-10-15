package org.example.utils;

import org.example.dto.PrivateUser;

import java.time.LocalDateTime;

public class Identity {
    private LocalDateTime expirationDate;
    final private PrivateUser user;
    private String token;

    public Identity(
        LocalDateTime expirationDate,
        PrivateUser user
    ) {
        this.expirationDate = expirationDate;
        this.user = user;

        LocalDateTime now = LocalDateTime.now();
        this.token = String.valueOf((this.user.id + this.user.name + this.expirationDate.toString() + now.hashCode()).hashCode());
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
}
