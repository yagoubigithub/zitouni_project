/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import zitouni_project.Object.Patient;

public class Affectation_kine extends javax.swing.JFrame {

    private Db db;
    private String mode_travail;
    private ArrayList<Patient> patients;
    private int id_kine;

    ArrayList<JComboBox> combos = new ArrayList<>();

    public Affectation_kine() {
        initComponents();

        db = new Db();
        patients = new ArrayList<>();
        getAllPatients();

        addItems();

    }

    public void addItems() {
        combos.add(d_c_1);
        combos.add(d_c_2);
        combos.add(d_c_3);
        combos.add(d_c_4);
        combos.add(l_c_1);
        combos.add(l_c_2);
        combos.add(l_c_3);
        combos.add(l_c_4);
        combos.add(ma_c_1);
        combos.add(ma_c_2);
        combos.add(ma_c_3);
        combos.add(ma_c_4);
        combos.add(me_c_1);
        combos.add(me_c_2);
        combos.add(me_c_3);
        combos.add(me_c_4);
        combos.add(j_c_1);
        combos.add(j_c_2);
        combos.add(j_c_3);
        combos.add(j_c_4);
        for (int i = 0; i < combos.size(); i++) {
            combos.get(i).removeAllItems();
            for (int j = 0; j < patients.size(); j++) {
                Patient patient = patients.get(j);

                combos.get(i).addItem(patient.getId() + "-" + patient.getNom() + " " + patient.getPrenom());
                /*
                 */
            }

        }
    }

    public void setModeTravail(String mode_travail) {
        if (mode_travail.contains("j")) {
            this.mode_travail = "jour";
        }
        if (mode_travail.contains("n")) {
            this.mode_travail = "nuit";
        }

        addTime();
        addJour();
        addItemListenr();
    }

    public void setIdKine(int id_kine) {
        this.id_kine = id_kine;
    }

