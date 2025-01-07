package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IUserTest {

    private User user;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book("Book Title 1", "Author 1", "Category 1");
        book2 = new Book("Book Title 2", "Author 2", "Category 2");

        user = new User("John Doe", 1);

        // Ensure book1 and book2 are available for each test
        book1.setAvailable(true);
        book2.setAvailable(true);
    }

    @Test
    void testAddBorrowedBook() {
        // Reset availability to true before borrowing
        book1.setAvailable(true);

        assertTrue(book1.isAvailable(), "Book should be available before borrowing");

        user.addBorrowedBook(book1);  // Borrow the book

        // Ensure the book has been added to borrowed books
        assertTrue(user.getBorrowedBooks().contains(book1), "Book should be added to borrowed books");

        // Check if the book is no longer available
        assertFalse(book1.isAvailable(), "Book should be marked as unavailable after borrowing");
    }

    @Test
    void testReturnBook() {
        // Borrow the book
        user.addBorrowedBook(book1);

        assertFalse(book1.isAvailable(), "Book should be unavailable after being borrowed");

        // Return the book
        user.returnBook(book1);

        // Check if the book is returned properly
        assertFalse(user.getBorrowedBooks().contains(book1), "Book should be removed from borrowed books after return");
        assertTrue(book1.isAvailable(), "Book should be available after being returned");
    }

    @Test
    void testAddBorrowedBookWhenUnavailable() {
        book1.setAvailable(false);  // Explicitly make the book unavailable

        // Try borrowing an unavailable book
        assertThrows(IllegalArgumentException.class, () -> user.addBorrowedBook(book1),
                "Should throw exception when borrowing an unavailable book");
    }

    @Test
    void testReturnBookNotBorrowedByUser() {
        // Try returning a book that wasn't borrowed by the user
        assertThrows(IllegalArgumentException.class, () -> user.returnBook(book1),
                "Should throw exception when returning a book not borrowed by the user");
    }
}
