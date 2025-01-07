package org.example.models;

import org.example.interfaces.IUser;

import java.util.List;
import java.util.ArrayList;

public class User implements IUser
{
    private String name;
    private int userId;
    private List<Book> borrowedBooks; // List of borrowed Book objects
    private List<Book> allBooks; // List of all books the user has interacted with (borrowed or owned)

    // Constructor
    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>(); // Initialize borrowedBooks as an empty list
        this.allBooks = new ArrayList<>(); // Initialize allBooks as an empty list
    }

    // Getter and setter methods
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Add a borrowed book
    @Override
    public void addBorrowedBook(Book book)
    {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("Book is not available for borrowing.");
        }

        book.setAvailable(false); // Mark the book as not available
        borrowedBooks.add(book);   // Add the book to the user's borrowed list
    }

    // Return a borrowed book
    @Override
    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book); // Remove the book from the borrowed list
            book.setAvailable(true); // Mark the book as available again
        } else {
            throw new IllegalArgumentException("Book not found in the user's borrowed books.");
        }
    }

    // Find a borrowed book by title
    public Book findBorrowedBookByTitle(String title) {
        for (Book book : borrowedBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Return null if the book is not found
    }

    // Getter for allBooks
    public List<Book> getAllBooks() {
        return allBooks;
    }

    // Setter for allBooks (if needed)
    public void setAllBooks(List<Book> allBooks) {
        this.allBooks = allBooks;
    }

    // Getter for borrowedBooks (if needed)
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Setter for borrowedBooks (if needed)
    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void viewBorrowedBooks()
    {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.printf("Title: %s, Author: %s%n", book.getTitle(), book.getAuthor());
            }
        }
    }

}
