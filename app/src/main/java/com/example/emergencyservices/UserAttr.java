package com.example.emergencyservices;

public class UserAttr {
    String Id;
    String Name;
    String Email;
    String Contact;
    String Age;
    String Address;
    String ImageUrl;
    String Category;
    int Status;

    public UserAttr() {
    }

    public UserAttr(String id, String name, String email, String contact, String age, String address, String imageUrl, String category, int status) {
        Id = id;
        Name = name;
        Email = email;
        Contact = contact;
        Age = age;
        Address = address;
        ImageUrl = imageUrl;
        Category = category;
        Status = status;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
