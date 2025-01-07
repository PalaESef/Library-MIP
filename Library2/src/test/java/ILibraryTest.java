package org.example.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ILibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private User user1;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("Title1", "Author1", "Category1");
        book2 = new Book("Title2", "Author2", "Category2");
        user1 = new User("User1", 1);
    }

    @Test
    void testAddBook() {
        Book newBook = new Book("New Title", "New Author", "New Category");
        library.addBook(newBook);
        assertTrue(library.getBooks().contains(newBook), "Book should be added to the library.");
    }

    @Test
    void testRemoveBook() {
        library.addBook(book1); // Ensure book is added before removal
        library.removeBook(book1); // Remove the book
        assertFalse(library.getBooks().contains(book1), "Book should be removed from the library.");
    }

    @Test
    void testUpdateBook() {
        library.addBook(book1);
        library.updateBook("Title1", "Author1", "Updated Title", "Updated Author");

        // Verify that the book has been updated
        Book updatedBook = library.findBookByTitle("Updated Title");
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthor());
    }

    @Test
    void testRegisterUser() {
        library.registerUser(user1);
        assertTrue(library.getUsers().contains(user1), "User should be registered.");
    }

    @Test
    void testRemoveUser() {
        library.registerUser(user1);
        library.removeUser(user1);
        assertFalse(library.getUsers().contains(user1), "User should be removed.");
    }

    @Test
    public void testLoanBook() {
        library.addBook(book1);
        library.addBook(book2);
        library.registerUser(user1);

        // Ensure the book is available before loaning
        assertTrue(book1.isAvailable(), "The book should be available for borrowing.");

        library.loanBook(book1, user1); // Loan the book without due date

        // Assert the book is no longer available (should be loaned)
        assertFalse(book1.isAvailable(), "The book should no longer be available.");

        // Assert that the user borrowed the book
        boolean bookBorrowed = false;
        for (Book borrowedBook : user1.getBorrowedBooks()) {
            if (borrowedBook.getTitle().equals(book1.getTitle())) {
                bookBorrowed = true;
            }
        }

        assertTrue(bookBorrowed, "User should have borrowed the book.");
    }

    @Test
    void testReturnBook() {
        // Add and loan a book
        library.addBook(book1);
        library.registerUser(user1);
        library.loanBook(book1, user1);

        // Verify the book is loaned to the user
        assertFalse(book1.isAvailable(), "The book should not be available after being loaned.");
        assertTrue(user1.getBorrowedBooks().contains(book1), "User should have borrowed the book.");

        // Return the book
        library.returnBook(book1, user1);

        // Assert the book is available again after being returned
        assertTrue(book1.isAvailable(), "The book should be available after being returned.");
        assertFalse(user1.getBorrowedBooks().contains(book1), "User should no longer have the book.");
    }
}
