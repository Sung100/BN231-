package com.bn231.clinic.service;

import com.bn231.clinic.model.*;
import com.bn231.clinic.repository.ClinicRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ClinicService {
    private Clinic clinic;
    private final ClinicRepository repository;
    public ClinicService(Clinic clinic,ClinicRepository repository){this.clinic=clinic;this.repository=repository;}
    public Clinic getClinic(){return clinic;}
    public void addPatient(Patient p){if(findPatient(p.getId()).isPresent())throw new IllegalArgumentException("Patient ID already exists.");clinic.getPatients().add(p);}
    public void addDoctor(Doctor d){if(findDoctor(d.getId()).isPresent())throw new IllegalArgumentException("Doctor ID already exists.");clinic.getDoctors().add(d);}
    public void addAppointment(Appointment a){
        if(findAppointment(a.getId()).isPresent())throw new IllegalArgumentException("Appointment ID already exists.");
        if(findPatient(a.getPatientId()).isEmpty())throw new IllegalArgumentException("Patient ID does not exist.");
        if(findDoctor(a.getDoctorId()).isEmpty())throw new IllegalArgumentException("Doctor ID does not exist.");
        boolean clash=clinic.getAppointments().stream().anyMatch(x->x.getDoctorId().equalsIgnoreCase(a.getDoctorId())&&x.getDate().equals(a.getDate())&&x.getTime().equals(a.getTime()));
        if(clash)throw new IllegalArgumentException("Doctor already has an appointment at that time.");
        clinic.getAppointments().add(a);
    }
    public void addTreatment(Treatment t){if(clinic.getTreatments().stream().anyMatch(x->x.getId().equalsIgnoreCase(t.getId())))throw new IllegalArgumentException("Treatment ID already exists.");if(findAppointment(t.getAppointmentId()).isEmpty())throw new IllegalArgumentException("Appointment ID does not exist.");clinic.getTreatments().add(t);}
    // Explicit linear-search algorithm required by Task 8.
    public Optional<Patient> findPatient(String id){for(Patient p:clinic.getPatients())if(p.getId().equalsIgnoreCase(id.trim()))return Optional.of(p);return Optional.empty();}
    public Optional<Doctor> findDoctor(String id){for(Doctor d:clinic.getDoctors())if(d.getId().equalsIgnoreCase(id.trim()))return Optional.of(d);return Optional.empty();}
    public Optional<Appointment> findAppointment(String id){for(Appointment a:clinic.getAppointments())if(a.getId().equalsIgnoreCase(id.trim()))return Optional.of(a);return Optional.empty();}
    // Explicit insertion-sort algorithm by date then time required by Task 8.
    public List<Appointment> sortedAppointments(){
        List<Appointment> result=new ArrayList<>(clinic.getAppointments());
        for(int i=1;i<result.size();i++){Appointment key=result.get(i);int j=i-1;while(j>=0&&compare(result.get(j),key)>0){result.set(j+1,result.get(j));j--;}result.set(j+1,key);}return result;
    }
    private int compare(Appointment a,Appointment b){int c=a.getDate().compareTo(b.getDate());return c!=0?c:a.getTime().compareTo(b.getTime());}
    public List<Appointment> doctorSchedule(String doctorId){List<Appointment> r=new ArrayList<>();for(Appointment a:sortedAppointments())if(a.getDoctorId().equalsIgnoreCase(doctorId))r.add(a);return r;}
    public void updatePatient(String id,String name,String dob,String phone,String address){Patient p=findPatient(id).orElseThrow(()->new IllegalArgumentException("Patient not found."));p.setName(name);p.setDateOfBirth(dob);p.setPhone(phone);p.setAddress(address);}
    public void deletePatient(String id){if(clinic.getAppointments().stream().anyMatch(a->a.getPatientId().equalsIgnoreCase(id)))throw new IllegalStateException("Delete the patient's appointments first.");if(!clinic.getPatients().removeIf(p->p.getId().equalsIgnoreCase(id)))throw new IllegalArgumentException("Patient not found.");}
    public void deleteAppointment(String id){clinic.getTreatments().removeIf(t->t.getAppointmentId().equalsIgnoreCase(id));if(!clinic.getAppointments().removeIf(a->a.getId().equalsIgnoreCase(id)))throw new IllegalArgumentException("Appointment not found.");}
    public void save() throws IOException{repository.save(clinic);} public void load() throws IOException,ClassNotFoundException{clinic=repository.load();}
    public long appointmentsOn(LocalDate date){return clinic.getAppointments().stream().filter(a->a.getDate().equals(date)).count();}
}
