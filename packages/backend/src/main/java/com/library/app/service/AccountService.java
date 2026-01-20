package com.library.app.service;

import com.library.app.Main;
import com.library.app.domain.account.Account;
import com.library.app.domain.account.AccountId;
import com.library.app.utils.EmailValidator;

public class AccountService {
    private Main main;

    public AccountService(Main main) {
        this.main = main;
    }

    public Account createAccount(String email, String password) {
        if (!EmailValidator.isValid(email))
            throw new IllegalArgumentException("Invalid email address");

        return new Account(AccountId.random(), email, password);
    }

    public void showAccount(Account account) {
        System.out.println("Account: " + account.getId());
        System.out.println("Account Email: " + account.getEmail());
        System.out.println("Account Borrowed Books Count: " + account.getBorrowedBooksCount());
        System.out.println("Borrowed books: ");
        account.getBorrowedBooks().forEach(book -> System.out.println(book.show()));
    }

    public boolean isBorrowedBooksLimit(Account account) {
        return account.getBorrowedBooksCount() >= 5;
    }
}
