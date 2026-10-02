package com.bn231.clinic.view;

import com.bn231.clinic.controller.ClinicController;
import com.bn231.clinic.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final ClinicController c; private final CardLayout cards=new CardLayout(); private final JPanel content=new JPanel(cards);
    private final JTextArea patientsArea=area(), appointmentsArea=area(), reportsArea=area();
    public MainFrame(ClinicController c){super("Community Health Clinic Management System");this.c=c;setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1000,680);setLocationRelativeTo(null);setLayout(new BorderLayout());
        JPanel nav=new JPanel(new GridLayout(0,1,6,6));nav.setBorder(new EmptyBorder(12,12,12,12));
        addPage(nav,"Main Menu",home());addPage(nav,"Register Patient",patientPanel());addPage(nav,"Register Doctor",doctorPanel());addPage(nav,"Book Appointment",appointmentPanel());addPage(nav,"Treatment Entry",treatmentPanel());addPage(nav,"Search and Manage",searchPanel());addPage(nav,"Reports",reportsPanel());
        JButton save=new JButton("Save Data");save.addActionListener(e->c.save());nav.add(save);JButton load=new JButton("Load Data");load.addActionListener(e->c.load());nav.add(load);add(nav,BorderLayout.WEST);add(content,BorderLayout.CENTER);
    }
    private void addPage(JPanel nav,String name,JPanel page){content.add(page,name);JButton b=new JButton(name);b.addActionListener(e->cards.show(content,name));nav.add(b);}
    private JPanel home(){JPanel p=panel("Community Health Clinic");JLabel l=new JLabel("<html><h2>Clinic Management Dashboard</h2><p>Use the menu to register patients and doctors, book appointments, record treatments, search records and produce schedules.</p></html>");p.add(l,BorderLayout.NORTH);return p;}
    private JPanel patientPanel(){return form("Register Patient",new String[]{"Patient ID","Full name","Date of birth (yyyy-MM-dd)","Phone","Address"},v->c.addPatient(v));}
    private JPanel doctorPanel(){return form("Register Doctor",new String[]{"Doctor ID","Full name","Specialty","Phone"},v->c.addDoctor(v));}
    private JPanel appointmentPanel(){return form("Book Appointment",new String[]{"Appointment ID","Patient ID","Doctor ID","Date (yyyy-MM-dd)","Time (HH:mm)","Reason"},v->c.addAppointment(v));}
    private JPanel treatmentPanel(){return form("Record Treatment",new String[]{"Treatment ID","Appointment ID","Date (yyyy-MM-dd)","Diagnosis","Medication","Notes"},v->c.addTreatment(v));}
    private JPanel form(String title,String[] labels,Submit submit){JPanel outer=panel(title);JPanel grid=new JPanel(new GridBagLayout());GridBagConstraints g=new GridBagConstraints();g.insets=new Insets(7,7,7,7);g.fill=GridBagConstraints.HORIZONTAL;JTextField[] f=new JTextField[labels.length];for(int i=0;i<labels.length;i++){g.gridx=0;g.gridy=i;g.weightx=0;grid.add(new JLabel(labels[i]),g);g.gridx=1;g.weightx=1;f[i]=new JTextField(25);grid.add(f[i],g);}JButton b=new JButton(title);b.addActionListener(e->{String[]v=new String[f.length];for(int i=0;i<f.length;i++)v[i]=f[i].getText();submit.go(v);});g.gridx=1;g.gridy=labels.length;g.weightx=0;grid.add(b,g);outer.add(grid,BorderLayout.NORTH);return outer;}
    private JPanel searchPanel(){JPanel p=panel("Search and Manage");JPanel top=new JPanel();JTextField id=new JTextField(10);top.add(new JLabel("Patient ID"));top.add(id);JButton search=new JButton("Linear Search");search.addActionListener(e->{Patient x=c.service().findPatient(id.getText()).orElse(null);patientsArea.setText(x==null?"Patient not found.":"ID: "+x.getId()+"\nName: "+x.getName()+"\nDOB: "+x.getDateOfBirth()+"\nPhone: "+x.getPhone()+"\nAddress: "+x.getAddress());});top.add(search);JButton update=new JButton("Update from fields");update.addActionListener(e->{JTextField n=new JTextField(),d=new JTextField(),ph=new JTextField(),a=new JTextField();Object[] fields={"Name",n,"DOB",d,"Phone",ph,"Address",a};if(JOptionPane.showConfirmDialog(this,fields,"Update patient",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION)c.updatePatient(id.getText(),n.getText(),d.getText(),ph.getText(),a.getText());});top.add(update);JButton del=new JButton("Delete Patient");del.addActionListener(e->c.deletePatient(id.getText()));top.add(del);p.add(top,BorderLayout.NORTH);p.add(new JScrollPane(patientsArea),BorderLayout.CENTER);return p;}
    private JPanel reportsPanel(){JPanel p=panel("Reports");JPanel top=new JPanel();JTextField did=new JTextField(8),aid=new JTextField(8);JButton schedule=new JButton("Doctor Schedule");schedule.addActionListener(e->showAppointments(c.service().doctorSchedule(did.getText())));JButton all=new JButton("Appointments by Date");all.addActionListener(e->showAppointments(c.service().sortedAppointments()));JButton del=new JButton("Delete Appointment");del.addActionListener(e->c.deleteAppointment(aid.getText()));top.add(new JLabel("Doctor ID"));top.add(did);top.add(schedule);top.add(all);top.add(new JLabel("Appointment ID"));top.add(aid);top.add(del);p.add(top,BorderLayout.NORTH);p.add(new JScrollPane(reportsArea),BorderLayout.CENTER);return p;}
    private void showAppointments(List<Appointment> list){StringBuilder s=new StringBuilder();for(Appointment a:list)s.append(a).append('\n');reportsArea.setText(s.length()==0?"No appointments found.":s.toString());}
    public void refreshAll(){StringBuilder p=new StringBuilder();for(Patient x:c.service().getClinic().getPatients())p.append(x).append('\n');patientsArea.setText(p.toString());showAppointments(c.service().sortedAppointments());}
    private static JPanel panel(String title){JPanel p=new JPanel(new BorderLayout(10,10));p.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(14,14,14,14),BorderFactory.createTitledBorder(title)));return p;}
    private static JTextArea area(){JTextArea a=new JTextArea();a.setEditable(false);a.setFont(new Font(Font.MONOSPACED,Font.PLAIN,13));return a;}
    @FunctionalInterface private interface Submit{void go(String[] values);}
}
