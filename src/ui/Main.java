package ui;
import models.Book;
import models.Borrower;
import services.FileHandler;
import services.LibraryService;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static final String BOOKS_FILE = "books.txt";
    private static final String BORROWERS_FILE = "borrowers.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService library = new LibraryService();

        try {
            library.listBooks().addAll(FileHandler.loadBooks(BOOKS_FILE));
            library.listBorrowers().addAll(FileHandler.loadBorrowers(BORROWERS_FILE));
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n=== Library Management ===");
            System.out.println("1. Add Book");
            System.out.println("2. List Books");
            System.out.println("3. Add Borrower");
            System.out.println("4. List Borrowers");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Save & Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Book ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    library.addBook(new Book(id, title, author, genre));
                    break;

                case 2:
                    for (Book b : library.listBooks()) {
                        System.out.println(b);
                    }
                    break;

                case 3:
                    System.out.print("Borrower ID: ");
                    String bid = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Contact: ");
                    String contact = scanner.nextLine();
                    library.addBorrower(new Borrower(bid, name, contact));
                    break;

                case 4:
                    for (Borrower b : library.listBorrowers()) {
                        System.out.println(b);
                    }
                    break;

                case 5:
                    System.out.print("Book ID to borrow: ");
                    String borrowId = scanner.nextLine();
                    if (library.borrowBook(borrowId)) {
                        System.out.println("Book borrowed.");
                    } else {
                        System.out.println("Book not available.");
                    }
                    break;

                case 6:
                    System.out.print("Book ID to return: ");
                    String returnId = scanner.nextLine();
                    if (library.returnBook(returnId)) {
                        System.out.println("Book returned.");
                    } else {
                        System.out.println("Book not found or not borrowed.");
                    }
                    break;

                case 7:
                    try {
                        FileHandler.saveBooks(library.listBooks(), BOOKS_FILE);
                        FileHandler.saveBorrowers(library.listBorrowers(), BORROWERS_FILE);
                        System.out.println("Data saved. Goodbye!");
                        return;
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}