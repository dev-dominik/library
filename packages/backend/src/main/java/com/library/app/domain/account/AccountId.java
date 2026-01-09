package com.library.app.domain.account;

import java.util.UUID;

public record AccountId(UUID value) {

    public AccountId {
        if (value == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
    }

    public static AccountId random() {
        return new AccountId(UUID.randomUUID());
    }
}
