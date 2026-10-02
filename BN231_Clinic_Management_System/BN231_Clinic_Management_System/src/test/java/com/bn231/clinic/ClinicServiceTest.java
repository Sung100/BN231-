package com.bn231.clinic;

import com.bn231.clinic.model.*;
import com.bn231.clinic.repository.ClinicRepository;
import com.bn231.clinic.service.ClinicService;
import org.junit.jupiter.api.*;
import java.nio.file.Path;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;

class ClinicServiceTest {
    ClinicService service;
    @BeforeEach void setup(){service=new ClinicService(new Clinic("Test"),new ClinicRepository(Path.of("target","test-clinic.dat")));service.addPatient(new Patient("P001","Alex Lee","1990-01-01","0400000000","Melbourne"));service.addDoctor(new Doctor("D001","Sam Smith","GP","0399999999"));}
    @Test void linearSearchFindsPatientById(){assertEquals("Alex Lee",service.findPatient("p001").orElseThrow().getName());}
    @Test void duplicatePatientIsRejected(){assertThrows(IllegalArgumentException.class,()->service.addPatient(new Patient("P001","Other","2000-01-01","1","X")));}
    @Test void insertionSortOrdersAppointmentsByDateAndTime(){service.addAppointment(new Appointment("A2","P001","D001",LocalDate.of(2026,10,2),LocalTime.of(10,0),"Review"));service.addAppointment(new Appointment("A1","P001","D001",LocalDate.of(2026,10,1),LocalTime.of(9,0),"Check"));assertEquals("A1",service.sortedAppointments().get(0).getId());}
    @Test void unknownPatientCannotBookAppointment(){assertThrows(IllegalArgumentException.class,()->service.addAppointment(new Appointment("A9","P999","D001",LocalDate.now(),LocalTime.NOON,"Test")));}
    @Test void saveAndLoadPreservesRecords() throws Exception {service.save();service.load();assertTrue(service.findPatient("P001").isPresent());}
}
