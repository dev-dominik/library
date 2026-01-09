package com.library.app.domain.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.library.app.domain.book.Book;

public class Account {

    private final AccountId id;
    private String email;
    private String password;
    private int borrowedBooksCount;
    private List<Book> borrowedBooks;

    public Account(AccountId id) {
        this.id = Objects.requireNonNull(id);
        this.borrowedBooks = new ArrayList<>();
        this.borrowedBooksCount = 0;
    }

    public AccountId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooksCount;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email);
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = Objects.requireNonNull(borrowedBooks);
        this.borrowedBooksCount = borrowedBooks.size();
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
        borrowedBooksCount++;
    }

    public void removeBorrowedBook(Book book) {
        if (borrowedBooks.remove(book)) {
            borrowedBooksCount--;
        }
    }
}
