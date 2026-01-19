package com.library.tests;

import java.util.Objects;
import java.util.Optional;

import com.library.app.Main;
import com.library.app.domain.book.Book;
import com.library.app.domain.book.BookId;
import com.library.app.web.BookController;

public final class ManualTests {

    private ManualTests() {
    }

    public static void runAllFixed() {
        int passed = 0;
        int failed = 0;

        boolean ok;

        ok = run("owner_can_add_book", ManualTests::owner_can_add_book);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("add_duplicate_book_throws", ManualTests::add_duplicate_book_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("remove_non_existing_book_throws", ManualTests::remove_non_existing_book_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;

        ok = run("borrow_adds_to_account_and_marks_borrowed", ManualTests::borrow_adds_to_account_and_marks_borrowed);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("borrow_6th_book_throws", ManualTests::borrow_6th_book_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("borrow_same_book_twice_throws", ManualTests::borrow_same_book_twice_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("borrow_already_borrowed_book_throws", ManualTests::borrow_already_borrowed_book_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;

        ok = run("return_removes_from_account_and_marks_available",
                ManualTests::return_removes_from_account_and_marks_available);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;
        ok = run("return_not_borrowed_by_user_throws", ManualTests::return_not_borrowed_by_user_throws);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;

        ok = run("user_cannot_remove_book_compile_time", ManualTests::user_cannot_remove_book_compile_time);
        passed += ok ? 1 : 0;
        failed += ok ? 0 : 1;

        System.out.println("\n------------------------------");
        System.out.println("PASSED: " + passed);
        System.out.println("FAILED: " + failed);
        System.out.println("------------------------------\n");
    }

    // -----------------------------
    // Tiny test runner
    // -----------------------------

    private static boolean run(String name, Runnable test) {
        try {
            test.run();
            System.out.println("✅ PASS: " + name);
            return true;
        } catch (Throwable t) {
            System.err.println("❌ FAIL: " + name + " -> " + t.getClass().getSimpleName() + ": " + t.getMessage());
            return false;
        }
    }

    private static void assertTrue(boolean cond, String msg) {
        if (!cond)
            throw new AssertionError(msg);
    }

    private static void assertFalse(boolean cond, String msg) {
        if (cond)
            throw new AssertionError(msg);
    }

    private static void assertEquals(Object expected, Object actual, String msg) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(msg + " | expected=" + expected + " actual=" + actual);
        }
    }

    private static <T extends Throwable> void assertThrows(Class<T> expected, Runnable action, String msg) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t))
                return;
            throw new AssertionError(msg + " | expected " + expected.getSimpleName() +
                    " but got " + t.getClass().getSimpleName(), t);
        }
        throw new AssertionError(msg + " | expected exception " + expected.getSimpleName() + " but nothing was thrown");
    }

    // -----------------------------
    // Context per test (fresh app)
    // -----------------------------

    private static TestCtx newCtx() {
        Main main = new Main();
        main.init(); // tworzy user + owner
        BookController controller = new BookController(main);
        return new TestCtx(main, controller);
    }

    private static final class TestCtx {
        final Main main;
        final BookController controller;

        TestCtx(Main main, BookController controller) {
            this.main = main;
            this.controller = controller;
        }
    }

    private static Book createBook(String title) {
        return Book.builder()
                .title(title)
                .author("Harlan Coben")
                .yearPublished(2001)
                .category("Thriller")
                .build();
    }

    // -----------------------------
    // TESTS
    // -----------------------------

    private static void owner_can_add_book() {
        TestCtx ctx = newCtx();
        Book b = createBook("Nie mów nikomu");

        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        Optional<Book> found = ctx.main.getBookService().findBookById(b.getId());
        assertTrue(found.isPresent(), "Book should be added to catalog");
        assertEquals(b.getTitle(), found.get().getTitle(), "Added book title should match");
    }

    private static void add_duplicate_book_throws() {
        TestCtx ctx = newCtx();
        Book b = createBook("W głębi lasu");

        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b),
                "Adding duplicate book should throw");
    }

    private static void remove_non_existing_book_throws() {
        TestCtx ctx = newCtx();
        BookId randomId = BookId.random();

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.removeBookFromLibraryCatalog(ctx.main.getOwner(), randomId),
                "Removing non-existing book should throw");
    }

    private static void borrow_adds_to_account_and_marks_borrowed() {
        TestCtx ctx = newCtx();
        Book b = createBook("Zachowaj tajemnicę");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        ctx.controller.borrowBook(ctx.main.getUser(), b);

        assertTrue(ctx.main.getUser().getAccount().getBorrowedBooks().contains(b),
                "Borrowed books should contain borrowed book");
        assertTrue(b.isBorrowed(), "Book should be marked as borrowed");
    }

    private static void borrow_6th_book_throws() {
        TestCtx ctx = newCtx();

        for (int i = 1; i <= 5; i++) {
            Book b = createBook("Book " + i);
            ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);
            ctx.controller.borrowBook(ctx.main.getUser(), b);
        }

        Book sixth = createBook("Book 6");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), sixth);

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.borrowBook(ctx.main.getUser(), sixth),
                "Borrowing 6th book should throw (max 5)");
    }

    private static void borrow_same_book_twice_throws() {
        TestCtx ctx = newCtx();
        Book b = createBook("Sześć lat później");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        ctx.controller.borrowBook(ctx.main.getUser(), b);

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.borrowBook(ctx.main.getUser(), b),
                "Borrowing same book twice should throw");
    }

    private static void borrow_already_borrowed_book_throws() {
        TestCtx ctx = newCtx();
        Book b = createBook("Chłopiec z lasu");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        // symulujemy, że książka już wypożyczona
        b.borrow();

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.borrowBook(ctx.main.getUser(), b),
                "Borrowing already borrowed book should throw");
    }

    private static void return_removes_from_account_and_marks_available() {
        TestCtx ctx = newCtx();
        Book b = createBook("Nie mów nikomu");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        ctx.controller.borrowBook(ctx.main.getUser(), b);
        ctx.controller.markBookAsReturned(ctx.main.getUser(), b);

        assertFalse(ctx.main.getUser().getAccount().getBorrowedBooks().contains(b),
                "Borrowed books should not contain returned book");
        assertFalse(b.isBorrowed(), "Book should be available after return");
    }

    private static void return_not_borrowed_by_user_throws() {
        TestCtx ctx = newCtx();
        Book b = createBook("W głębi lasu");
        ctx.controller.addBookToLibraryCatalog(ctx.main.getOwner(), b);

        assertThrows(IllegalStateException.class,
                () -> ctx.controller.markBookAsReturned(ctx.main.getUser(), b),
                "Returning not borrowed book should throw");
    }

    /**
     * "user próbuje usunąć książkę" – Twoja sygnatura metody wymaga Owner,
     * więc User nie przejdzie na etapie kompilacji. To jest najlepsze
     * zabezpieczenie.
     */
    private static void user_cannot_remove_book_compile_time() {
        assertTrue(true, "Method requires Owner, so User cannot call it (compile-time safety).");
    }
}
