package com.library.app;

import com.library.app.domain.book.Book;

public class Main {

    public static void main(String[] args) {
        Book book = Book.builder()
            .title("Effective Java")
            .author("Joshua Bloch")
            .yearPublished(2018)
            .category("Programming")
            .build();

        System.out.println(book.show());
    }
}
