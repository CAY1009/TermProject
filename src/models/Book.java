package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book {
    // Book fields
    private String id, title, author, genre, borrowerId;
    private boolean isAvailable;
    private LocalDate dueDate;
    public static final int MAX_DURATION_BY_DAYS = 14;

    // late fee as 2 dollars per day past the due date
    public static final int DOLLARS_PER_DAY = 2;

    // Constructor to create a book
    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }
    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowerId() { return borrowerId; }
    public LocalDate getDueDate() { return dueDate; }

    // Borrow a book - updates availability, borrowerId and dueDate
    public void checkOutBook(String borrowerId) {
        this.isAvailable = false;
        this.borrowerId = borrowerId;
        this.dueDate = LocalDate.now().plusDays(MAX_DURATION_BY_DAYS);
    }


    // Setters for internal updates
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    // Edit the title, author, and genre
    public void setTitleNew(String newTitle) {
        this.title = newTitle;
    }
    public void setAuthorNew(String newAuthor) {
        this.author = newAuthor;
    }
    public void setGenreNew(String newGenre) {
        this.genre = newGenre;
    }

    // Display book info, including borrow status and due date
    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Borrowed by " + borrowerId +
                " (Due: " + (dueDate != null ? dueDate.format(DateTimeFormatter.ISO_DATE) : "N/A") + ")";
        return id + " | " + title + " | " + author + " | " + genre + " | " + status;
    }
}