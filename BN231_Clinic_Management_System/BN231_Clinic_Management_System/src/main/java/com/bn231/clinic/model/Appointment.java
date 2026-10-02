package com.bn231.clinic.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Serializable {
    private final String id, patientId, doctorId;
    private LocalDate date;
    private LocalTime time;
    private String reason, status;
    public Appointment(String id,String patientId,String doctorId,LocalDate date,LocalTime time,String reason){
        if(id==null||id.isBlank()||patientId==null||patientId.isBlank()||doctorId==null||doctorId.isBlank()||date==null||time==null) throw new IllegalArgumentException("All appointment fields except reason are required.");
        this.id=id.trim();this.patientId=patientId.trim();this.doctorId=doctorId.trim();this.date=date;this.time=time;this.reason=reason==null?"":reason.trim();this.status="Scheduled";
    }
    public String getId(){return id;} public String getPatientId(){return patientId;} public String getDoctorId(){return doctorId;} public LocalDate getDate(){return date;} public LocalTime getTime(){return time;} public String getReason(){return reason;} public String getStatus(){return status;}
    public void setDate(LocalDate date){this.date=date;} public void setTime(LocalTime time){this.time=time;} public void setReason(String reason){this.reason=reason;} public void setStatus(String status){this.status=status;}
    @Override public String toString(){return id+" | "+date+" "+time+" | Patient "+patientId+" | Doctor "+doctorId+" | "+status;}
}
