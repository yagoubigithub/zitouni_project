/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import zitouni_project.Object.Kine;
import zitouni_project.Object.Medecin;
import zitouni_project.Object.Patient;
import zitouni_project.Object.Personne;

public class MainFrame extends javax.swing.JFrame {

    
    String type_user;
    Db db;
    ArrayList<Patient> patients;

    ArrayList<Kine> kines;
    ArrayList<Medecin> medecins;
    String personne = "patient";

    
    
    
    public void setType_User(String type_user){
        this.type_user = type_user;
        
        switch(type_user){
            case "chef de service":
            kine_btn.setVisible(true);
            break;
            
            case "Réceptionniste" :
                kine_btn.setVisible(false);
                accueil_btn.setVisible(false);
                psycho_btn.setVisible(false);
                ergoth_btn.setVisible(false);
                 orthophonie_btn.setVisible(false);
                  psychom_btn.setVisible(false);
                   telend_btn.setVisible(false);
                break;
        }
    }
    public void searchPatientsWitheNom(String nom) {
        ResultSet res = db.getPatietsWithNom(nom);
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
        displayPatients();
    }

    /**
     * *********************************************
     */
    /*
    afficher interface acceuil , kini,...
     */
    public void showPanel(String panel) {

        switch (panel) {
            case "kini": {
                accueil_panel.setVisible(false);
                kini_panel.setVisible(true);
                this.personne = "medecin";

            }
            break;

            default: {
                accueil_panel.setVisible(true);
                kini_panel.setVisible(false);
            }
            break;
        }
    }

    //Afficher dans le tableau
    public void displayPatients() {

        DefaultTableModel dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"", "Nom:", "Prénom:", "Dg:", "Séancec:", "Médecin", "Date visit"};
        dm.setColumnIdentifiers(header);
        patient_table.setModel(dm);

