package services;

import models.Book;
import models.Borrower;
import java.util.*;

public class LibraryService {
    // In-memory list of books and borrowers
    private List<Book> books = new ArrayList<>();
    private List<Borrower> borrowers = new ArrayList<>();

    // Add book or borrower
    public void addBook(Book book) { books.add(book); }
    public void addBorrower(Borrower b) { borrowers.add(b); }

    // Return all books and borrowers
    public List<Book> listBooks() { return books; }
    public List<Borrower> listBorrowers() { return borrowers; }

      // Borrow a book by ID if available
    public void borrowBook(String bookId, String borrowerId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && b.isAvailable()) {
                b.borrowBook(borrowerId);
                return;
            }
        }
        System.out.println("Book is not available or does not exist.");
    }

    // Return a book
    public void returnBook(String bookId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && !b.isAvailable()) {
                b.setAvailable(true);
                b.setBorrowerId(null);
                b.setDueDate(null);
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book with ID " + bookId + " is either not borrowed or doesn't exist.");
    }

    // Search books by: ID, title, author, or genre
    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
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

    // Remove book by ID
    public void removeBook(String bookId) {
        if (books.removeIf(book -> book.getId().equals(bookId))) {
            System.out.println("Success");
        } else {
            System.out.println("Sorry, try again.");
        }
    }

//    // Find book by ID (utility method)
//    public Book findBookById(String bookId) {
//        for (Book b : books) {
//            if (b.getId().equals(bookId)) {
//                return b;
//            }
//        }
//        return null;
//    }

//    edit book
    public void editBook(){

    }
}
