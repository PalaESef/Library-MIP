package org.example.interfaces;

import org.example.models.Book;
import java.util.List;

public interface IUser {
    String getName();
    void setName(String name);
    int getUserId();
    void setUserId(int userId);
    List<Book> getBorrowedBooks();
    void addBorrowedBook(Book book);  // Removed dueDate argument
    void returnBook(Book book);
}
