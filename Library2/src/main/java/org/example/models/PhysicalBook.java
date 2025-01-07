package org.example.models;

import org.example.interfaces.IPhysicalBook;

public class PhysicalBook extends Book implements IPhysicalBook
{
    private String shelfLocation;

    public PhysicalBook() {
        super();
    }

    public PhysicalBook(String title, String author, String category, String shelfLocation)
    {
        super(title, author, category);
        if (shelfLocation == null || shelfLocation.isEmpty())
        {
            throw new IllegalArgumentException("Shelf location cannot be null or empty.");
        }
        this.shelfLocation = shelfLocation;
    }


    @Override
    public String getShelfLocation()
    {
        return shelfLocation;
    }

    @Override
    public void setShelfLocation(String shelfLocation)
    {
        if (shelfLocation == null || shelfLocation.isEmpty())
        {
            throw new IllegalArgumentException("Shelf location cannot be null or empty.");
        }
        this.shelfLocation = shelfLocation;
    }
}
