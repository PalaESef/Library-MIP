package org.example.models;

import org.example.interfaces.IBook;
import org.example.interfaces.IBorrowable;

public class Book implements IBook, IBorrowable
{
    private String title;
    private String author;
    private String category;
    private boolean available;
    private String type;

    public Book() {
        this.available = true;
        this.category = "Default Category";
    }

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
        this.type = "unknown";
    }

    public Book(String title, String author, String type, String category) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty.");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty.");
        }

        this.title = title;
        this.author = author;
        this.type = type;
        this.category = category;
        this.available = true;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean borrow() {
        if (available) {
            available = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean returnBook() {
        if (!available) {
            available = true;
            return true;
        }
        return false;
    }
}
