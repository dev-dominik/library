package com.library.app.domain.book;

import java.util.UUID;

public record BookId(UUID value) {

    public BookId {
        if (value == null) {
            throw new IllegalArgumentException("BookId cannot be null");
        }
    }

    public static BookId random() {
        return new BookId(UUID.randomUUID());
    }
}
