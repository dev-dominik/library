package com.library.app.web;

import com.library.app.Main;
import com.library.app.domain.account.Account;
import com.library.app.domain.user.User;
import com.library.app.domain.user.UserId;
import com.library.app.utils.Validation;

public class AccountController {
    private Main main;

    public AccountController(Main main) {
        this.main = main;
    }

    public User createAccount(String firstName, String lastName, String email, String password) {
        Validation.requireNotBlank(firstName, "First name");
        Validation.requireLength(firstName, "First name", 2, 40);

        Validation.requireNotBlank(lastName, "Last name");
        Validation.requireLength(lastName, "Last name", 2, 60);

        Validation.requireNotBlank(email, "Email");
        Validation.requireValidEmail(email);

        Validation.requireNotBlank(password, "Password");
        Validation.requireStrongPassword(password);

        Account userAccount = this.main.getAccountService().createAccount(email, password);
        if (userAccount == null)
            throw new NullPointerException("User account is null!");

        return new User(UserId.random(), userAccount, firstName, lastName);
    }

    public void checkAccount(User user) {
        if (user == null)
            throw new NullPointerException("User is null!");
        if (user.getAccount() == null)
            throw new NullPointerException("User account is null!");

        main.getAccountService().showAccount(user.getAccount());
    }
}
