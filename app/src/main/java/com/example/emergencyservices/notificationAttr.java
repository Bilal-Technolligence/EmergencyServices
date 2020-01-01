package com.example.emergencyservices;

public class notificationAttr {
    String id;
    String message;
    String lat;
    String lon;
    String address;
    String name;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public notificationAttr(String id, String message, String lat, String lon, String address, String name, String status) {
        this.id = id;
        this.message = message;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.name = name;
        this.status = status;
    }

    public notificationAttr() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
