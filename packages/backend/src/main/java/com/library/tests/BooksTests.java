package com.library.tests;

import com.library.app.Main;
import com.library.app.domain.book.BookId;
import com.library.app.domain.librarycatalog.LibraryCatalog;
import com.library.app.domain.owner.Owner;
import com.library.app.web.BookController;

public class BooksTests {
    private Main main;

    public BooksTests(Main main) {
        this.main = main;
    }

    public void runTest() {
        runSafe(() -> showBooks());
        runSafe(() -> removeBookWhichDoesNotExist(this.main.getOwner()));
        runSafe(() -> addBookWchichAlreadyExists(this.main.getOwner()));
    }

    private void showBooks() {
        System.out.println("------------------------------");
        System.out.println("Show books test: ");
        this.main.getBookController().showLibraryCatalog();
        System.out.println("------------------------------");
    }

    private void removeBookWhichDoesNotExist(Owner owner) {
        System.out.println("------------------------------");
        System.out.println("Remove book which does not exist: ");
        this.main.getBookController().removeBookFromLibraryCatalog(owner, BookId.random());
        System.out.println("------------------------------");
    }

    private void addBookWchichAlreadyExists(Owner owner) {
        System.out.println("------------------------------");
        System.out.println("Add book which already exists test: ");
        BookController bookController = this.main.getBookController();
        bookController.addBookToLibraryCatalog(owner, LibraryCatalog.getInstance().getBooks().get(0));
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
