package com.library.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.library.app.domain.book.Book;
import com.library.app.domain.book.BookId;
import com.library.app.domain.librarycatalog.LibraryCatalog;

public class BookService {
    private LibraryCatalog lc = LibraryCatalog.getInstance();

    public Optional<Book> findBook(String title, String author) {
        return lc.getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title)
                        && book.getAuthor().equalsIgnoreCase(author))
                .findFirst();
    }

    public Optional<Book> findBookById(BookId bookId) {
        return lc.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    public boolean returnBook(BookId bookId) {
        LibraryCatalog lc = LibraryCatalog.getInstance();
        Optional<Book> opt = lc.getBooks().stream().filter(book -> book.getId() == bookId).findFirst();
        if (opt.isPresent()) {
            Book b = opt.get();
            b.returnBook();
        }

        return opt.isPresent();
    }

    public List<Book> searchBooks(String title, String author) {
        return lc.getBooks().stream()
                .filter(book -> (title == null || title.isBlank() || book.getTitle().equalsIgnoreCase(title)) &&
                        (author == null || author.isBlank() || book.getAuthor().equalsIgnoreCase(author)))
                .collect(Collectors.toList());
    }

    public List<Book> getBooks() {
        return this.lc.getBooks();
    }

    public void addBook(Book book) {
        lc.addBook(book);
    }

    public void removeBook(Book book) {
        lc.removeBook(book);
    }

    public boolean returnBook(Book book) {
        if (book == null)
            return false;

        boolean exists = lc.getBooks().stream().anyMatch(b -> b.getId().equals(book.getId()));
        if (!exists)
            return false;

        book.returnBook();
        return true;
    }

    public boolean borrowBook(Book book) {
        if (book == null)
            return false;

        Optional<Book> opt = lc.getBooks().stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst();

        if (opt.isEmpty())
            return false;

        opt.get().borrow();
        return true;
    }

    public boolean markAsBorrowed(Book book) {
        if (book == null)
            return false;

        Optional<Book> opt = lc.getBooks().stream()
                .filter(b -> b.getId().equals(book.getId()))
                .findFirst();

        if (opt.isEmpty())
            return false;

        opt.get().borrow();
        return true;
    }

    public boolean markAsReturned(Book book) {
        return returnBook(book);
    }
}
