package com.library.app.domain.book;

public class Book {

    private final BookId id;
    private final String title;
    private final String author;
    private final int yearPublished;
    private final String category;
    private boolean borrowed;

    private Book(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.author = b.author;
        this.yearPublished = b.yearPublished;
        this.category = b.category;
        this.borrowed = b.borrowed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isAvailable() {
        return !borrowed;
    }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("Book is already borrowed");
        }
        this.borrowed = true;
    }

    public void returnBook() {
        if (!borrowed) {
            throw new IllegalStateException("Book is not borrowed");
        }
        this.borrowed = false;
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

    public String show() {
        return """
                Book
                   ───────────────────
                   ID: %s
                   Title: %s
                   Author: %s
                   Year published: %d
                   Category: %s
                   Status: %s
                   """.formatted(
                id,
                title,
                author,
                yearPublished,
                category,
                borrowed ? "BORROWED" : "AVAILABLE");
    }

    public static final class Builder {
        private BookId id = BookId.random();
        private String title;
        private String author;
        private int yearPublished;
        private String category;
        private boolean borrowed;

        public Builder id(BookId id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder yearPublished(int year) {
            this.yearPublished = year;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder borrowed(boolean borrowed) {
            this.borrowed = borrowed;
            return this;
        }

        public Book build() {
            validate();
            return new Book(this);
        }

        private void validate() {
            if (title == null || title.isBlank())
                throw new IllegalStateException("Title is required");
            if (author == null || author.isBlank())
                throw new IllegalStateException("Author is required");
            if (yearPublished <= 0)
                throw new IllegalStateException("Year published must be positive");
            if (category == null || category.isBlank())
                throw new IllegalStateException("Category is required");
        }
    }
}
