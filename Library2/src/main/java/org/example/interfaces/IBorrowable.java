package org.example.interfaces;

public interface IBorrowable
{
    boolean borrow();
    boolean returnBook();
    String getTitle(); //
    boolean isAvailable();
    void setAvailable(boolean available);
    String getAuthor();
}
