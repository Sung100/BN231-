package com.bn231.clinic.repository;

import com.bn231.clinic.model.Clinic;
import java.io.*;
import java.nio.file.*;

public class ClinicRepository {
    private final Path file;
    public ClinicRepository(Path file){this.file=file;}
    public void save(Clinic clinic) throws IOException {
        if(file.getParent()!=null) Files.createDirectories(file.getParent());
        try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(file))){out.writeObject(clinic);}
    }
    public Clinic load() throws IOException, ClassNotFoundException {
        if(!Files.exists(file)) return new Clinic("Community Health Clinic");
        try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(file))){return (Clinic)in.readObject();}
    }
}
