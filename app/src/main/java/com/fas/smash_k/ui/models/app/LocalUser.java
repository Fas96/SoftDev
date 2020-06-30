package com.fas.smash_k.ui.models.app;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(indexes = {@Index(value = "image_url,image_mime_type,base64Image,fname,lname,birth_date,email,contact,password DESC", unique = true)})
public class LocalUser {
    @Id
    private Long id;

    private String image_url;
    private String image_mime_type;
    private String base64Image;
    private String fname;
    private String lname;
    private String birth_date;
    private String email;
    private String contact;
    private String password;

    public LocalUser(){

    }
    public LocalUser(String image_url, String fname, String lname, String birth_date, String email, String contact, String password) {
        this.image_url = image_url;
        this.fname = fname;
        this.lname = lname;
        this.birth_date = birth_date;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }
    @Generated(hash = 1841842280)
    public LocalUser(Long id, String image_url, String image_mime_type, String base64Image, String fname, String lname,
                     String birth_date, String email, String contact, String password) {
        this.id = id;
        this.image_url = image_url;
        this.image_mime_type = image_mime_type;
        this.base64Image = base64Image;
        this.fname = fname;
        this.lname = lname;
        this.birth_date = birth_date;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setImageMimeType(String imageMimeType) {
        this.image_mime_type = imageMimeType;
    }
    public String getImage_mime_type() {
        return this.image_mime_type;
    }
    public void setImage_mime_type(String image_mime_type) {
        this.image_mime_type = image_mime_type;
    }
    public String getBase64Image() {
        return this.base64Image;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
