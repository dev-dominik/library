package com.library.app;

import java.util.List;

import com.library.app.domain.account.Account;
import com.library.app.domain.account.AccountId;
import com.library.app.domain.book.Book;
import com.library.app.domain.owner.Owner;
import com.library.app.domain.user.User;
import com.library.app.domain.user.UserId;
import com.library.app.service.AccountService;
import com.library.app.service.BookService;
import com.library.app.web.AccountController;
import com.library.app.web.BookController;
import com.library.tests.AccountTests;
import com.library.tests.BooksTests;

public class Main {

    private final BookService bookService;
    private final BookController bookController;
    private final AccountService accountService;
    private final AccountController accountController;

    // tests
    private AccountTests accountTests;
    private BooksTests booksTests;

    private User user;
    private Owner owner;

    public Main() {
        this.bookService = new BookService();
        this.bookController = new BookController(this);
        this.accountService = new AccountService(this);
        this.accountController = new AccountController(this);

        this.accountTests = new AccountTests(this);
        this.booksTests = new BooksTests(this);
    }

    public User getUser() {
        return user;
    }

    public Owner getOwner() {
        return owner;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookService getBookService() {
        return bookService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
        try {
            initUsers();

            // add books
            List<Book> cobenBooks = createCobenBooks();
            for (Book book : cobenBooks)
                bookController.addBookToLibraryCatalog(owner, book);

            accountTests.runTests(user);
            booksTests.runTest();
        } catch (RuntimeException e) {
            System.err.println("Application error: " + e.getMessage());
        }
    }

    private void initUsers() {
        this.user = this.accountController.createAccount("Hubert", "Kowalski", "user@gmail.com", "Password1");

        // owner account must be created by system administrator, so we do not use
        // 'createAccount' method
        Account ownerAccount = new Account(AccountId.random(), "owner@gmail.com", "password");
        this.owner = new Owner(UserId.random(), ownerAccount, "Marek", "Adamczyk");
    }

    private List<Book> createCobenBooks() {
        return List.of(
                Book.builder()
                        .title("Nie mów nikomu")
                        .author("Harlan Coben")
                        .yearPublished(2001)
                        .category("Thriller")
                        .build(),
                Book.builder()
                        .title("W głębi lasu")
                        .author("Harlan Coben")
                        .yearPublished(2007)
                        .category("Thriller")
                        .build(),
                Book.builder()
                        .title("Zachowaj tajemnicę")
                        .author("Harlan Coben")
                        .yearPublished(2008)
                        .category("Thriller")
                        .build(),
                Book.builder()
                        .title("Sześć lat później")
                        .author("Harlan Coben")
                        .yearPublished(2013)
                        .category("Thriller")
                        .build(),
                Book.builder()
                        .title("Chłopiec z lasu")
                        .author("Harlan Coben")
                        .yearPublished(2020)
                        .category("Thriller")
                        .build(),
                Book.builder().title("Run Away").author("Harlan Coben").yearPublished(2019).category("Thriller")
                        .build());
    }
}
