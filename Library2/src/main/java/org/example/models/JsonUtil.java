package org.example.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;

public class JsonUtil {

    private static final String BOOKS_FILE_PATH = "library_data.json"; // Path to books JSON file
    private static final String USERS_FILE_PATH = "users_data.json"; // Path to users JSON file

    // Load books from JSON file
    public static List<Book> loadBooksFromJson() {
        try (FileReader reader = new FileReader(BOOKS_FILE_PATH)) {
            Gson gson = new Gson();
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            return gson.fromJson(reader, bookListType); // Deserialize JSON to a list of Book objects
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list if error occurs (file missing or empty)
        }
    }

    // Load users from JSON file
    public static List<User> loadUsersFromJson() {
        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>(){}.getType();
            return gson.fromJson(reader, userListType); // Deserialize JSON to a list of User objects
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list if error occurs (file missing or empty)
        }
    }

    // Save books and users to JSON files with pretty-printing
    public static void saveToJson(List<Book> books, List<User> users) {
        try (FileWriter bookWriter = new FileWriter(BOOKS_FILE_PATH);
             FileWriter userWriter = new FileWriter(USERS_FILE_PATH)) {

            // Create a Gson instance with pretty printing enabled
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Serialize books and users to JSON files with pretty-printing
            gson.toJson(books, bookWriter);
            gson.toJson(users, userWriter);

            System.out.println("Data saved to JSON with pretty-printing.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
