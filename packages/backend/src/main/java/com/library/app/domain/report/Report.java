package com.library.app.domain.report;

public class Report {
    private int bookCount;
    private int userCount;

    public void setBooksCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public void setUsersCount(int userCount) {
        this.userCount = userCount;
    }

    public int getBooksCount() {
        return this.bookCount;
    }

    public int getUsersCount() {
        return this.userCount;
    }
}
