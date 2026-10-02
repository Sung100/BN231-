package com.bn231.clinic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Clinic implements Serializable {
    private final String name;
    private final List<Patient> patients=new ArrayList<>();
    private final List<Doctor> doctors=new ArrayList<>();
    private final List<Appointment> appointments=new ArrayList<>();
    private final List<Treatment> treatments=new ArrayList<>();
    public Clinic(String name){this.name=name;}
    public String getName(){return name;} public List<Patient> getPatients(){return patients;} public List<Doctor> getDoctors(){return doctors;} public List<Appointment> getAppointments(){return appointments;} public List<Treatment> getTreatments(){return treatments;}
}
