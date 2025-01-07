package org.example.interfaces;

public interface IEBook extends IBook, IBorrowable
{
    String getDownloadLink();
    void setDownloadLink(String downloadLink);
}
