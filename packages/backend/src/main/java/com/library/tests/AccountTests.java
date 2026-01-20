package com.library.tests;

import com.library.app.Main;
import com.library.app.domain.book.Book;
import com.library.app.domain.librarycatalog.LibraryCatalog;
import com.library.app.domain.user.User;
import com.library.app.web.AccountController;
import com.library.app.web.BookController;

public class AccountTests {
    private Main main;

    public AccountTests(Main main) {
        this.main = main;
    }

    public void runTests(User user) {
        runSafe(() -> borrowFiveBooks(user));
        runSafe(() -> borrowBookAbove(user));
        runSafe(() -> returnBook(user));
        runSafe(() -> borrowBookWhichDoesNotExist(user));
    }

    private void borrowFiveBooks(User user) {
        System.out.println("------------------------------");
        System.out.println("Borrow five books test: ");
        BookController bookController = this.main.getBookController();
        for (int x = 0; x <= 4; x++) {
            Book book = this.main.getBookService().getBooks().get(x);
            bookController.borrowBook(user, book);
        }

        System.out.println("User account: ");
        this.main.getAccountController().checkAccount(user);
        System.out.println("------------------------------");
    }

    private void borrowBookAbove(User user) {
        System.out.println("------------------------------");
        System.out.println("Borrow book if user have already 5 borrowed test: ");
        BookController bookController = this.main.getBookController();
        Book book = this.main.getBookService().getBooks().get(5);
        bookController.borrowBook(user, book);
        System.out.println("------------------------------");
    }

    private void returnBook(User user) {
        System.out.println("------------------------------");
        System.out.println("Return book test: ");
        BookController bookController = this.main.getBookController();
        AccountController accountController = this.main.getAccountController();
        bookController.markBookAsReturned(user, LibraryCatalog.getInstance().getBooks().get(0));
        System.out.println("User account after returning book");
        accountController.checkAccount(user);
        System.out.println("------------------------------");
    }

    private void borrowBookWhichDoesNotExist(User user) {
        System.out.println("------------------------------");
        BookController bookController = this.main.getBookController();
        Book book = Book.builder().title("Test").author("author").category("category").yearPublished(2016).build();
        bookController.borrowBook(user, book);
        System.out.println("------------------------------");
    }

    private void runSafe(Runnable test) {
        try {
            test.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
