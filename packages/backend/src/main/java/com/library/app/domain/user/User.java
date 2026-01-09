package com.library.app.domain.user;

import java.util.Objects;

import com.library.app.domain.account.Account;

public class User {

    private final UserId id;
    private Account account;
    private String firstName;
    private String lastName;

    public User(UserId id) {
        this.id = id;
    }

    public User(UserId id, Account account, String firstName, String lastName) {
        this.id = Objects.requireNonNull(id);
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserId getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}