        int count = 1;

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);

            Vector<Object> data = new Vector<Object>();
            data.add(patient.getId());

            data.add(patient.getNom());
            data.add(patient.getPrenom());

            data.add(patient.getDg());
            data.add(patient.getNombre_seance());
            data.add("Dr " + patient.getNom_medecin().toUpperCase()
                    + " " + patient.getPrenom_medecin().toUpperCase());

            data.add(patient.getDate_visit());

            dm.addRow(data);
            count++;
        }
        personne = "patient";

    }

    /*
    Afficher tout les information du patients dans le tableau
     */
    public void getAllPatients() {
        ResultSet res = db.getAllPatient();

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
        displayPatients();
    }

    /**
     * **************************************************************************************
     */
    public void getAllKine() {
        ResultSet res = db.getAllKine();
        try {
            kines.clear();

            while (res.next()) {
                kines.add(new Kine(res.getInt("id"), new Personne(res.getInt("id_personne"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("adresse"), res.getString("num_tel"),
                        res.getString("date_naissance"),
                        res.getString("sexe")), res.getString("mode_travail")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayAllKine();
    }

    /**
     * ****************************************************************************************************
     */
    public void displayAllKine() {
        this.personne = "kiné";
        DefaultTableModel dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"", "Nom:", "Prénom:", "Date de naissance", "Adresse", "Sexe", "Num Tel", "Mode travail"};
        dm.setColumnIdentifiers(header);
        jTable1.setModel(dm);

        int count = 1;

        for (int i = 0; i < kines.size(); i++) {
            Kine kine = kines.get(i);

            Vector<Object> data = new Vector<Object>();
            data.add(kine.getId());

            data.add(kine.getPersonne().getNom());
            data.add(kine.getPersonne().getPrenom());
            data.add(kine.getPersonne().getDate_naissance());
            data.add(kine.getPersonne().getAdresse());

            data.add(kine.getPersonne().getSexe());
            data.add(kine.getPersonne().getNum_tele());

            data.add(kine.getMode_travail());

            dm.addRow(data);
            count++;
        }
        affectaion_and_detail_btn.setText("Affectation");

    }

    /**
     * **************************************************************************************
     */
    public void getAllMedecins() {
        ResultSet res = db.getAllMedecins();
        try {
            medecins.clear();

            //personne.*,unite.nom nom_unite,medecin.grade,medecin.id id_medecin,medecin.profession,medecin.mode_travail
            while (res.next()) {
                medecins.add(new Medecin(res.getInt("id_medecin"), new Personne(res.getInt("id"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("adresse"), res.getString("num_tel"),
                        res.getString("date_naissance"),
                        res.getString("sexe")), res.getString("grade"),
                        res.getString("profession"), res.getString("mode_travail")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayAllMedecin();
    }

    /**
     * ****************************************************************************************************
     */
    public void displayAllMedecin() {
        this.personne = "medecin";
        DefaultTableModel dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"", "Nom:", "Prénom:", "Date de naissance", "Adresse", "Sexe", "Num Tel",
            "Grade", "Profession", "Mode travail"};
        dm.setColumnIdentifiers(header);
        jTable1.setModel(dm);

        int count = 1;

        for (int i = 0; i < medecins.size(); i++) {
            Medecin medecin = medecins.get(i);

            Vector<Object> data = new Vector<Object>();
            data.add(medecin.getId());

            data.add(medecin.getPersonne().getNom());
            data.add(medecin.getPersonne().getPrenom());
            data.add(medecin.getPersonne().getDate_naissance());
            data.add(medecin.getPersonne().getAdresse());
            data.add(medecin.getPersonne().getSexe());
            data.add(medecin.getPersonne().getNum_tele());
            data.add(medecin.getGrad());
            data.add(medecin.getProfession());

            data.add(medecin.getMode_travail());

            dm.addRow(data);
            count++;
        }
        affectaion_and_detail_btn.setText("Médecin Detail");
    }

    public MainFrame() {
        initComponents();
        patient_table.setBackground(Color.WHITE);
       
        patient_table.setRowHeight(50);
        
        patients = new ArrayList<>();
        kines = new ArrayList<>();
        medecins = new ArrayList<>();
        showPanel("accueil");
        db = new Db();

        
        getAllPatients();
        //  getAllKine();
        getAllMedecins();
        this.personne = "patient";

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        accueil_btn = new keeptoo.KButton();
        kine_btn = new keeptoo.KButton();
        psycho_btn = new keeptoo.KButton();
        ergoth_btn = new keeptoo.KButton();
        orthophonie_btn = new keeptoo.KButton();
        psychom_btn = new keeptoo.KButton();
        telend_btn = new keeptoo.KButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        accueil_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        patient_table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        input_name_serch = new javax.swing.JTextField();
        kButton1 = new keeptoo.KButton();
        kButton2 = new keeptoo.KButton();
        kButton3 = new keeptoo.KButton();
        kButton8 = new keeptoo.KButton();
        kButton10 = new keeptoo.KButton();
        kini_panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        kButton4 = new keeptoo.KButton();
        kButton5 = new keeptoo.KButton();
        kButton6 = new keeptoo.KButton();
        kButton7 = new keeptoo.KButton();
        modier_btn = new keeptoo.KButton();
        kButton9 = new keeptoo.KButton();
        affectaion_and_detail_btn = new keeptoo.KButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        accueil_btn.setText("Accueill");
        accueil_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accueil_btnActionPerformed(evt);
            }
        });

        kine_btn.setText(" kinésithérapie");
        kine_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kine_btnActionPerformed(evt);
            }
        });

        psycho_btn.setText("psychologie");

        ergoth_btn.setText("ergothérapie");

        orthophonie_btn.setText("orthophonie");

        psychom_btn.setText("psychométrie");

        telend_btn.setText("Talend");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accueil_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kine_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(psycho_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ergoth_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orthophonie_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(psychom_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(telend_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(orthophonie_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(psychom_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telend_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accueil_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kine_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(psycho_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ergoth_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jLayeredPane1.setBorder(new mdlaf.shadows.DropShadowBorder());

        accueil_panel.setBackground(new java.awt.Color(253, 253, 253));

        patient_table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        patient_table.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        patient_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        patient_table.setGridColor(new java.awt.Color(204, 204, 255));
        patient_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patient_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(patient_table);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new mdlaf.shadows.DropShadowBorder());

        input_name_serch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_name_serchActionPerformed(evt);
            }
        });
        input_name_serch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_name_serchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                input_name_serchKeyTyped(evt);
            }
        });

        kButton1.setText("Ajouter");
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        kButton2.setText("Suprimer");
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        kButton3.setText("Listé");
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });

        kButton8.setText("Modifier");
        kButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton8ActionPerformed(evt);
            }
        });

        kButton10.setText("imprimer");
        kButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(input_name_serch, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(309, 309, 309)
                .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(input_name_serch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout accueil_panelLayout = new javax.swing.GroupLayout(accueil_panel);
        accueil_panel.setLayout(accueil_panelLayout);
        accueil_panelLayout.setHorizontalGroup(
            accueil_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        accueil_panelLayout.setVerticalGroup(
            accueil_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accueil_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );

        kini_panel.setBackground(new java.awt.Color(255, 255, 255));

        kButton4.setText("Ajouter Medecin");
        kButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton4ActionPerformed(evt);
            }
        });

        kButton5.setText("Ajouter Kiné");
        kButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton5ActionPerformed(evt);
            }
        });

        kButton6.setText("List des Medecins");
        kButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton6ActionPerformed(evt);
            }
        });

        kButton7.setText("List des kinés");
        kButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton7ActionPerformed(evt);
            }
        });

        modier_btn.setText("Modifier");
        modier_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modier_btnActionPerformed(evt);
            }
        });

        kButton9.setText("Suprimer");
        kButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton9ActionPerformed(evt);
            }
        });

        affectaion_and_detail_btn.setText("Affectation");
        affectaion_and_detail_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affectaion_and_detail_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modier_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(affectaion_and_detail_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(360, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(modier_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(kButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(affectaion_and_detail_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable1FocusLost(evt);
            }
        });
        jTable1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable1InputMethodTextChanged(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout kini_panelLayout = new javax.swing.GroupLayout(kini_panel);
        kini_panel.setLayout(kini_panelLayout);
        kini_panelLayout.setHorizontalGroup(
            kini_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kini_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1128, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        kini_panelLayout.setVerticalGroup(
            kini_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kini_panelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(accueil_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(kini_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(accueil_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(kini_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(accueil_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(kini_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accueil_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accueil_btnActionPerformed

        showPanel("accueil");
        /*  
PrinterJob pjob = PrinterJob.getPrinterJob();
PageFormat preformat = pjob.defaultPage();
preformat.setOrientation(PageFormat.LANDSCAPE);
PageFormat postformat = pjob.pageDialog(preformat);
//If user does not hit cancel then print.
if (preformat != postformat) {
    //Set print component
    pjob.setPrintable(new Printer(jtable1), postformat);
    if (pjob.printDialog()) {
        try {
            pjob.print();
        } catch (PrinterException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}*/
    }//GEN-LAST:event_accueil_btnActionPerformed

    private void kine_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kine_btnActionPerformed
        // TODO add your handling code here:

        showPanel("kini");
    }//GEN-LAST:event_kine_btnActionPerformed

    private void input_name_serchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_name_serchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_name_serchActionPerformed

    private void input_name_serchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_name_serchKeyTyped


    }//GEN-LAST:event_input_name_serchKeyTyped

    private void input_name_serchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_name_serchKeyReleased
        // TODO add your handling code here:
        searchPatientsWitheNom(input_name_serch.getText());
    }//GEN-LAST:event_input_name_serchKeyReleased

    private void patient_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patient_tableMouseClicked
        
    }//GEN-LAST:event_patient_tableMouseClicked

    private void kButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton6ActionPerformed
        getAllMedecins();
    }//GEN-LAST:event_kButton6ActionPerformed

    private void kButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton4ActionPerformed

        AjouterMedecin aj = new AjouterMedecin();
        aj.setVisible(true);
        aj.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                getAllMedecins();

            }
        }
        );

    }//GEN-LAST:event_kButton4ActionPerformed

    private void kButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton5ActionPerformed

        Ajouterkine aj = new Ajouterkine();
        aj.setVisible(true);
        aj.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                getAllKine();

            }
        }
        );

    }//GEN-LAST:event_kButton5ActionPerformed

    private void jTable1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1InputMethodTextChanged

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void jTable1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusLost

    }//GEN-LAST:event_jTable1FocusLost

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed

    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped

    }//GEN-LAST:event_jTable1KeyTyped

    private void modier_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modier_btnActionPerformed
       

        if (personne.equals("medecin")) {
            try {
                int id_medecin = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                String nom = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
                String prenom = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
                String date_naissance = jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
                String adresse = jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString();
                String sexe = jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString();
                String num_tel = jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString();

                int id_unite = db.getIdUniteFromName("kinésithérapie");
                String grade = jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString();
                String profession = jTable1.getValueAt(jTable1.getSelectedRow(), 8).toString();

                String mode_travail = jTable1.getValueAt(jTable1.getSelectedRow(), 9).toString();

                boolean isUpdate = db.updateMedecin(id_medecin, nom, prenom, date_naissance, adresse, sexe,
                        num_tel, id_unite, grade, profession, mode_travail);
                if (isUpdate) {
                    getAllMedecins();
                } else {
                    System.out.println("not update medecin");
                }
            } catch (Exception e) {
                System.out.println("exeption " + e.getMessage());
            }
        } else if (personne.equals("kiné")) {

            System.out.println(personne);

            try {
                int id_kine = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                String nom = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
                String prenom = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString();
                String date_naissance = jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString();
                String adresse = jTable1.getValueAt(jTable1.getSelectedRow(), 4).toString();
                String sexe = jTable1.getValueAt(jTable1.getSelectedRow(), 5).toString();
                String num_tel = jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString();

                String mode_travail = jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString();

                boolean isUpdate = db.updatekine(id_kine, nom, prenom, date_naissance, adresse, sexe,
                        num_tel, mode_travail);
                if (isUpdate) {
                    getAllKine();
                } else {
                    System.out.println("not update kiné");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        modier_btn.setText("Modifier");
    }//GEN-LAST:event_modier_btnActionPerformed

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        modier_btn.setText("Modifier*");
    }//GEN-LAST:event_jTable1FocusGained

    private void kButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton9ActionPerformed
        if (personne.equals("medecin")) {
            try {
                int id_medecin = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                boolean isDelete = db.deleteMedecin(id_medecin);
                if (isDelete) {
                    getAllMedecins();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (personne.equals("kiné")) {
            try {
                int id_kine = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                boolean isDelete = db.deleteKine(id_kine);
                if (isDelete) {
                    getAllKine();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_kButton9ActionPerformed

    private void kButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton7ActionPerformed
        getAllKine();
    }//GEN-LAST:event_kButton7ActionPerformed

    private void affectaion_and_detail_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affectaion_and_detail_btnActionPerformed

        if (this.personne.equals("medecin")) {
            MedecinFram medecinFram = new MedecinFram();
            int id_medecin = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            medecinFram.setId_medecin(id_medecin);
            
            medecinFram.setType_User(type_user);
            medecinFram.setVisible(true);
        } else if (this.personne.equals("kiné")) {
            try {
                int id_kine = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                String mode_travail = jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString();
                if (id_kine > 0) {
                    Affectation_kine affectation_kine = new Affectation_kine();

                    affectation_kine.setModeTravail(mode_travail);
                    affectation_kine.setIdKine(id_kine);
                    affectation_kine.setVisible(true);

                   
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


    }//GEN-LAST:event_affectaion_and_detail_btnActionPerformed

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
          AjouterPatient ajouterPatientFrame = new AjouterPatient();
        ajouterPatientFrame.setVisible(true);

        ajouterPatientFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.out.println(".windowClosed()");
                getAllPatients();

            }

        });
    }//GEN-LAST:event_kButton1ActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        try {
            int id_patient = Integer.parseInt(patient_table.getValueAt(patient_table.getSelectedRow(), 0).toString());
            boolean isDelete = db.deletePatient(id_patient);
            if (isDelete) {
                getAllPatients();
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_kButton2ActionPerformed

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed
     
        FilterFrame filterFrame = new FilterFrame();
        filterFrame.setVisible(true);

        filterFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // get all information
                String day = null;
                String mois = null;
                String annee = null;
                if (filterFrame.isSelectedDay()) {
                    day = filterFrame.getDay();
                }

                if (filterFrame.isSelectedMois()) {
                    mois = filterFrame.getMois();

                }
                if (filterFrame.isSelectedAnnee()) {
                    annee = filterFrame.getAnnee();

                }
                ResultSet res = db.getPatietsWithdate(day, mois, annee);
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
                displayPatients();

                e.getWindow().dispose();
            }
        });
    }//GEN-LAST:event_kButton3ActionPerformed

    private void kButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton8ActionPerformed
        System.out.println(this.personne);
        
        if(this.personne.equals("patient")){
            
            try {
                  int id_patient = Integer.parseInt(patient_table.getValueAt(patient_table.getSelectedRow(), 0).toString());
                String nom = patient_table.getValueAt(patient_table.getSelectedRow(), 1).toString();
                String prenom = patient_table.getValueAt(patient_table.getSelectedRow(), 2).toString();
                String dg = patient_table.getValueAt(patient_table.getSelectedRow(), 3).toString();
                int nb_seance = Integer.parseInt(patient_table.getValueAt(patient_table.getSelectedRow(), 4).toString());
               
                String date_visit = patient_table.getValueAt(patient_table.getSelectedRow(), 6).toString();

                
                boolean isUpdte = db.updatePatient(id_patient,nom,prenom,dg,nb_seance,date_visit);
                
                if(isUpdte){
                    JOptionPane.showMessageDialog(this, 
         "Patient update",
         " Update ",
         JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_kButton8ActionPerformed

    private void kButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton10ActionPerformed
      
     MessageFormat header = new MessageFormat("List DES  Patients");
      MessageFormat footer = new MessageFormat("Page{0,number,Integer}");
     
     
        try {
            patient_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
            /*        ImprimePatients2 imprimePatients = new ImprimePatients2();
            
            imprimePatients.setPatients(patients);
            imprimePatients.setVisible(true);*/
        } catch (PrinterException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_kButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton accueil_btn;
    private javax.swing.JPanel accueil_panel;
    private keeptoo.KButton affectaion_and_detail_btn;
    private keeptoo.KButton ergoth_btn;
    private javax.swing.JTextField input_name_serch;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private keeptoo.KButton kButton1;
    private keeptoo.KButton kButton10;
    private keeptoo.KButton kButton2;
    private keeptoo.KButton kButton3;
    private keeptoo.KButton kButton4;
    private keeptoo.KButton kButton5;
    private keeptoo.KButton kButton6;
    private keeptoo.KButton kButton7;
    private keeptoo.KButton kButton8;
    private keeptoo.KButton kButton9;
    private keeptoo.KButton kine_btn;
    private javax.swing.JPanel kini_panel;
    private keeptoo.KButton modier_btn;
    private keeptoo.KButton orthophonie_btn;
    private javax.swing.JTable patient_table;
    private keeptoo.KButton psycho_btn;
    private keeptoo.KButton psychom_btn;
    private keeptoo.KButton telend_btn;
    // End of variables declaration//GEN-END:variables
}
