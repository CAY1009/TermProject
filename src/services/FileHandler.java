package services;

import models.Book;
import models.Borrower;
import java.io.*;
import java.util.*;

public class FileHandler {
    public static void saveBooks(List<Book> books, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book b : books) {
                writer.write(b.getId() + "," + b.getTitle() + "," + b.getAuthor() + "," +
                        b.getGenre() + "," + b.isAvailable());
                writer.newLine();
            }
        }
    }

    public static List<Book> loadBooks(String filename) throws IOException {
        List<Book> books = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    Book b = new Book(parts[0], parts[1], parts[2], parts[3]);
                    if (!Boolean.parseBoolean(parts[4])) b.borrowBook();
                    books.add(b);
                }
            }
        }
        return books;
    }

    public static void saveBorrowers(List<Borrower> borrowers, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Borrower b : borrowers) {
                writer.write(b.getId() + "," + b.getName() + "," + b.getContact());
                writer.newLine();
            }
        }
    }

    public static List<Borrower> loadBorrowers(String filename) throws IOException {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return borrowers;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    borrowers.add(new Borrower(parts[0], parts[1], parts[2]));
                }
            }
        }
        return borrowers;
    }
}