package com.bn231.clinic.controller;

import com.bn231.clinic.model.*;
import com.bn231.clinic.service.ClinicService;
import com.bn231.clinic.view.MainFrame;
import javax.swing.*;
import java.time.*;
import java.time.format.DateTimeParseException;

public class ClinicController {
    private final ClinicService service; private MainFrame view;
    public ClinicController(ClinicService service){this.service=service;}
    public void attach(MainFrame view){this.view=view;view.refreshAll();}
    public ClinicService service(){return service;}
    public LocalDate date(String text){try{return LocalDate.parse(text.trim());}catch(DateTimeParseException e){throw new IllegalArgumentException("Date must use yyyy-MM-dd.");}}
    public LocalTime time(String text){try{return LocalTime.parse(text.trim());}catch(DateTimeParseException e){throw new IllegalArgumentException("Time must use HH:mm, for example 09:30.");}}
    public void addPatient(String...v){run(()->service.addPatient(new Patient(v[0],v[1],v[2],v[3],v[4])),"Patient registered.");}
    public void updatePatient(String...v){run(()->service.updatePatient(v[0],v[1],v[2],v[3],v[4]),"Patient updated.");}
    public void deletePatient(String id){run(()->service.deletePatient(id),"Patient deleted.");}
    public void addDoctor(String...v){run(()->service.addDoctor(new Doctor(v[0],v[1],v[2],v[3])),"Doctor registered.");}
    public void addAppointment(String...v){run(()->service.addAppointment(new Appointment(v[0],v[1],v[2],date(v[3]),time(v[4]),v[5])),"Appointment booked.");}
    public void deleteAppointment(String id){run(()->service.deleteAppointment(id),"Appointment deleted.");}
    public void addTreatment(String...v){run(()->service.addTreatment(new Treatment(v[0],v[1],date(v[2]),v[3],v[4],v[5])),"Treatment recorded.");}
    public void save(){runChecked(service::save,"Data saved.");} public void load(){runChecked(service::load,"Data loaded.");}
    private void run(Runnable action,String success){try{action.run();try{service.save();}catch(Exception ignored){}view.refreshAll();JOptionPane.showMessageDialog(view,success);}catch(Exception e){error(e);}}
    private void runChecked(Checked action,String success){try{action.run();view.refreshAll();JOptionPane.showMessageDialog(view,success);}catch(Exception e){error(e);}}
    private void error(Exception e){JOptionPane.showMessageDialog(view,e.getMessage(),"Input error",JOptionPane.ERROR_MESSAGE);}
    @FunctionalInterface private interface Checked{void run()throws Exception;}
}
