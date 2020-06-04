package com.fas.smash_k.ui.models.chatItems;

public class User {
    private String dateOfBirth;
    private String email;

    /* renamed from: id */
    private int f8id;
    private String imagePath;
    private String lastName;
    private String name;
    private String password;
    private String phone;

    public User() {
    }

    public User(int id, String name2, String lastName2) {
        this.f8id = id;
        this.name = name2;
        this.lastName = lastName2;
    }

    public User(int id, String imagePath2, String name2, String lastName2, String dateOfBirth2, String email2, String phone2, String password2) {
        this.f8id = id;
        this.imagePath = imagePath2;
        this.name = name2;
        this.lastName = lastName2;
        this.dateOfBirth = dateOfBirth2;
        this.email = email2;
        this.phone = phone2;
        this.password = password2;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath2) {
        this.imagePath = imagePath2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth2) {
        this.dateOfBirth = dateOfBirth2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone2) {
        this.phone = phone2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public int getId() {
        return this.f8id;
    }
}
