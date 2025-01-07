package org.example.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;

public class IPhysicalBookTest {

    @Test
    public void testPhysicalBookAttributes()
    {
        Book book = new Book("Title", "Author", "Category", "Fiction");
        Assertions.assertEquals("Fiction", book.getCategory(), "Category should match the one provided.");
    }

    @Test
    void testSetShelfLocation()
    {
        PhysicalBook physicalBook = new PhysicalBook("Title", "Author", "Category", "Shelf B");

        // Update shelf location
        physicalBook.setShelfLocation("Shelf C");
        assertEquals("Shelf C", physicalBook.getShelfLocation(), "Shelf location should be updated to 'Shelf C'.");

        // Test invalid shelf location
        Exception exception = assertThrows(IllegalArgumentException.class, () -> physicalBook.setShelfLocation(""));
        assertEquals("Shelf location cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testBorrowAndReturnPhysicalBook()
    {
        PhysicalBook physicalBook = new PhysicalBook("Title", "Author", "Category", "Shelf B");

        // Borrow the book
        assertTrue(physicalBook.borrow(), "Physical book should be borrowable when available.");
        assertFalse(physicalBook.isAvailable(), "Physical book should not be available after being borrowed.");

        // Return the book
        assertTrue(physicalBook.returnBook(), "Physical book should be returnable after being borrowed.");
        assertTrue(physicalBook.isAvailable(), "Physical book should be available after being returned.");
    }

    @Test
    void testInvalidPhysicalBookCreation()
    {
        // Test null shelf location
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new PhysicalBook("Title", "Author", "Category", null));
        assertEquals("Shelf location cannot be null or empty.", exception.getMessage());

        // Test empty shelf location
        exception = assertThrows(IllegalArgumentException.class,
                () -> new PhysicalBook("Title", "Author", "Category", ""));
        assertEquals("Shelf location cannot be null or empty.", exception.getMessage());
    }
}
