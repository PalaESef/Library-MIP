package org.example.interfaces;

public interface IPhysicalBook extends IBook, IBorrowable
{
    String getShelfLocation();
    void setShelfLocation(String shelfLocation);
}
