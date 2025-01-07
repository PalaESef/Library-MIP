package org.example.models;

import org.example.interfaces.IEBook;

public class EBook extends Book implements IEBook
{
    private String downloadLink;

    public EBook()
    {
        super();
    }

    public EBook(String title, String author, String category, String downloadLink)
    {
        super(title, author, category);
        this.downloadLink = downloadLink;
    }

    @Override
    public String getDownloadLink()
    {
        return downloadLink;
    }

    @Override
    public void setDownloadLink(String downloadLink)
    {
        this.downloadLink = downloadLink;
    }
}
