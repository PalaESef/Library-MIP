package org.example;

import org.example.models.Book;
import org.example.models.EBook;
import org.example.models.PhysicalBook;
import org.example.models.User;
import org.example.interfaces.IBorrowable;
import org.example.models.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<IBorrowable> library = new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        loadData();

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a physical book");
            System.out.println("2. Add an e-book");
            System.out.println("3. Create a user");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Delete a book");
            System.out.println("7. View the library");
            System.out.println("8. View all users");  // Moved to be after "View the library"
            System.out.println("9. View available books");
            System.out.println("10. Search books by author or category");
            System.out.println("11. Update book details");
            System.out.println("12. View borrowed books by a user");
            System.out.println("13. Save data to JSON file");
            System.out.println("14. Exit");

            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline after reading a number

            switch (option) {
                case 1:
                    addPhysicalBook(scanner);
                    break;
                case 2:
                    addEBook(scanner);
                    break;
                case 3:
                    createUser(scanner);
                    break;
                case 4:
                    borrowBook(scanner);
                    break;
                case 5:
                    returnBook(scanner);
                    break;
                case 6:
                    deleteBook(scanner);
                    break;
                case 7:
                    displayLibrary();
                    break;
                case 8:  // Moved to display users after library
                    displayUsers();
                    break;
                case 9:
                    displayAvailableBooks();
                    break;
                case 10:
                    searchBooksByAuthorOrCategory(scanner);
                    break;
                case 11:
                    updateBookDetails(scanner);
                    break;
                case 12:
                    viewBorrowedBooksByUser(scanner);
                    break;
                case 13:
                    List<Book> books = new ArrayList<>();
                    for (IBorrowable borrowable : library) {
                        if (borrowable instanceof Book) {
                            books.add((Book) borrowable);  // Add the Book to the list
                        }
                    }
                    JsonUtil.saveToJson(books, users);  // Save filtered books and users data
                    System.out.println("Data saved to JSON file.");
                    break;
                case 14:
                    System.out.println("Exiting the application.");
                    // Filter library to get only Book instances
                    List<Book> exitBooks = new ArrayList<>();
                    for (IBorrowable borrowable : library) {
                        if (borrowable instanceof Book) {
                            exitBooks.add((Book) borrowable);  // Add the Book to the list
                        }
                    }
                    JsonUtil.saveToJson(exitBooks, users);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (option != 14);

        scanner.close();
    }

    private static void addPhysicalBook(Scanner scanner) {
        System.out.print("Enter the title of the physical book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author: ");
        String author = scanner.nextLine();
        System.out.print("Enter the category: ");
        String category = scanner.nextLine();
        System.out.print("Enter the shelf location: ");
        String shelfLocation = scanner.nextLine();

        PhysicalBook book = new PhysicalBook(title, author, category, shelfLocation);
        book.setType("physical");
        library.add(book);
        System.out.println("Physical book added successfully!");
    }

    private static void addEBook(Scanner scanner) {
        System.out.print("Enter the title of the e-book: ");
        String title = scanner.nextLine();
        System.out.print("Enter the author: ");
        String author = scanner.nextLine();
        System.out.print("Enter the category: ");
        String category = scanner.nextLine();
        System.out.print("Enter the download link: ");
        String downloadLink = scanner.nextLine();

        EBook ebook = new EBook(title, author, category, downloadLink);
        ebook.setType("ebook");
        library.add(ebook);
        System.out.println("E-book added successfully!");
    }

    private static void createUser(Scanner scanner) {
        System.out.print("Enter the user's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the user's ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline after reading a number

        User user = new User(name, userId);
        users.add(user);
        System.out.println("User created successfully!");
    }

    private static void borrowBook(Scanner scanner) {
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }

        // Step 1: Get user ID
        System.out.print("Enter the user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Step 2: Find the user by ID
        User user = findUserById(userId);
        if (user == null) {
            System.out.println("User with ID " + userId + " not found.");
            return;
        }

        // Step 3: Get book title to borrow
        System.out.print("Enter the title of the book you want to borrow: ");
        String title = scanner.nextLine();

        // Step 4: Find the book in the library
        Book book = findBookByTitle(title);
        if (book != null && book.isAvailable()) {
            // Step 5: Borrow the book if it's available
            user.addBorrowedBook(book);  // Add the book to the user's borrowed books
            System.out.println("The book '" + title + "' has been successfully borrowed!");
        } else {
            System.out.println("The book is not available or not found.");
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter the user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Step 1: Find user by ID
        User user = findUserById(userId);
        if (user != null) {
            System.out.print("Enter the title of the book you want to return: ");
            String title = scanner.nextLine();

            // Step 2: Find the borrowed book in the user's borrowed list
            Book book = user.findBorrowedBookByTitle(title);
            if (book != null) {
                try {
                    // Step 3: Return the book
                    user.returnBook(book);
                    // Step 4: Add the book back to the library
                    library.add(book);
                    System.out.println("The book '" + title + "' has been successfully returned to the library!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage()); // Print error message if book was not borrowed
                }
            } else {
                System.out.println("The book '" + title + "' is not borrowed by this user.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void deleteBook(Scanner scanner) {
        System.out.print("Enter the title of the book you want to delete: ");
        String title = scanner.nextLine();

        Book book = findBookByTitle(title);
        if (book != null) {
            // Remove the book from the library
            library.remove(book);
            System.out.println("The book '" + title + "' has been successfully deleted from the library.");
        } else {
            System.out.println("The book '" + title + "' was not found in the library.");
        }
    }

    private static void displayLibrary() {
        System.out.println("Library Contents:");
        if (library.isEmpty()) {
            System.out.println("The library is empty.");
        } else {
            for (IBorrowable borrowable : library) {
                if (borrowable instanceof Book) {
                    Book book = (Book) borrowable;
                    System.out.printf("Title: %s, Author: %s, Type: %s%n",
                            book.getTitle(), book.getAuthor(), book.getType());
                }
            }
        }
    }

    private static User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;  // User not found
    }

    private static Book findBookByTitle(String title) {
        for (IBorrowable borrowable : library) {
            if (borrowable instanceof Book) {
                Book book = (Book) borrowable;
                if (book.getTitle().equalsIgnoreCase(title)) {
                    return book;
                }
            }
        }
        return null;  // Book not found
    }

    private static void displayAvailableBooks() {
        System.out.println("Available Books:");
        boolean found = false;
        for (IBorrowable borrowable : library) {
            if (borrowable instanceof Book) {
                Book book = (Book) borrowable;
                if (book.isAvailable()) {
                    found = true;
                    System.out.printf("Title: %s, Author: %s, Type: %s%n",
                            book.getTitle(), book.getAuthor(), book.getType());
                }
            }
        }
        if (!found) {
            System.out.println("No available books in the library.");
        }
    }



    private static void viewBorrowedBooksByUser(Scanner scanner) {
        System.out.print("Enter the user ID to view borrowed books: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User user = findUserById(userId);
        if (user != null) {
            user.viewBorrowedBooks();
        } else {
            System.out.println("User not found.");
        }
    }

    private static void updateBookDetails(Scanner scanner)
    {
        System.out.print("Enter the title of the book to update: ");
        String title = scanner.nextLine();

        Book book = findBookByTitle(title);
        if (book != null) {
            System.out.println("Enter new details for the book:");
            System.out.print("New title (leave blank to keep unchanged): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                book.setTitle(newTitle);
            }

            System.out.print("New author (leave blank to keep unchanged): ");
            String newAuthor = scanner.nextLine();
            if (!newAuthor.isEmpty()) {
                book.setAuthor(newAuthor);
            }

            System.out.print("New category (leave blank to keep unchanged): ");
            String newCategory = scanner.nextLine();
            if (!newCategory.isEmpty()) {
                book.setCategory(newCategory);
            }

            System.out.println("Book details updated successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void searchBooksByAuthorOrCategory(Scanner scanner)
    {
        System.out.print("Enter the author or category to search: ");
        String searchTerm = scanner.nextLine();

        boolean found = false;
        for (IBorrowable borrowable : library) {
            if (borrowable instanceof Book) {
                Book book = (Book) borrowable;
                if (book.getAuthor().equalsIgnoreCase(searchTerm) || book.getCategory().equalsIgnoreCase(searchTerm)) {
                    found = true;
                    System.out.printf("Title: %s, Author: %s, Category: %s%n",
                            book.getTitle(), book.getAuthor(), book.getCategory());
                }
            }
        }
        if (!found) {
            System.out.println("No books found matching the search term.");
        }
    }

    private static void displayUsers()
    {
        System.out.println("Users List:");
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
        } else {
            for (User user : users) {
                System.out.printf("User ID: %d, Name: %s%n", user.getUserId(), user.getName());
            }
        }
    }

    private static void loadData() {
        // Load books and users from the JSON files
        List<Book> books = JsonUtil.loadBooksFromJson();
        List<User> usersList = JsonUtil.loadUsersFromJson();

        // If no books or users were loaded, initialize the lists as empty
        if (books == null) {
            books = new ArrayList<>();
        }
        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        // Convert books to the library list and users to the users list
        for (Book book : books) {
            library.add(book);
        }

        users = usersList;
    }


}
