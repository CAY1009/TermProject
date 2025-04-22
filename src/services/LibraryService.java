package services;
import models.Book;
import models.Borrower;
import java.util.*;

public class LibraryService {
    private List<Book> books = new ArrayList<>();
    private List<Borrower> borrowers = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void editBook(String id, String title, String author, String genre) {
        for (Book b : books) {
            if (b.getId().equals(id)) {
                b.setTitle(title);
                b.setAuthor(author);
                b.setGenre(genre);
                break;
            }
        }
    }

    public void deleteBook(String id) {
        books.removeIf(b -> b.getId().equals(id));
    }

    public List<Book> listBooks() {
        return books;
    }

    public void addBorrower(Borrower b) {
        borrowers.add(b);
    }

    public void updateBorrower(String id, String name, String contact) {
        for (Borrower b : borrowers) {
            if (b.getId().equals(id)) {
                b.setName(name);
                b.setContact(contact);
                break;
            }
        }
    }

    public void deleteBorrower(String id) {
        borrowers.removeIf(b -> b.getId().equals(id));
    }

    public boolean borrowBook(String bookId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && b.isAvailable()) {
                b.borrowBook();
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String bookId) {
        for (Book b : books) {
            if (b.getId().equals(bookId) && !b.isAvailable()) {
                b.returnBook();
                return true;
            }
        }
        return false;
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

    public List<Book> filterByAvailability(boolean isAvailable) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.isAvailable() == isAvailable) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Borrower> listBorrowers() {
        return borrowers;
    }
}