    public ArrayList<Patient> getPatientInKine() {
        ArrayList<Patient> patients = new ArrayList<>();
        ResultSet res = db.getAllPatientInKine();
        try {

            while (res.next()) {
                patients.add(new Patient(res.getInt("id_patient"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("diagno"), res.getInt("nmbr_seance"),
                        res.getString("nom_medecin"),
                        res.getString("prenom_medecin"), res.getString("date_visit")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patients;
    }

    public void addTime() {
        if (this.mode_travail.equals("jour")) {
            time1.setText("8:00-10:00");
            time2.setText("10:00-12:00");
            time3.setText("13:00-15:00");
            time4.setText("15:00-17:00");
        } else if (this.mode_travail.equals("nuit")) {
            time1.setText("17:00-19:00");
            time2.setText("19:00-21:00");
            time3.setText("21:00-23:00");
            time4.setText("23:00-00:00");
        }
    }

    public void addJour() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime j1 = now.getDayOfWeek() == DayOfWeek.THURSDAY ? now.plusDays(3) : now.plusDays(1);
        LocalDateTime j2 = j1.getDayOfWeek() == DayOfWeek.THURSDAY ? j1.plusDays(3) : j1.plusDays(1);
        LocalDateTime j3 = j2.getDayOfWeek() == DayOfWeek.THURSDAY ? j2.plusDays(3) : j2.plusDays(1);
        LocalDateTime j4 = j3.getDayOfWeek() == DayOfWeek.THURSDAY ? j3.plusDays(3) : j3.plusDays(1);
        LocalDateTime j5 = j4.getDayOfWeek() == DayOfWeek.THURSDAY ? j4.plusDays(3) : j4.plusDays(1);

        //System.out.println(dtf.format(j1)); //2016/11/16 12:08:43
        j1_t.setText(dtf.format(j1));
        j2_t.setText(dtf.format(j2));
        j3_t.setText(dtf.format(j3));
        j4_t.setText(dtf.format(j4));
        j5_t.setText(dtf.format(j5));

        //jour 1 
        Patient patient_in_seance1_j1 = db.getPatientParDateSuiviAndTime(dtf.format(j1), time1.getText().toString().split("-")[0]);
        Patient patient_in_seance2_j1 = db.getPatientParDateSuiviAndTime(dtf.format(j1), time2.getText().toString().split("-")[0]);
        Patient patient_in_seance3_j1 = db.getPatientParDateSuiviAndTime(dtf.format(j1), time3.getText().toString().split("-")[0]);
        Patient patient_in_seance4_j1 = db.getPatientParDateSuiviAndTime(dtf.format(j1), time4.getText().toString().split("-")[0]);

        l_d_t_1.setText(patient_in_seance1_j1 != null ? patient_in_seance1_j1.getNom() + " " + patient_in_seance1_j1.getPrenom() : "");
        l_d_t_2.setText(patient_in_seance2_j1 != null ? patient_in_seance2_j1.getNom() + " " + patient_in_seance2_j1.getPrenom() : "");
        l_d_t_3.setText(patient_in_seance3_j1 != null ? patient_in_seance3_j1.getNom() + " " + patient_in_seance3_j1.getPrenom() : "");
        l_d_t_4.setText(patient_in_seance4_j1 != null ? patient_in_seance4_j1.getNom() + " " + patient_in_seance4_j1.getPrenom() : "");

        //jour 2
        Patient patient_in_seance1_j2 = db.getPatientParDateSuiviAndTime(dtf.format(j2), time1.getText().toString().split("-")[0]);
        Patient patient_in_seance2_j2 = db.getPatientParDateSuiviAndTime(dtf.format(j2), time2.getText().toString().split("-")[0]);
        Patient patient_in_seance3_j2 = db.getPatientParDateSuiviAndTime(dtf.format(j2), time3.getText().toString().split("-")[0]);
        Patient patient_in_seance4_j2 = db.getPatientParDateSuiviAndTime(dtf.format(j2), time4.getText().toString().split("-")[0]);

        l_l_t_1.setText(patient_in_seance1_j2 != null ? patient_in_seance1_j2.getNom() + " " + patient_in_seance1_j2.getPrenom() : "");
        l_l_t_2.setText(patient_in_seance2_j2 != null ? patient_in_seance2_j2.getNom() + " " + patient_in_seance2_j2.getPrenom() : "");
        l_l_t_3.setText(patient_in_seance3_j2 != null ? patient_in_seance3_j2.getNom() + " " + patient_in_seance3_j2.getPrenom() : "");
        l_l_t_4.setText(patient_in_seance4_j2 != null ? patient_in_seance4_j2.getNom() + " " + patient_in_seance4_j2.getPrenom() : "");

        //jour 3
        Patient patient_in_seance1_j3 = db.getPatientParDateSuiviAndTime(dtf.format(j3), time1.getText().toString().split("-")[0]);
        Patient patient_in_seance2_j3 = db.getPatientParDateSuiviAndTime(dtf.format(j3), time2.getText().toString().split("-")[0]);
        Patient patient_in_seance3_j3 = db.getPatientParDateSuiviAndTime(dtf.format(j3), time3.getText().toString().split("-")[0]);
        Patient patient_in_seance4_j3 = db.getPatientParDateSuiviAndTime(dtf.format(j3), time4.getText().toString().split("-")[0]);

        l_ma_t_1.setText(patient_in_seance1_j3 != null ? patient_in_seance1_j3.getNom() + " " + patient_in_seance1_j3.getPrenom() : "");
        l_ma_t_2.setText(patient_in_seance2_j3 != null ? patient_in_seance2_j3.getNom() + " " + patient_in_seance2_j3.getPrenom() : "");
        l_ma_t_3.setText(patient_in_seance3_j3 != null ? patient_in_seance3_j3.getNom() + " " + patient_in_seance3_j3.getPrenom() : "");
        l_ma_t_4.setText(patient_in_seance4_j3 != null ? patient_in_seance4_j3.getNom() + " " + patient_in_seance4_j3.getPrenom() : "");

        //jour 4
        Patient patient_in_seance1_j4 = db.getPatientParDateSuiviAndTime(dtf.format(j4), time1.getText().toString().split("-")[0]);
        Patient patient_in_seance2_j4 = db.getPatientParDateSuiviAndTime(dtf.format(j4), time2.getText().toString().split("-")[0]);
        Patient patient_in_seance3_j4 = db.getPatientParDateSuiviAndTime(dtf.format(j4), time3.getText().toString().split("-")[0]);
        Patient patient_in_seance4_j4 = db.getPatientParDateSuiviAndTime(dtf.format(j4), time4.getText().toString().split("-")[0]);

        l_me_t_1.setText(patient_in_seance1_j4 != null ? patient_in_seance1_j4.getNom() + " " + patient_in_seance1_j4.getPrenom() : "");
        l_me_t_2.setText(patient_in_seance2_j4 != null ? patient_in_seance2_j4.getNom() + " " + patient_in_seance2_j4.getPrenom() : "");
        l_me_t_3.setText(patient_in_seance3_j4 != null ? patient_in_seance3_j4.getNom() + " " + patient_in_seance3_j4.getPrenom() : "");
        l_me_t_4.setText(patient_in_seance4_j4 != null ? patient_in_seance4_j4.getNom() + " " + patient_in_seance4_j4.getPrenom() : "");

        //jour 5
        Patient patient_in_seance1_j5 = db.getPatientParDateSuiviAndTime(dtf.format(j5), time1.getText().toString().split("-")[0]);
        Patient patient_in_seance2_j5 = db.getPatientParDateSuiviAndTime(dtf.format(j5), time2.getText().toString().split("-")[0]);
        Patient patient_in_seance3_j5 = db.getPatientParDateSuiviAndTime(dtf.format(j5), time3.getText().toString().split("-")[0]);
        Patient patient_in_seance4_j5 = db.getPatientParDateSuiviAndTime(dtf.format(j5), time4.getText().toString().split("-")[0]);

        l_j_t_1.setText(patient_in_seance1_j5 != null ? patient_in_seance1_j5.getNom() + " " + patient_in_seance1_j5.getPrenom() : "");
        l_j_t_2.setText(patient_in_seance2_j5 != null ? patient_in_seance2_j5.getNom() + " " + patient_in_seance2_j5.getPrenom() : "");
        l_j_t_3.setText(patient_in_seance3_j5 != null ? patient_in_seance3_j5.getNom() + " " + patient_in_seance3_j5.getPrenom() : "");
        l_j_t_4.setText(patient_in_seance4_j5 != null ? patient_in_seance4_j5.getNom() + " " + patient_in_seance4_j5.getPrenom() : "");

    }

    public void addItemListenr() {
        /**************************************************************************************/
        //jour1
        
        d_c_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(d_c_1.getSelectedItem().toString().split("-")[0]);

                    String nom = d_c_1.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = d_c_1.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j1_t.getText().toString();
                    String heure = time1.getText().toString().split("-")[0];
                    if (l_d_t_1.getText().equals("")) {
                        //create
                        int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_d_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_d_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        d_c_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(d_c_2.getSelectedItem().toString().split("-")[0]);

                    String nom = d_c_2.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = d_c_2.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j1_t.getText().toString();
                    String heure = time2.getText().toString().split("-")[0];
                    if (l_d_t_2.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_d_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_d_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        d_c_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(d_c_3.getSelectedItem().toString().split("-")[0]);

                    String nom = d_c_3.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = d_c_3.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j1_t.getText().toString();
                    String heure = time3.getText().toString().split("-")[0];
                   if (l_d_t_3.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_d_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_d_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        d_c_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(d_c_4.getSelectedItem().toString().split("-")[0]);

                    String nom = d_c_4.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = d_c_4.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j1_t.getText().toString();
                    String heure = time4.getText().toString().split("-")[0];
                    if (l_d_t_4.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_d_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_d_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        
        /******************************************************************************************************/
        
        
         /**************************************************************************************/
        //jour2
        
        l_c_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(l_c_1.getSelectedItem().toString().split("-")[0]);

                    String nom = l_c_1.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = l_c_1.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j2_t.getText().toString();
                    String heure = time1.getText().toString().split("-")[0];
                    if (l_l_t_1.getText().equals("")) {
                        //create
                        int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_l_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_l_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        l_c_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(l_c_2.getSelectedItem().toString().split("-")[0]);

                    String nom = l_c_2.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = l_c_2.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j2_t.getText().toString();
                    String heure = time2.getText().toString().split("-")[0];
                    if (l_l_t_2.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_l_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_l_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        l_c_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(l_c_3.getSelectedItem().toString().split("-")[0]);

                    String nom = l_c_3.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = l_c_3.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j2_t.getText().toString();
                    String heure = time3.getText().toString().split("-")[0];
                   if (l_l_t_3.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_l_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_l_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        l_c_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(l_c_4.getSelectedItem().toString().split("-")[0]);

                    String nom = l_c_4.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = l_c_4.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j2_t.getText().toString();
                    String heure = time4.getText().toString().split("-")[0];
                    if (l_l_t_4.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_l_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_l_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        
        /******************************************************************************************************/
        
        
        
         /**************************************************************************************/
        //jour3
        
        ma_c_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(ma_c_1.getSelectedItem().toString().split("-")[0]);

                    String nom = ma_c_1.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = ma_c_1.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j3_t.getText().toString();
                    String heure = time1.getText().toString().split("-")[0];
                    if (l_ma_t_1.getText().equals("")) {
                        //create
                        int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_ma_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_ma_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        ma_c_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(ma_c_2.getSelectedItem().toString().split("-")[0]);

                    String nom = ma_c_2.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = ma_c_2.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j3_t.getText().toString();
                    String heure = time2.getText().toString().split("-")[0];
                    if (l_ma_t_2.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_ma_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_ma_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        ma_c_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(ma_c_3.getSelectedItem().toString().split("-")[0]);

                    String nom = ma_c_3.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = ma_c_3.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j3_t.getText().toString();
                    String heure = time3.getText().toString().split("-")[0];
                   if (l_ma_t_3.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_ma_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_ma_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        ma_c_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(ma_c_4.getSelectedItem().toString().split("-")[0]);

                    String nom = ma_c_4.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = ma_c_4.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j3_t.getText().toString();
                    String heure = time4.getText().toString().split("-")[0];
                    if (l_ma_t_4.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_ma_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_ma_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        
        /******************************************************************************************************/
        
        
        
        
        
        
         /**************************************************************************************/
        //jour4
        
        me_c_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(me_c_1.getSelectedItem().toString().split("-")[0]);

                    String nom = me_c_1.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = me_c_1.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j4_t.getText().toString();
                    String heure = time1.getText().toString().split("-")[0];
                    if (l_me_t_1.getText().equals("")) {
                        //create
                        int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_me_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_me_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        me_c_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(me_c_2.getSelectedItem().toString().split("-")[0]);

                    String nom = me_c_2.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = me_c_2.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j4_t.getText().toString();
                    String heure = time2.getText().toString().split("-")[0];
                    if (l_me_t_2.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_me_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_me_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        me_c_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(me_c_3.getSelectedItem().toString().split("-")[0]);

                    String nom = me_c_3.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = me_c_3.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j4_t.getText().toString();
                    String heure = time3.getText().toString().split("-")[0];
                   if (l_me_t_3.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_me_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_me_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        me_c_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(me_c_4.getSelectedItem().toString().split("-")[0]);

                    String nom = me_c_4.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = me_c_4.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j4_t.getText().toString();
                    String heure = time4.getText().toString().split("-")[0];
                    if (l_me_t_4.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_me_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_me_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        
        /******************************************************************************************************/
        
        
        
         /**************************************************************************************/
        //jour5
        
       j_c_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(j_c_1.getSelectedItem().toString().split("-")[0]);

                    String nom = j_c_1.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = j_c_1.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j5_t.getText().toString();
                    String heure = time1.getText().toString().split("-")[0];
                    if (l_j_t_1.getText().equals("")) {
                        //create
                        int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_j_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_j_t_1.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        j_c_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(j_c_2.getSelectedItem().toString().split("-")[0]);

                    String nom = j_c_2.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = j_c_2.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j5_t.getText().toString();
                    String heure = time2.getText().toString().split("-")[0];
                    if (l_j_t_2.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_j_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_j_t_2.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        j_c_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(j_c_3.getSelectedItem().toString().split("-")[0]);

                    String nom = j_c_3.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = j_c_3.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j5_t.getText().toString();
                    String heure = time3.getText().toString().split("-")[0];
                   if (l_j_t_3.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_j_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_j_t_3.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }
                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        j_c_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_patient = Integer.parseInt(j_c_4.getSelectedItem().toString().split("-")[0]);

                    String nom = j_c_4.getSelectedItem().toString().split("-")[1].split(" ")[0];

                    String prenom = j_c_4.getSelectedItem().toString().split("-")[1].split(" ")[1];
                    String date = j5_t.getText().toString();
                    String heure = time4.getText().toString().split("-")[0];
                    if (l_j_t_4.getText().equals("")) {
                        //create
                         int id_unite = db.getIdUniteFromName("kinésithérapie");
                         int id_medecin = db.getIdMedecinFromIdPatient(id_patient);
                     boolean isCreated = db.createSuivi(id_unite,id_patient,date,heure,id_medecin,id_kine);
                        if (isCreated) {
                            l_j_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    } else {
                        //update
                        boolean isUpdate = db.updateSuiviParDateAndTime(date, heure, id_patient);
                        if (isUpdate) {
                            l_j_t_4.setText(id_patient + "-" + nom + " " + prenom);
                        }
                    }

                } catch (Exception ex) {

                    System.out.println(ex.getMessage());
                }
            }
        });
        
        /******************************************************************************************************/
    }

    public void getAllPatients() {
        ResultSet res = db.getAllPatientInKine();

        try {
            patients.clear();

            while (res.next()) {
                patients.add(new Patient(res.getInt("id_patient"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("diagno"), res.getInt("nmbr_seance"),
                        res.getString("nom_medecin"),
                        res.getString("prenom_medecin"), res.getString("date_visit")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        j1_t = new javax.swing.JLabel();
        j2_t = new javax.swing.JLabel();
        j3_t = new javax.swing.JLabel();
        j4_t = new javax.swing.JLabel();
        j5_t = new javax.swing.JLabel();
        d_c_1 = new javax.swing.JComboBox<>();
        d_c_2 = new javax.swing.JComboBox<>();
        d_c_3 = new javax.swing.JComboBox<>();
        d_c_4 = new javax.swing.JComboBox<>();
        time1 = new javax.swing.JLabel();
        time2 = new javax.swing.JLabel();
        time3 = new javax.swing.JLabel();
        time4 = new javax.swing.JLabel();
        l_c_1 = new javax.swing.JComboBox<>();
        l_c_2 = new javax.swing.JComboBox<>();
        l_c_3 = new javax.swing.JComboBox<>();
        l_c_4 = new javax.swing.JComboBox<>();
        ma_c_1 = new javax.swing.JComboBox<>();
        ma_c_2 = new javax.swing.JComboBox<>();
        ma_c_3 = new javax.swing.JComboBox<>();
        ma_c_4 = new javax.swing.JComboBox<>();
        me_c_1 = new javax.swing.JComboBox<>();
        me_c_2 = new javax.swing.JComboBox<>();
        me_c_3 = new javax.swing.JComboBox<>();
        me_c_4 = new javax.swing.JComboBox<>();
        j_c_1 = new javax.swing.JComboBox<>();
        j_c_2 = new javax.swing.JComboBox<>();
        j_c_3 = new javax.swing.JComboBox<>();
        j_c_4 = new javax.swing.JComboBox<>();
        kButton1 = new keeptoo.KButton();
        l_d_t_1 = new javax.swing.JLabel();
        l_d_t_2 = new javax.swing.JLabel();
        l_d_t_3 = new javax.swing.JLabel();
        l_d_t_4 = new javax.swing.JLabel();
        l_l_t_1 = new javax.swing.JLabel();
        l_l_t_2 = new javax.swing.JLabel();
        l_l_t_3 = new javax.swing.JLabel();
        l_l_t_4 = new javax.swing.JLabel();
        l_ma_t_1 = new javax.swing.JLabel();
        l_ma_t_2 = new javax.swing.JLabel();
        l_ma_t_3 = new javax.swing.JLabel();
        l_ma_t_4 = new javax.swing.JLabel();
        l_me_t_1 = new javax.swing.JLabel();
        l_me_t_2 = new javax.swing.JLabel();
        l_me_t_3 = new javax.swing.JLabel();
        l_me_t_4 = new javax.swing.JLabel();
        l_j_t_1 = new javax.swing.JLabel();
        l_j_t_4 = new javax.swing.JLabel();
        l_j_t_3 = new javax.swing.JLabel();
        l_j_t_2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        j1_t.setText("dimanche ");

        j2_t.setText("lundi");

        j3_t.setText("mardi ");

        j4_t.setText("mercredi ");

        j5_t.setText("jeudi ");

        d_c_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        d_c_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d_c_1ActionPerformed(evt);
            }
        });

        d_c_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        d_c_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        d_c_4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        time1.setText("jLabel6");

        time2.setText("jLabel7");

        time3.setText("jLabel8");

        time4.setText("jLabel9");

        l_c_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        l_c_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        l_c_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        l_c_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                l_c_3ActionPerformed(evt);
            }
        });

        l_c_4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ma_c_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ma_c_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ma_c_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ma_c_4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        me_c_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        me_c_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        me_c_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        me_c_4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        j_c_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        j_c_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        j_c_3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        j_c_4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        kButton1.setText("Enregesterie");

        l_d_t_1.setText("jLabel10");

        l_d_t_2.setText("jLabel11");

        l_d_t_3.setText("jLabel12");

        l_d_t_4.setText("jLabel13");

        l_l_t_1.setText("jLabel14");

        l_l_t_2.setText("jLabel15");

        l_l_t_3.setText("jLabel16");

        l_l_t_4.setText("jLabel17");

        l_ma_t_1.setText("jLabel18");

        l_ma_t_2.setText("jLabel19");

        l_ma_t_3.setText("jLabel20");

        l_ma_t_4.setText("jLabel21");

        l_me_t_1.setText("jLabel22");

        l_me_t_2.setText("jLabel23");

        l_me_t_3.setText("jLabel24");

        l_me_t_4.setText("jLabel25");

        l_j_t_1.setText("jLabel26");

        l_j_t_4.setText("jLabel27");

        l_j_t_3.setText("jLabel28");

        l_j_t_2.setText("jLabel29");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j1_t)
                    .addComponent(j2_t)
                    .addComponent(j3_t)
                    .addComponent(j4_t)
                    .addComponent(j5_t))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(d_c_1, 0, 132, Short.MAX_VALUE)
                                .addComponent(time1)
                                .addComponent(l_c_1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ma_c_1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(me_c_1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(j_c_1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(l_d_t_1)
                            .addComponent(l_l_t_1)
                            .addComponent(l_ma_t_1)
                            .addComponent(l_me_t_1)
                            .addComponent(l_j_t_1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l_j_t_2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(d_c_2, 0, 130, Short.MAX_VALUE)
                                .addComponent(time2)
                                .addComponent(l_c_2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ma_c_2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(me_c_2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(j_c_2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(l_d_t_2)
                            .addComponent(l_l_t_2)
                            .addComponent(l_ma_t_2)
                            .addComponent(l_me_t_2))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(l_j_t_3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(d_c_3, 0, 131, Short.MAX_VALUE)
                                .addComponent(time3)
                                .addComponent(l_c_3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ma_c_3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(me_c_3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(j_c_3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(l_d_t_3)
                            .addComponent(l_l_t_3)
                            .addComponent(l_ma_t_3)
                            .addComponent(l_me_t_3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(d_c_4, 0, 129, Short.MAX_VALUE)
                            .addComponent(l_c_4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ma_c_4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(me_c_4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(j_c_4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(l_j_t_4)
                                    .addComponent(l_me_t_4)
                                    .addComponent(l_ma_t_4)
                                    .addComponent(l_l_t_4)
                                    .addComponent(l_d_t_4)
                                    .addComponent(time4))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time1)
                    .addComponent(time2)
                    .addComponent(time3)
                    .addComponent(time4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j1_t)
                    .addComponent(d_c_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d_c_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d_c_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(d_c_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_d_t_1)
                    .addComponent(l_d_t_2)
                    .addComponent(l_d_t_3)
                    .addComponent(l_d_t_4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j2_t, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(l_c_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(l_c_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(l_c_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(l_c_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_l_t_1)
                    .addComponent(l_l_t_2)
                    .addComponent(l_l_t_3)
                    .addComponent(l_l_t_4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j3_t)
                    .addComponent(ma_c_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ma_c_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ma_c_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ma_c_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_ma_t_1)
                    .addComponent(l_ma_t_2)
                    .addComponent(l_ma_t_3)
                    .addComponent(l_ma_t_4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(j4_t, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(me_c_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(me_c_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(me_c_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(me_c_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_me_t_1)
                    .addComponent(l_me_t_2)
                    .addComponent(l_me_t_3)
                    .addComponent(l_me_t_4))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j5_t)
                    .addComponent(j_c_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_c_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_c_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(j_c_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(l_j_t_1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(l_j_t_4)
                            .addComponent(l_j_t_3)
                            .addComponent(l_j_t_2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void l_c_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_l_c_3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_l_c_3ActionPerformed

    private void d_c_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d_c_1ActionPerformed


    }//GEN-LAST:event_d_c_1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Affectation_kine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Affectation_kine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Affectation_kine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Affectation_kine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Affectation_kine().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> d_c_1;
    private javax.swing.JComboBox<String> d_c_2;
    private javax.swing.JComboBox<String> d_c_3;
    private javax.swing.JComboBox<String> d_c_4;
    private javax.swing.JLabel j1_t;
    private javax.swing.JLabel j2_t;
    private javax.swing.JLabel j3_t;
    private javax.swing.JLabel j4_t;
    private javax.swing.JLabel j5_t;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> j_c_1;
    private javax.swing.JComboBox<String> j_c_2;
    private javax.swing.JComboBox<String> j_c_3;
    private javax.swing.JComboBox<String> j_c_4;
    private keeptoo.KButton kButton1;
    private javax.swing.JComboBox<String> l_c_1;
    private javax.swing.JComboBox<String> l_c_2;
    private javax.swing.JComboBox<String> l_c_3;
    private javax.swing.JComboBox<String> l_c_4;
    private javax.swing.JLabel l_d_t_1;
    private javax.swing.JLabel l_d_t_2;
    private javax.swing.JLabel l_d_t_3;
    private javax.swing.JLabel l_d_t_4;
    private javax.swing.JLabel l_j_t_1;
    private javax.swing.JLabel l_j_t_2;
    private javax.swing.JLabel l_j_t_3;
    private javax.swing.JLabel l_j_t_4;
    private javax.swing.JLabel l_l_t_1;
    private javax.swing.JLabel l_l_t_2;
    private javax.swing.JLabel l_l_t_3;
    private javax.swing.JLabel l_l_t_4;
    private javax.swing.JLabel l_ma_t_1;
    private javax.swing.JLabel l_ma_t_2;
    private javax.swing.JLabel l_ma_t_3;
    private javax.swing.JLabel l_ma_t_4;
    private javax.swing.JLabel l_me_t_1;
    private javax.swing.JLabel l_me_t_2;
    private javax.swing.JLabel l_me_t_3;
    private javax.swing.JLabel l_me_t_4;
    private javax.swing.JComboBox<String> ma_c_1;
    private javax.swing.JComboBox<String> ma_c_2;
    private javax.swing.JComboBox<String> ma_c_3;
    private javax.swing.JComboBox<String> ma_c_4;
    private javax.swing.JComboBox<String> me_c_1;
    private javax.swing.JComboBox<String> me_c_2;
    private javax.swing.JComboBox<String> me_c_3;
    private javax.swing.JComboBox<String> me_c_4;
    private javax.swing.JLabel time1;
    private javax.swing.JLabel time2;
    private javax.swing.JLabel time3;
    private javax.swing.JLabel time4;
    // End of variables declaration//GEN-END:variables
}
