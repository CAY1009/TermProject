package services;

import models.Book;
import models.Borrower;
import java.util.*;

public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<Borrower> borrowers = new ArrayList<>();

    public void addBook(Book book) { books.add(book); }
    public void addBorrower(Borrower b) { borrowers.add(b); }
    public List<Book> listBooks() { return books; }
    public List<Borrower> listBorrowers() { return borrowers; }

    public boolean borrowBook(String bookId, String borrowerId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && b.isAvailable()) {
                b.borrowBook(borrowerId);
                return true;
            }
        }
        return false;
    }

    public double returnBook(String bookId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && !b.isAvailable()) {
                return b.returnBook();
            }
        }
        return -1;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    b.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }
    public boolean removeBook(String bookId) {
        return books.removeIf(book -> book.getId().equals(bookId));
    }
}