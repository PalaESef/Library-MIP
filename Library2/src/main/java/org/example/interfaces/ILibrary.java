package org.example.interfaces;
import org.example.models.Book;
import org.example.models.User;

public interface ILibrary
{
    void addBook(Book book);
    void removeBook(Book book);
    void updateBook(String currentTitle, String currentAuthor, String newTitle, String newAuthor);
    void registerUser(User user);
    void removeUser(User user);
    void loanBook(Book book, User user); // Removed dueDate
    void returnBook(Book book, User user);
}
