package org.example.interfaces;

public interface IBook
{
    String getTitle();
    void setTitle(String title);

    String getAuthor();
    void setAuthor(String author);

    String getCategory();
    void setCategory(String category);

    boolean isAvailable();
    void setAvailable(boolean available);

    String getType();
    void setType(String type);
}
