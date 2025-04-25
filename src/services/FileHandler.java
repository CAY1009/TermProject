package services;

import models.Book;
import models.Borrower;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileHandler {

    public static List<Book> loadBooks(String filename) throws IOException {
        List<Book> books = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                Book book = new Book(parts[0], parts[1], parts[2], parts[3]);
                if (parts.length >= 5) {
                    book.setAvailable(Boolean.parseBoolean(parts[4]));
                    if (!book.isAvailable() && parts.length >= 7) {
                        book.setBorrowerId(parts[5]);
                        book.setDueDate(LocalDate.parse(parts[6]));
                    }
                }
                books.add(book);
            }
        }
        reader.close();
        return books;
    }

    public static void saveBooks(List<Book> books, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Book b : books) {
            writer.write(String.join(",",
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getGenre(),
                    String.valueOf(b.isAvailable()),
                    b.getBorrowerId() != null ? b.getBorrowerId() : "",
                    b.getDueDate() != null ? b.getDueDate().toString() : ""
            ));
            writer.newLine();
        }
        writer.close();
    }

    public static List<Borrower> loadBorrowers(String filename) throws IOException {
        List<Borrower> borrowers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                borrowers.add(new Borrower(parts[0], parts[1], parts[2]));
            }
        }

        reader.close();
        return borrowers;
    }

    public static void saveBorrowers(List<Borrower> borrowers, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Borrower b : borrowers) {
            writer.write(String.join(",", b.getId(), b.getName(), b.getContact()));
            writer.newLine();
        }
        writer.close();
    }
}