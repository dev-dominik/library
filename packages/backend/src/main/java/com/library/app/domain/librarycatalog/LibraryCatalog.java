package com.library.app.domain.librarycatalog;

import com.library.app.domain.book.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LibraryCatalog {

    private static LibraryCatalog instance;

    private final List<Book> books;
    private final int maxLoans;

    private LibraryCatalog(int maxLoans) {
        this.books = new ArrayList<>();
        this.maxLoans = maxLoans;
    }

    public static synchronized LibraryCatalog getInstance() {
        if (instance == null) {
            instance = new LibraryCatalog(5);
        }
        return instance;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
