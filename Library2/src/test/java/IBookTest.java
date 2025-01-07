package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IBookTest {

    private Book book;

    @BeforeEach
    void setUp()
    {
        // Setup book with initial values
        book = new Book("Title1", "Author1", "physical", "Category1");
    }

    @Test
    void testBookAttributes() {
        // Verify initial attributes
        assertEquals("physical", book.getType());
        assertEquals("Title1", book.getTitle());
        assertEquals("Author1", book.getAuthor());
        assertTrue(book.isAvailable());

        // Modify and validate attributes
        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");
        book.setCategory("Fiction");

        assertEquals("Updated Title", book.getTitle());
        assertEquals("Updated Author", book.getAuthor());
        assertEquals("Fiction", book.getCategory());
    }

    @Test
    void testBorrowBook() {
        // Verify initial availability and borrowing behavior
        assertTrue(book.isAvailable());
        assertTrue(book.borrow()); // Book should be borrowed
        assertFalse(book.isAvailable()); // Now unavailable
        assertFalse(book.borrow()); // Should not be able to borrow again while already borrowed
    }

    @Test
    void testReturnBook() {
        // Verify return behavior
        book.borrow();
        assertFalse(book.isAvailable()); // Book is not available after borrowing
        assertTrue(book.returnBook()); // Return the book successfully
        assertTrue(book.isAvailable()); // Book should be available after return
        assertFalse(book.returnBook()); // Should not be able to return again if already returned
    }

    @Test
    void testBookAvailability() {
        // Verify setting and getting availability
        assertTrue(book.isAvailable());
        book.setAvailable(false);
        assertFalse(book.isAvailable()); // Book should be unavailable after setting it to false

        // Reset availability and verify
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    // Additional test case for edge cases
    @Test
    void testBorrowingEdgeCases() {
        // Ensure that borrowing and returning can't happen under unexpected conditions
        book.setAvailable(false); // Make the book unavailable
        assertFalse(book.borrow()); // Should fail to borrow when not available

        // Return it and borrow again
        book.setAvailable(true);
        assertTrue(book.borrow()); // Should be able to borrow when available
        assertFalse(book.borrow()); // Can't borrow again after it's already borrowed
    }

    @Test
    void testMultipleBooks() {
        // Testing behavior with multiple books
        Book book2 = new Book("Title2", "Author2", "digital", "Category2");

        assertTrue(book.isAvailable());
        assertTrue(book2.isAvailable());

        // Borrow both books and check availability
        book.borrow();
        book2.borrow();

        assertFalse(book.isAvailable());
        assertFalse(book2.isAvailable());

        // Return both books and check availability
        book.returnBook();
        book2.returnBook();

        assertTrue(book.isAvailable());
        assertTrue(book2.isAvailable());
    }
}
