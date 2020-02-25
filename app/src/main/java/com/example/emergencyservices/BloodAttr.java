package com.example.emergencyservices;

public class BloodAttr {
    String name;
    String address;
    String age;
    String bloodgroup;
    String email;
    String id;
    String phone;
    String user;
    String imgurl;

    public BloodAttr() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BloodAttr(String name, String address, String age, String bloodgroup, String email, String id, String phone, String user, String imgurl) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.bloodgroup = bloodgroup;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.user = user;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
