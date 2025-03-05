package com.tuban.midterm.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String firstName;
    private String lastName;
    private List<String> email;
    private List<String> phoneNumber;
    private String resourceName;

    public Contact() {}

    public Contact(String firstName, String lastName, List<String> email, List<String> phoneNumber, String resourceName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.resourceName = resourceName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public List<String> getEmail() { return email != null ? email : new ArrayList<>(); }
    public void setEmail(List<String> email) { this.email = email; }
    
    public List<String> getPhoneNumber() { return phoneNumber != null ? phoneNumber : new ArrayList<>(); }
    public void setPhoneNumber(List<String> phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
}
