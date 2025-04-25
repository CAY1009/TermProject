package ui;

import models.Book;
import models.Borrower;
import services.FileHandler;
import services.LibraryService;

import java.util.*;

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
            System.out.println("8. Remove Book");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    List<Book> sortedBooks = new ArrayList<>(library.listBooks());
                    sortedBooks.sort(Comparator.comparing(Book::getGenre).thenComparing(Book::getTitle));
                    System.out.println("Books by Genre:");
                    for (Book book : sortedBooks) {
                        System.out.println("Genre: " + book.getGenre() + " | " + book);
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
                    library.listBorrowers().forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Search by keyword: ");
                    String keyword = scanner.nextLine();
                    List<Book> matches = library.searchBooks(keyword).stream().filter(Book::isAvailable).toList();
                    if (matches.isEmpty()) {
                        System.out.println("No available books found.");
                        break;
                    }
                    matches.forEach(System.out::println);
                    System.out.print("Enter Book ID to borrow: ");
                    String borrowId = scanner.nextLine();
                    System.out.print("Enter Borrower ID: ");
                    String borrowerId = scanner.nextLine();
                    if (library.borrowBook(borrowId, borrowerId)) {
                        System.out.println("Book borrowed. Due in " + Book.maxDurationByDays + " days.");
                    } else {
                        System.out.println("Book not available or ID incorrect.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scanner.nextLine();
                    double fee = library.returnBook(returnId);
                    if (fee >= 0) {
                        System.out.println("Book returned." + (fee > 0 ? " Late fee: $" + fee : ""));
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
                case 8:
                    System.out.print("Enter Book ID to remove: ");
                    String removeId = scanner.nextLine();
                    if (library.removeBook(removeId)) {
                        System.out.println("Book removed successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}