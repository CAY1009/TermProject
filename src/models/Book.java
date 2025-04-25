package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book {
    private String id, title, author, genre, borrowerId;
    private boolean isAvailable;
    private LocalDate dueDate;
    public static final int maxDurationByDays = 14;

    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowerId() { return borrowerId; }
    public LocalDate getDueDate() { return dueDate; }

    public void borrowBook(String borrowerId) {
        this.isAvailable = false;
        this.borrowerId = borrowerId;
        this.dueDate = LocalDate.now().plusDays(maxDurationByDays);
    }

    public double returnBook() {
        this.isAvailable = true;
        double fine = 0;
        if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
            fine = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
        this.borrowerId = null;
        this.dueDate = null;
        return fine;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Borrowed by " + borrowerId +
                " (Due: " + (dueDate != null ? dueDate.format(DateTimeFormatter.ISO_DATE) : "N/A") + ")";
        return id + " - " + title + " by " + author + " [" + status + "]";
    }
}