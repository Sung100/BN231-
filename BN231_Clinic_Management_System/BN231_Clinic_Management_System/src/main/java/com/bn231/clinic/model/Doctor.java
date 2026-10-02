package com.bn231.clinic.model;

import java.io.Serializable;

public class Doctor implements Serializable {
    private final String id;
    private String name;
    private String specialty;
    private String phone;
    public Doctor(String id, String name, String specialty, String phone) {
        if (id == null || id.isBlank() || name == null || name.isBlank() || specialty == null || specialty.isBlank()) throw new IllegalArgumentException("Doctor ID, name and specialty are required.");
        this.id=id.trim(); this.name=name.trim(); this.specialty=specialty.trim(); this.phone=phone == null ? "" : phone.trim();
    }
    public String getId(){return id;} public String getName(){return name;} public String getSpecialty(){return specialty;} public String getPhone(){return phone;}
    public void setName(String value){if(value==null||value.isBlank())throw new IllegalArgumentException("Name is required.");name=value.trim();}
    public void setSpecialty(String value){if(value==null||value.isBlank())throw new IllegalArgumentException("Specialty is required.");specialty=value.trim();}
    public void setPhone(String value){phone=value==null?"":value.trim();}
    @Override public String toString(){return id+" - Dr " + name + " ("+specialty+")";}
}
