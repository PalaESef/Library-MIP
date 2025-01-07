package org.example.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IEBookTest
{
    @Test
    void testEBookAttributes()
    {
        // Crearea unui obiect EBook
        EBook ebook = new EBook("EBook Title", "EBook Author", "Category", "www.downloadlink.com");

        // Verificarea atributelor
        assertEquals("EBook Title", ebook.getTitle());
        assertEquals("EBook Author", ebook.getAuthor());
        assertEquals("Category", ebook.getCategory());
        assertEquals("www.downloadlink.com", ebook.getDownloadLink());
        assertTrue(ebook.isAvailable());

        // Modificarea disponibilității
        ebook.setAvailable(false);
        assertFalse(ebook.isAvailable());
    }
}
