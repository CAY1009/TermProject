package services;

import models.Book;
import models.Borrower;
import services.FileHandler;

import java.io.IOException;
import java.util.*;

public class LibraryService {
    // In-memory list of books and borrowers
    private List<Book> books = new ArrayList<>();
    private List<Borrower> borrowers = new ArrayList<>();
    private static final String BOOKS_FILE = "books.txt";
    private static final String BORROWERS_FILE = "borrowers.txt";

    //gives the user the ability to return to main menu if accidental selection
    public boolean askToReturnToMainMenu(Scanner scanner) {
        System.out.print("Enter ! to go back to the main menu, or press any key to continue: ");
        String input = scanner.nextLine();
        return input.equals("!");
        // Returns true if the user wants to go back to the main menu
    }

    // Add book to list
    public void addBook(Book book) {
        this.books.add(book);

        // persist book in books.txt
        try {
            FileHandler.saveBooks(this.books, BOOKS_FILE);
            System.out.println("Success.");
        } catch (IOException ioException) {
            System.out.println("Book is not saved successfully. Please try again.");
        }
    }

    //Add borrower to list
    public void addBorrower(Borrower b) {
        this.borrowers.add(b);
        // persist borrowers in borrowers.txt
        try {
            FileHandler.saveBorrowers(this.borrowers, BORROWERS_FILE);
            System.out.println("Success.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    //list the books and the borrowers from database
    public List<Book> listBooks() {
        return this.books;
    }

    public List<Borrower> listBorrowers() {
        try  {
            return this.borrowers = FileHandler.loadBorrowers(BORROWERS_FILE);
        } catch (IOException ioException){
            System.out.println(ioException.getMessage());
            return null;
        }
    }

    // Borrow a book by ID if available
    public void borrowBook(String bookId, String borrowerId) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            this.borrowers = FileHandler.loadBorrowers(BORROWERS_FILE);
            boolean foundBook = false;
            for (Book b : this.books) {
//                book to borrow is found
                if (b.getId().equals(bookId) && b.isAvailable()) {
                    b.checkOutBook(borrowerId);
                    foundBook = true;
                    break;
                }
            }
            if(!foundBook){
                System.out.println("Book is not available or does not exist.");
            }

            FileHandler.saveBooks(this.books, BOOKS_FILE);
            System.out.println("The book has been checked out successfully.");
        } catch (IOException ioException) {
            System.out.println("Book is not saved successfully. Please try again.");
        }
    }

    // Return a book and reset availability status
    public void returnBook(String bookId) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            this.borrowers = FileHandler.loadBorrowers(BORROWERS_FILE);
            for (Book b : this.books) {
                if (b.getId().equals(bookId) && !b.isAvailable()) {
                    b.setAvailable(true);
                    b.setBorrowerId(null);
                    b.setDueDate(null);
                    System.out.println("Book returned successfully.");
                    FileHandler.saveBooks(this.books, BOOKS_FILE);
                    break;
                }
            }
        } catch (IOException ioException) {
            System.out.println("Book with ID " + bookId + " is either not borrowed or doesn't exist.");
        }
    }

    // Before Borrowing - Search books by: ID, title, author, or genre
    public List<Book> searchBooks(String keyword) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
        } catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
        List<Book> result = new ArrayList<>();
        for (Book b : this.books) {
            if (b.getId().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }
        if (result.isEmpty()) {
            System.out.println("There is no book found.");
        }
        return result;
    }

    // Sort books by Genre or Availability
    public void sortBooks(Scanner scanner) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
        } catch (IOException ioException) {
            System.out.println("Book is not saved successfully. Please try again.");
        }
        System.out.println("View Books:");
        System.out.println("1. Show All by Original Order");
        System.out.println("2. Sort by Genre");
        System.out.println("3. Sort by Availability");

        System.out.print("Choose option: ");
        int viewChoice = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        System.out.println("ID  | Title | Author | Genre | Availability");
        List<Book> booksToDisplay = new ArrayList<>(this.books); // copy to avoid modifying the original list

        switch (viewChoice) {
            case 1:
                // No sorting needed
                break;
            case 2:
                booksToDisplay.sort(Comparator.comparing(Book::getGenre));
                break;
            case 3:
                booksToDisplay.sort(Comparator.comparing(Book::isAvailable).reversed()); // Available first
                break;
            default:
                System.out.println("Invalid option. Showing all books.");
        }

        booksToDisplay.forEach(book -> {
            String availability = book.isAvailable() ? "Available" : "Borrowed";
            System.out.println(book);
        });
    }

    // Remove book by Book ID
    public void removeBook(String bookId) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            if (this.books.removeIf(book -> book.getId().equals(bookId))) {
                System.out.println("Success");
            } else {
                System.out.println("Sorry, try again.");
            }
            FileHandler.saveBooks(this.books, BOOKS_FILE);
        } catch (IOException ioException) {
            System.out.println("Book is not saved successfully. Please try again.");
        }
    }

    //    edit book
    public void editBook(Scanner scanner) {
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            System.out.print("Enter Book ID to edit: ");
            String editId = scanner.nextLine();
            Book bookToEdit = null;

            for (Book b : this.books) {
                if (b.getId().equals(editId)) {
                    bookToEdit = b;
                    break;
                }
            }

            if (bookToEdit == null) {
                System.out.println("Book not found.");
                return;
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
            FileHandler.saveBooks(this.books, BOOKS_FILE);
            System.out.println("Success.");
        } catch (IOException ioException) {
            System.out.println("Book is not saved successfully. Please try again.");
        }

    }

    //Edit Borrower
    public void editBorrower(Scanner scanner) {
        // persist borrowers in borrowers.txt
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            System.out.print("Enter Borrower ID to edit: ");
            String editId = scanner.nextLine();
            Borrower borrowerToEdit = null;

            for (Borrower b : this.borrowers) {
                if (b.getId().equals(editId)) {
                    borrowerToEdit = b;
                    break;
                }
            }

            if (borrowerToEdit == null) {
                System.out.println("Borrower not found.");
                return;
            }

            System.out.println("Editing Borrower: " + borrowerToEdit);
            System.out.print("New Name (leave blank to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.isBlank()) {
                borrowerToEdit.setName(newName);
            }
            System.out.print("New email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isBlank()) {
                borrowerToEdit.setEmail(newEmail);
            }
            System.out.print("New Phone (leave blank to keep current): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isBlank()) {
                borrowerToEdit.setPhone(newPhone);
            }
            FileHandler.saveBorrowers(this.borrowers, BORROWERS_FILE);
            System.out.println("Success.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }

    }

    //Delete Borrower
    public void deleteBorrower(Scanner scanner) {
        // persist borrowers in borrowers.txt
        try {
            this.books = FileHandler.loadBooks(BOOKS_FILE);
            System.out.print("Enter Borrower ID to delete: ");
            String borrowerId = scanner.nextLine();
            Borrower borrowerToDelete = null;

            for (Borrower b : this.borrowers) {
                if (b.getId().equals(borrowerId)) {
                    borrowerToDelete = b;
                    break;
                }
            }

            if (borrowerToDelete == null) {
                System.out.println("Borrower not found.");
                return;
            }

            this.borrowers.remove(borrowerToDelete);
            System.out.println("Borrower deleted successfully.");
            FileHandler.saveBorrowers(this.borrowers, BORROWERS_FILE);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Exit and save data
    public void exitProgram() {
        System.out.println("Exited the program successfully.");
    }

}
