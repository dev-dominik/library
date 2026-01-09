package com.library.app.domain.librarycatalog;

import com.library.app.domain.book.Book;
import com.library.app.domain.loan.Loan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LibraryCatalog {

    private static LibraryCatalog instance;

    private final List<Book> books;
    private final List<Loan> loanHistory;
    private final int maxLoans;

    private LibraryCatalog(int maxLoans) {
        this.books = new ArrayList<>();
        this.loanHistory = new ArrayList<>();
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

    public void addLoanToHistory(Loan loan) {
        loanHistory.add(loan);
    }

    public List<Loan> getLoanHistory() {
        return Collections.unmodifiableList(loanHistory);
    }

    public int getMaxLoans() {
        return maxLoans;
    }
}
