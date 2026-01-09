package com.library.app.domain.owner;

import com.library.app.domain.account.Account;
import com.library.app.domain.user.User;
import com.library.app.domain.user.UserId;

public class Owner extends User {

    public Owner(UserId id) {
        super(id);
    }

    public Owner(UserId id, Account account, String firstName, String lastName) {
        super(id, account, firstName, lastName);
    }

    /**
     * Displays system report.
     */
    public void displayReport() {
        // not implemented yet
    }
}
