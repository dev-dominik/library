package com.library.app;

import java.util.List;

import com.library.app.domain.account.Account;
import com.library.app.domain.account.AccountId;
import com.library.app.domain.book.Book;
import com.library.app.domain.owner.Owner;
import com.library.app.domain.user.User;
import com.library.app.domain.user.UserId;
import com.library.app.service.BookService;
import com.library.app.web.BookController;
import com.library.tests.ManualTests;

public class Main {

    private final BookService bookService;
    private final BookController bookController;

    private User user;
    private Owner owner;

    public Main() {
        this.bookService = new BookService();
        this.bookController = new BookController(this);
    }

    public User getUser() {
        return user;
    }

    public Owner getOwner() {
        return owner;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static void main(String[] args) {
        new Main().init();

        ManualTests.runAllFixed();
    }

    public void init() {
        try {
            initUsers();

            // // add books
            // List<Book> cobenBooks = createCobenBooks();
            // for (Book book : cobenBooks)
            // bookController.addBookToLibraryCatalog(owner, book);

            // // search books
            // bookController.showLibraryCatalog();

            // // remove book
            // bookController.removeBookFromLibraryCatalog(owner,
            // cobenBooks.get(0).getId());

            // // borrow book
            // bookController.borrowBook(user, cobenBooks.get(1));

            // // return book
            // bookController.markBookAsReturned(user, cobenBooks.get(3));
        } catch (RuntimeException e) {
            System.err.println("Application error: " + e.getMessage());
        }
    }

    private void initUsers() {
        Account userAccount = new Account(AccountId.random(), "user@gmail.com", "password");
        this.user = new User(UserId.random(), userAccount, "Jan", "Kowalski");

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
                        .build());
    }
}
