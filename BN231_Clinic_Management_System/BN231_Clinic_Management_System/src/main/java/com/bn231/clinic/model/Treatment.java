package com.bn231.clinic.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Treatment implements Serializable {
    private final String id, appointmentId, diagnosis, medication, notes;
    private final LocalDate date;
    public Treatment(String id,String appointmentId,LocalDate date,String diagnosis,String medication,String notes){
        if(id==null||id.isBlank()||appointmentId==null||appointmentId.isBlank()||date==null||diagnosis==null||diagnosis.isBlank()) throw new IllegalArgumentException("Treatment ID, appointment, date and diagnosis are required.");
        this.id=id.trim();this.appointmentId=appointmentId.trim();this.date=date;this.diagnosis=diagnosis.trim();this.medication=medication==null?"":medication.trim();this.notes=notes==null?"":notes.trim();
    }
    public String getId(){return id;} public String getAppointmentId(){return appointmentId;} public LocalDate getDate(){return date;} public String getDiagnosis(){return diagnosis;} public String getMedication(){return medication;} public String getNotes(){return notes;}
    @Override public String toString(){return id+" | Appointment "+appointmentId+" | "+date+" | "+diagnosis;}
}
