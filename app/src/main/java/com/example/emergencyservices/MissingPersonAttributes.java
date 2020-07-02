package com.example.emergencyservices;

public class MissingPersonAttributes {
    String Name;
    String fatherName;
    String Age;
    String Contact;
    String Address;
    String Relation;
    String imgurl;
    String id;
    String uid;

    public MissingPersonAttributes() {
    }


    public MissingPersonAttributes(String name, String fatherName, String age, String contact, String address, String relation, String imgurl, String id, String uid) {
        Name = name;
        this.fatherName = fatherName;
        Age = age;
        Contact = contact;
        Address = address;
        Relation = relation;
        this.imgurl = imgurl;
        this.id = id;
        this.uid = uid;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
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

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
