package com.bn231.clinic;

import com.bn231.clinic.controller.ClinicController;
import com.bn231.clinic.model.Clinic;
import com.bn231.clinic.repository.ClinicRepository;
import com.bn231.clinic.service.ClinicService;
import com.bn231.clinic.view.MainFrame;
import javax.swing.*;
import java.nio.file.Path;

public class ClinicApplication {
    public static void main(String[] args){SwingUtilities.invokeLater(()->{try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){}ClinicRepository repo=new ClinicRepository(Path.of("data","clinic.dat"));Clinic clinic;try{clinic=repo.load();}catch(Exception e){clinic=new Clinic("Community Health Clinic");}ClinicController controller=new ClinicController(new ClinicService(clinic,repo));MainFrame frame=new MainFrame(controller);controller.attach(frame);frame.setVisible(true);});}
}
