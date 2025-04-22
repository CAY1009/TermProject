package models;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true; // Default status
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }

    public void borrowBook() { this.isAvailable = false; }
    public void returnBook() { this.isAvailable = true; }

    @Override
    public String toString() {
        return id + " - " + title + " by " + author + " [" + (isAvailable ? "Available" : "Borrowed") + "]";
    }
}