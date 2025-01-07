package org.example.models;

import java.util.ArrayList;
import java.util.List;
import org.example.interfaces.ILibrary;

public class Library implements ILibrary {

    private List<Book> books;
    private List<User> users;

    // Constructor initializes books and users lists
    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Returns the list of all books in the library
    public List<Book> getBooks() {
        return books;
    }

    // Returns the list of all users in the library
    public List<User> getUsers() {
        return users;
    }

    // Adds a book to the library
    @Override
    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        } else {
            throw new IllegalArgumentException("The book already exists in the library.");
        }
    }

    // Removes a book from the library
    @Override
    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
        } else {
            throw new IllegalArgumentException("The book does not exist in the library.");
        }
    }

    // Updates the details of a book by its current title and author
    @Override
    public void updateBook(String currentTitle, String currentAuthor, String newTitle, String newAuthor) {
        for (Book book : books) {
            if (book.getTitle().equals(currentTitle) && book.getAuthor().equals(currentAuthor)) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                return;
            }
        }
        throw new IllegalArgumentException("The book to update was not found in the library.");
    }

    // Finds a book in the library by its title
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null; // Or throw an exception if the book must exist
    }

    // Registers a new user to the library
    @Override
    public void registerUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        } else {
            throw new IllegalArgumentException("The user is already registered.");
        }
    }

    // Removes an existing user from the library
    @Override
    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
        } else {
            throw new IllegalArgumentException("The user is not registered.");
        }
    }

    // Loans a book to a user
    @Override
    public void loanBook(Book book, User user) {
        if (book == null || !book.isAvailable()) {
            throw new IllegalArgumentException("Book cannot be loaned.");
        }
        user.addBorrowedBook(book); // Add the book to the user's borrowed books list
        book.setAvailable(false); // Mark the book as not available
    }

    // Returns a loaned book from the user to the library
    @Override
    public void returnBook(Book book, User user) {
        if (!user.getBorrowedBooks().contains(book)) {
            throw new IllegalArgumentException("The user has not borrowed this book.");
        }

        // Remove the book from the user's borrowed books list
        user.returnBook(book);

        // Mark the book as available again in the library
        book.setAvailable(true);
    }
}
