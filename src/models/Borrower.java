package models;

public class Borrower {
    // Fields
    private String id;
    private String name;
    private String contact;

    // Constructor
    public Borrower(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }

    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }

    // Display borrower info
    @Override
    public String toString() {
        return id + " - " + name + " (" + contact + ")";
    }
}