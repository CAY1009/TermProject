package ui;

import models.Book;
import models.Borrower;

import services.LibraryService;

import java.util.*;

public class Main {

    private static final String BORROWERS_FILE = "borrowers.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService libraryService = new LibraryService();


        // Main menu loop - Menu options
        while (true) {
            System.out.println("\n===Main Menu===");
            System.out.println("1. List Books");
            System.out.println("2. Add book");
            System.out.println("3. Edit Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");

            System.out.println("7. List Borrowers");
            System.out.println("8. Register Borrower");
            System.out.println("9. Update Borrower");
            System.out.println("10. Delete Borrower");
            System.out.println("11. Display Due Date and Late Fee");
            System.out.println("12. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                //List and Sort Books
                case 1:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        // Return to the main menu
                        continue;
                    }

                    // display books based on sort option
                    libraryService.sortBooks(scanner);
                    break;

                // Add new book
                case 2:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Book ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    libraryService.addBook(new Book(id, title, author, genre));
                    break;

                    // Edit existing book
                case 3:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    libraryService.editBook(scanner);
                    break;

                // Remove book
                case 4:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Enter Book ID to remove: ");
                    String removeId = scanner.nextLine();
                    libraryService.removeBook(removeId);
                    break;

                // Search books and borrow if available
                case 5:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Search books by Book ID, title, author, or genre: ");
                    String keyword = scanner.nextLine();
                    List<Book> matches = libraryService.searchBooks(keyword).stream().filter(Book::isAvailable).toList();
                    matches.forEach(System.out::println);
                    if (matches.isEmpty()) {
                        System.out.println("No available books found.");
                        break;
                    }
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Enter Book ID to borrow: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter Borrower ID: ");
                    String borrowerId = scanner.nextLine();
                    libraryService.borrowBook(bookId, borrowerId);
                    break;

                // Return book
                case 6:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scanner.nextLine();
                    libraryService.returnBook(returnId);
                    break;

                // Display all borrowers
                case 7:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.println("Borrower ID | Name | Phone | Email ");
                    libraryService.listBorrowers().forEach(System.out::println);
                    break;

                // Add new borrower and save
                case 8:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    System.out.print("Borrower ID: ");
                    String bid = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    libraryService.addBorrower(new Borrower(bid, name, email, phone));
                    break;

                //Edit Borrower Information
                case 9:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    libraryService.editBorrower(scanner);
                    break;

                //Delete Borrower
                case 10:
                    if (libraryService.askToReturnToMainMenu(scanner)) {
                        continue;
                    }
                    libraryService.deleteBorrower(scanner);
                    break;

                // Display due date and late fee
                case 11:
                    if(libraryService.askToReturnToMainMenu(scanner)){
                        continue;
                    }
                    libraryService.displayDueDateAndLateFee(scanner);
                    break;

                // Exit and save data
                case 12:
                    libraryService.exitProgram();
                    return;

                default:
                    System.out.println("Invalid option.");

            }
        }
    }
}
