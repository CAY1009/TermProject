package models;

public class Borrower {
    // Fields
    private String id;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public Borrower(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone(){ return phone;}

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email;}
    public void setPhone(String phone) { this.phone = phone;}

    // Display borrower info
    @Override
    public String toString() {
        return id + " | " + name + " | " + email + " | " + phone;
    }

}