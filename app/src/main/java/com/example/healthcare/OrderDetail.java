package com.example.healthcare;

public class OrderDetail {
    private String name;
    private String address;
    private String contact;
    private String pincode;
    private String date;
    private String time;
    private String price;

    public OrderDetail() {
        // Default constructor required for calls to DataSnapshot.getValue(OrderDetail.class)
    }

    public OrderDetail(String name, String address, String contact, String pincode, String date, String time, String price) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.pincode = pincode;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
