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

        // Load books and borrowers from files
        try {
            library.listBooks().addAll(FileHandler.loadBooks(BOOKS_FILE));
            library.listBorrowers().addAll(FileHandler.loadBorrowers(BORROWERS_FILE));
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        // Main menu loop - Menu options
        while (true) {
            System.out.println("\n=== Library Management ===");
            System.out.println("1. Add Book");
            System.out.println("2. List Books");
            System.out.println("3. Add Borrower");
            System.out.println("4. List Borrowers");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Delete Book");
            System.out.println("8. Edit Book");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:    // Add new book
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

                case 2:  // List and sort books by genre then title
                    List<Book> books = library.listBooks();
                    books.sort(Comparator.comparing(Book::getGenre).thenComparing(Book::getTitle));

                    System.out.println("Books by Genre:");
                    for (Book book : books) {
                        System.out.println("Genre: " + book.getGenre() + " | " + book);
                    }

                    try {
                        FileHandler.saveBooks(books, BOOKS_FILE);
                        System.out.println("Data saved.");
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    break;

                case 3:// Add new borrower and save
                    System.out.print("Borrower ID: ");
                    String bid = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String contact = scanner.nextLine();
                    library.addBorrower(new Borrower(bid, name, contact));
                    try {
                        FileHandler.saveBorrowers(library.listBorrowers(), BORROWERS_FILE);
                        System.out.println("Data saved.");
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    break;

                case 4: // Display all borrowers
                    library.listBorrowers().forEach(System.out::println);
                    break;

                case 5:// Search books and borrow if available
                    System.out.print("Search books by Book ID, title, author, or genre: ");
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
                    library.borrowBook(borrowId, borrowerId);
                    break;

                case 6:  // Return book
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scanner.nextLine();
                    library.returnBook(returnId);
                    break;

                case 7:   // Remove book
                    System.out.print("Enter Book ID to remove: ");
                    String removeId = scanner.nextLine();
                    library.removeBook(removeId);
                    break;

                case 8: // Edit existing book
                    System.out.print("Enter Book ID to edit: ");
                    String editId = scanner.nextLine();
                    Book bookToEdit = null;

                    for (Book b : library.listBooks()) {
                        if (b.getId().equals(editId)) {
                            bookToEdit = b;
                            break;
                        }
                    }

                    if (bookToEdit == null) {
                        System.out.println("Book not found.");
                        break;
                    }

                    System.out.println("Editing Book: " + bookToEdit);
                    System.out.print("New Title (leave blank to keep current): ");
                    String newTitle = scanner.nextLine();
                    if (!newTitle.isBlank()) {
                        bookToEdit.setTitleNew(newTitle);
                    }

                    System.out.print("New Author (leave blank to keep current): ");
                    String newAuthor = scanner.nextLine();
                    if (!newAuthor.isBlank()) {
                        bookToEdit.setAuthorNew(newAuthor);
                    }

                    System.out.print("New Genre (leave blank to keep current): ");
                    String newGenre = scanner.nextLine();
                    if (!newGenre.isBlank()) {
                        bookToEdit.setGenreNew(newGenre);
                    }

                    try {
                        FileHandler.saveBooks(library.listBooks(), BOOKS_FILE);
                        System.out.println("Changes saved successfully.");
                    } catch (Exception e) {
                        System.out.println("Error saving changes: " + e.getMessage());
                    }

                    break;

                case 9:// Exit and save data
                    try {
                        FileHandler.saveBooks(library.listBooks(), BOOKS_FILE);
                        FileHandler.saveBorrowers(library.listBorrowers(), BORROWERS_FILE);
                        System.out.println("Data saved. Goodbye!");
                    } catch (Exception e) {
                        System.out.println("Error saving data: " + e.getMessage());
                    }
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
