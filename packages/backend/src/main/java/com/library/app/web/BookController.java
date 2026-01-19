package com.library.app.web;

import java.util.Objects;

import com.library.app.Main;
import com.library.app.domain.account.Account;
import com.library.app.domain.book.Book;
import com.library.app.domain.book.BookId;
import com.library.app.domain.librarycatalog.LibraryCatalog;
import com.library.app.domain.owner.Owner;
import com.library.app.domain.user.User;
import com.library.app.service.BookService;

public class BookController {
    private Main main;

    public BookController(Main main) {
        this.main = main;
    }

    public void addBookToLibraryCatalog(Owner owner, Book book) {
        Objects.requireNonNull(owner, "Owner cannot be null");
        validateBook(book);

        BookService bookService = Objects.requireNonNull(
                main.getBookService(),
                "Book service is null!");

        boolean exists = bookService
                .findBook(book.getTitle(), book.getAuthor())
                .isPresent();

        if (exists)
            throw new IllegalStateException("This book already exists in library catalog!");

        bookService.addBook(book);
        System.out.println("Book added!");
    }

    public void removeBookFromLibraryCatalog(Owner owner, BookId bookId) {
        Objects.requireNonNull(owner, "Owner cannot be null");
        Objects.requireNonNull(bookId, "BookId cannot be null");

        BookService bookService = Objects.requireNonNull(
                main.getBookService(),
                "Book service is null!");

        Book book = bookService.findBookById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "This book does not exist in library catalog!"));

        bookService.removeBook(book);
        System.out.println("Book removed!");
    }

    public void borrowBook(User user, Book book) {
        this.validateBook(book);
        Objects.requireNonNull(user, "User cannot be null");
        Objects.requireNonNull(book, "Book cannot be null");

        BookService bookService = Objects.requireNonNull(
                main.getBookService(),
                "Book service is null!");

        Account userAccount = user.getAccount();
        if (userAccount == null)
            throw new NullPointerException("User account cannot be null!");

        if (userAccount.getBorrowedBooks().size() >= 5)
            throw new IllegalStateException("You have already reached the maximum number of borrowed books!");

        if (userAccount.getBorrowedBooks().contains(book))
            throw new IllegalStateException("You already have borrowed this book!");

        bookService.borrowBook(book);
        user.getAccount().addBorrowedBook(book);
    }

    public void showLibraryCatalog() {
        System.out.println("Books in library catalog:");
        LibraryCatalog.getInstance().getBooks().forEach(book -> {
            System.out.println(book.show());
        });
    }

    public void markBookAsReturned(User user, Book book) {
        Objects.requireNonNull(user, "User cannot be null");
        Objects.requireNonNull(book, "Book cannot be null");

        Account userAccount = Objects.requireNonNull(user.getAccount(), "User account cannot be null!");

        if (!userAccount.getBorrowedBooks().contains(book)) {
            throw new IllegalStateException("You have not borrowed this book!");
        }

        BookService bookService = Objects.requireNonNull(main.getBookService(), "Book service is null!");

        userAccount.getBorrowedBooks().remove(book);

        bookService.markAsReturned(book);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalStateException("Title is required");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalStateException("Author is required");
        if (book.getYearPublished() <= 0)
            throw new IllegalStateException("Year published must be positive");
        if (book.getCategory() == null || book.getCategory().isBlank())
            throw new IllegalStateException("Category is required");
    }
}
