package com.bn231.clinic.model;

import java.io.Serializable;

public class Patient implements Serializable {
    private final String id;
    private String name;
    private String dateOfBirth;
    private String phone;
    private String address;

    public Patient(String id, String name, String dateOfBirth, String phone, String address) {
        this.id = require(id, "Patient ID");
        this.name = require(name, "Patient name");
        this.dateOfBirth = require(dateOfBirth, "Date of birth");
        this.phone = require(phone, "Phone");
        this.address = require(address, "Address");
    }
    private static String require(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required.");
        return value.trim();
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = require(name, "Patient name"); }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = require(dateOfBirth, "Date of birth"); }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = require(phone, "Phone"); }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = require(address, "Address"); }
    @Override public String toString() { return id + " - " + name; }
}
