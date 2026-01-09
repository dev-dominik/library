package com.library.app.domain.loan;

import java.time.LocalDate;
import java.util.Objects;

import com.library.app.domain.book.Book;
import com.library.app.domain.user.User;

public class Loan {

    private final LoanId id;
    private final Book book;
    private final User user;
    private final LocalDate loanDate;

    private LocalDate returnDate;
    private int penalty;

    public Loan(LoanId id, Book book, User user, LocalDate loanDate) {
        this.id = Objects.requireNonNull(id);
        this.book = Objects.requireNonNull(book);
        this.user = Objects.requireNonNull(user);
        this.loanDate = Objects.requireNonNull(loanDate);
        this.penalty = 0;
    }

    public LoanId getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getPenalty() {
        return penalty;
    }

    public boolean isReturned() {
        return returnDate != null;
    }
}
