package main.java.com.library.app.domain.book;

import java.util.Objects;

public class Book {

    private BookId id;
    private String title;
    private String author;
    private int yearPublished;
    private String category;
    private boolean borrowed;

    private Book() {
    }

    public boolean isAvailable() {
        return !borrowed;
    }

    public BookId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public Book setTitle(String title) {
        this.title = Objects.requireNonNull(title);
        return this;
    }

    public Book setAuthor(String author) {
        this.author = Objects.requireNonNull(author);
        return this;
    }

    public Book setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
        return this;
    }

    public Book setCategory(String category) {
        this.category = Objects.requireNonNull(category);
        return this;
    }

    public Book setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final Book book = new Book();

        public Builder id(BookId id) {
            book.id = id;
            return this;
        }

        public Builder title(String title) {
            book.title = title;
            return this;
        }

        public Builder author(String author) {
            book.author = author;
            return this;
        }

        public Builder yearPublished(int yearPublished) {
            book.yearPublished = yearPublished;
            return this;
        }

        public Builder category(String category) {
            book.category = category;
            return this;
        }

        public Builder borrowed(boolean borrowed) {
            book.borrowed = borrowed;
            return this;
        }

        public Book build() {
            if (book.id == null) {
                book.id = BookId.random(); 
            }
            validate(book);
            return book;
        }

        private void validate(Book book) {
            if (book.title == null || book.title.isBlank()) 
                throw new IllegalStateException("Title cannot be empty");
            if (book.author == null || book.author.isBlank()) 
                throw new IllegalStateException("Author cannot be empty");
            if (book.yearPublished <= 0) 
                throw new IllegalStateException("Year published must be positive");
            if (book.category == null || book.category.isBlank()) 
                throw new IllegalStateException("Category cannot be empty");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", yearPublished=" + yearPublished +
            ", category='" + category + '\'' +
            ", borrowed=" + borrowed +
            '}';
    }
}
