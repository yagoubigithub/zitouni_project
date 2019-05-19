/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import zitouni_project.Object.Kine;
import zitouni_project.Object.Patient;
import zitouni_project.Object.Personne;
import zitouni_project.Object.Suivi;

public class EmploiDuTemps extends javax.swing.JFrame {

    String mode_travail = "jour";
    int id_kine;
    Db db;

    public EmploiDuTemps() {
        initComponents();
        db = new Db();
        addTime();
        addJour();
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

        //  addPatients();
    }

    public void setIdKine(int id_kine) {
        this.id_kine = id_kine;
        addPatients();
        
        addKine();
    }

    public void addKine(){
        Kine kine = db.getKine(this.id_kine);
        kine_name.setText(kine.getPersonne().getNom() + " " + kine.getPersonne().getPrenom());
    }
    public ArrayList<Suivi> getSuivi(String date) {
        ArrayList<Suivi> suivis = new ArrayList<>();
        ResultSet res = db.getAllSuiviInKineFromDateAndIdKine(id_kine, date);
        try {

            while (res.next()) {
                suivis.add(new Suivi(res.getInt("id"), res.getInt("id_unite"), res.getInt("id_patient"),
                        res.getString("date"),
                        res.getString("heure"),
                        res.getInt("id_medecin"),
                        res.getInt("id_kine"),
                        new Personne(res.getInt("id"),
                                res.getString("nom"), res.getString("prenom"),
                                res.getString("adresse"), res.getString("num_tel"),
                                res.getString("date_naissance"),
                                res.getString("sexe")))
                );

            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return suivis;
    }

    public void addJour() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDateTime j1_ = LocalDateTime.now();
        LocalDateTime j2_ = j1_.getDayOfWeek() == DayOfWeek.THURSDAY ? j1_.plusDays(3) : j1_.plusDays(1);
        LocalDateTime j3_ = j2_.getDayOfWeek() == DayOfWeek.THURSDAY ? j2_.plusDays(3) : j2_.plusDays(1);
        LocalDateTime j4_ = j3_.getDayOfWeek() == DayOfWeek.THURSDAY ? j3_.plusDays(3) : j3_.plusDays(1);
        LocalDateTime j5_ = j4_.getDayOfWeek() == DayOfWeek.THURSDAY ? j4_.plusDays(3) : j4_.plusDays(1);

        //System.out.println(dtf.format(j1)); //2016/11/16 12:08:43
        j1.setText(dtf.format(j1_));
        j2.setText(dtf.format(j2_));
        j3.setText(dtf.format(j3_));
        j4.setText(dtf.format(j4_));
        j5.setText(dtf.format(j5_));

    }

    public void addTime() {
        if (this.mode_travail.equals("jour")) {
            t1.setText("8:00-10:00");
            t2.setText("10:00-12:00");
            t3.setText("13:00-15:00");
            t4.setText("15:00-17:00");
        } else if (this.mode_travail.equals("nuit")) {
            t1.setText("17:00-19:00");
            t2.setText("19:00-21:00");
            t3.setText("21:00-23:00");
            t4.setText("23:00-00:00");
        }
    }

    private void addPatients() {

        ArrayList<Suivi> suivi_j1 = getSuivi(j1.getText());
        j1_t1.setText("");
        j1_t2.setText("");
        j1_t3.setText("");
        j1_t4.setText("");
        for (int i = 0; i < suivi_j1.size(); i++) {
            Suivi suivi = suivi_j1.get(i);
            if (this.mode_travail.equals("jour")) {
                switch (suivi.getHeure()) {
                    case "08:00:00":

                        j1_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;
                    case "10:00:00":

                        j1_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "13:00:00":

                        j1_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "15:00:00":

                        j1_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            } else {
                switch (suivi.getHeure()) {

                    case "17:00:00":
                        j1_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;

                    case "19:00:00":
                        j1_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "21:00:00":
                        j1_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "23:00:00":
                        j1_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            }

        }
        
  /************ **************************************************************************************************/
        
        //Jour 2

        ArrayList<Suivi> suivi_j2 = getSuivi(j2.getText());
        j2_t1.setText("");
        j2_t2.setText("");
        j2_t3.setText("");
        j2_t4.setText("");

        for (int i = 0; i < suivi_j2.size(); i++) {
            Suivi suivi = suivi_j2.get(i);

            if (this.mode_travail.contains("j")) {
                switch (suivi.getHeure()) {
                    case "08:00:00":

                        j2_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;
                    case "10:00:00":

                        j2_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "13:00:00":

                        j2_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "15:00:00":

                        j2_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            } else {
                switch (suivi.getHeure()) {

                    case "17:00:00":
                        j2_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;

                    case "19:00:00":
                        j2_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "21:00:00":
                        j2_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "23:00:00":
                        j2_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            }

        }
        /*****************************************************************************************************************/
        
        
        /****************************************************************************************************************/
        //jour 3
        ArrayList<Suivi> suivi_j3 = getSuivi(j3.getText());
        
         j3_t1.setText("");
        j3_t2.setText("");
        j3_t3.setText("");
        j3_t4.setText("");

        for (int i = 0; i < suivi_j3.size(); i++) {
            Suivi suivi = suivi_j3.get(i);

            if (this.mode_travail.contains("j")) {
                switch (suivi.getHeure()) {
                    case "08:00:00":

                        j3_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;
                    case "10:00:00":

                        j3_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "13:00:00":

                        j3_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "15:00:00":

                        j3_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            } else {
                switch (suivi.getHeure()) {

                    case "17:00:00":
                        j3_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;

                    case "19:00:00":
                        j3_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "21:00:00":
                        j3_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "23:00:00":
                        j3_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            }

        }
        /*****************************************************************************************************************/
        
        /***************************************************************************************************************/
        //Jour 4
        ArrayList<Suivi> suivi_j4 = getSuivi(j4.getText());
         j4_t1.setText("");
        j4_t2.setText("");
        j4_t3.setText("");
        j4_t4.setText("");

        for (int i = 0; i < suivi_j4.size(); i++) {
            Suivi suivi = suivi_j4.get(i);

            if (this.mode_travail.contains("j")) {
                switch (suivi.getHeure()) {
                    case "08:00:00":

                        j4_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;
                    case "10:00:00":

                        j4_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "13:00:00":

                        j4_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "15:00:00":

                        j4_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            } else {
                switch (suivi.getHeure()) {

                    case "17:00:00":
                        j4_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;

                    case "19:00:00":
                        j4_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "21:00:00":
                        j4_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "23:00:00":
                        j4_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            }

        }
        /*****************************************************************************************************************/
        
        /*******************************************************************************************************/
        //jour5
        ArrayList<Suivi> suivi_j5 = getSuivi(j5.getText());
        
        
         j5_t1.setText("");
        j5_t2.setText("");
        j5_t3.setText("");
        j5_t4.setText("");

        for (int i = 0; i < suivi_j5.size(); i++) {
            Suivi suivi = suivi_j5.get(i);

            if (this.mode_travail.contains("j")) {
                switch (suivi.getHeure()) {
                    case "08:00:00":

                        j5_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;
                    case "10:00:00":

                        j5_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "13:00:00":

                        j5_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;
                    case "15:00:00":

                        j5_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            } else {
                switch (suivi.getHeure()) {

                    case "17:00:00":
                        j5_t1.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());

                        break;

                    case "19:00:00":
                        j5_t2.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "21:00:00":
                        j5_t3.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                    case "23:00:00":
                        j5_t4.setText(suivi.getPatient().getNom() + " " + suivi.getPatient().getPrenom());
                        break;

                }
            }

        }
        /*****************************************************************************************************************/

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
        j1 = new javax.swing.JLabel();
        j1_t1 = new javax.swing.JLabel();
        j1_t2 = new javax.swing.JLabel();
        j1_t3 = new javax.swing.JLabel();
        j1_t4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        kine_name = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        j2 = new javax.swing.JLabel();
        j2_t1 = new javax.swing.JLabel();
        j2_t2 = new javax.swing.JLabel();
        j2_t3 = new javax.swing.JLabel();
        j2_t4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        j3 = new javax.swing.JLabel();
        j3_t1 = new javax.swing.JLabel();
        j3_t2 = new javax.swing.JLabel();
        j3_t3 = new javax.swing.JLabel();
        j3_t4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        j4 = new javax.swing.JLabel();
        j4_t1 = new javax.swing.JLabel();
        j4_t2 = new javax.swing.JLabel();
        j4_t3 = new javax.swing.JLabel();
        j4_t4 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        j5 = new javax.swing.JLabel();
        j5_t1 = new javax.swing.JLabel();
        j5_t2 = new javax.swing.JLabel();
        j5_t3 = new javax.swing.JLabel();
        j5_t4 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        t1 = new javax.swing.JLabel();
        t2 = new javax.swing.JLabel();
        t3 = new javax.swing.JLabel();
        t4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        j1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        j1.setText("2019/05/21");

        j1_t1.setText("jLabel11");

        j1_t2.setText("jLabel12");

        j1_t3.setText("jLabel13");

        j1_t4.setText("jLabel14");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j1)
                .addGap(73, 73, 73)
                .addComponent(j1_t1)
                .addGap(113, 113, 113)
                .addComponent(j1_t2)
                .addGap(130, 130, 130)
                .addComponent(j1_t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j1_t4)
                .addGap(71, 71, 71))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j1)
                    .addComponent(j1_t1)
                    .addComponent(j1_t2)
                    .addComponent(j1_t3)
                    .addComponent(j1_t4))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        kine_name.setText("jLabel10");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(kine_name)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(kine_name)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        j2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        j2.setText("2019/01/14");

        j2_t1.setText("jLabel15");

        j2_t2.setText("jLabel16");

        j2_t3.setBackground(new java.awt.Color(255, 255, 255));
        j2_t3.setText("jLabel17");

        j2_t4.setText("jLabel18");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j2)
                .addGap(70, 70, 70)
                .addComponent(j2_t1)
                .addGap(114, 114, 114)
                .addComponent(j2_t2)
                .addGap(133, 133, 133)
                .addComponent(j2_t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j2_t4)
                .addGap(66, 66, 66))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j2)
                    .addComponent(j2_t1)
                    .addComponent(j2_t2)
                    .addComponent(j2_t3)
                    .addComponent(j2_t4))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        j3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        j3.setText("2019/01/21");

        j3_t1.setText("jLabel19");

        j3_t2.setText("jLabel20");

        j3_t3.setText("jLabel21");

        j3_t4.setText("jLabel22");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j3)
                .addGap(70, 70, 70)
                .addComponent(j3_t1)
                .addGap(116, 116, 116)
                .addComponent(j3_t2)
                .addGap(132, 132, 132)
                .addComponent(j3_t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j3_t4)
                .addGap(67, 67, 67))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j3)
                    .addComponent(j3_t1)
                    .addComponent(j3_t2)
                    .addComponent(j3_t3)
                    .addComponent(j3_t4))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        j4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        j4.setText("2018/01/44");

        j4_t1.setText("jLabel23");

        j4_t2.setText("jLabel24");

        j4_t3.setText("jLabel25");

        j4_t4.setText("jLabel26");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j4)
                .addGap(71, 71, 71)
                .addComponent(j4_t1)
                .addGap(112, 112, 112)
                .addComponent(j4_t2)
                .addGap(134, 134, 134)
                .addComponent(j4_t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j4_t4)
                .addGap(63, 63, 63))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j4)
                    .addComponent(j4_t1)
                    .addComponent(j4_t2)
                    .addComponent(j4_t3)
                    .addComponent(j4_t4))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        j5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        j5.setText("2018/05/20");

        j5_t1.setText("jLabel27");

        j5_t2.setText("jLabel28");

        j5_t3.setText("jLabel29");

        j5_t4.setText("jLabel30");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(j5)
                .addGap(68, 68, 68)
                .addComponent(j5_t1)
                .addGap(117, 117, 117)
                .addComponent(j5_t2)
                .addGap(130, 130, 130)
                .addComponent(j5_t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(j5_t4)
                .addGap(58, 58, 58))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(j5)
                    .addComponent(j5_t1)
                    .addComponent(j5_t2)
                    .addComponent(j5_t3)
                    .addComponent(j5_t4))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        t1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        t1.setText("08:00-12:00");

        t2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        t2.setText("10:00-12:00");

        t3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        t3.setText("13:00-15:00");

        t4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        t4.setText("15:00-17:00");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(t1)
                .addGap(64, 64, 64)
                .addComponent(t2)
                .addGap(80, 80, 80)
                .addComponent(t3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(t4)
                .addGap(45, 45, 45))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 64, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t1)
                    .addComponent(t2)
                    .addComponent(t3)
                    .addComponent(t4)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(EmploiDuTemps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmploiDuTemps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmploiDuTemps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmploiDuTemps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmploiDuTemps().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel j1;
    private javax.swing.JLabel j1_t1;
    private javax.swing.JLabel j1_t2;
    private javax.swing.JLabel j1_t3;
    private javax.swing.JLabel j1_t4;
    private javax.swing.JLabel j2;
    private javax.swing.JLabel j2_t1;
    private javax.swing.JLabel j2_t2;
    private javax.swing.JLabel j2_t3;
    private javax.swing.JLabel j2_t4;
    private javax.swing.JLabel j3;
    private javax.swing.JLabel j3_t1;
    private javax.swing.JLabel j3_t2;
    private javax.swing.JLabel j3_t3;
    private javax.swing.JLabel j3_t4;
    private javax.swing.JLabel j4;
    private javax.swing.JLabel j4_t1;
    private javax.swing.JLabel j4_t2;
    private javax.swing.JLabel j4_t3;
    private javax.swing.JLabel j4_t4;
    private javax.swing.JLabel j5;
    private javax.swing.JLabel j5_t1;
    private javax.swing.JLabel j5_t2;
    private javax.swing.JLabel j5_t3;
    private javax.swing.JLabel j5_t4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel kine_name;
    private javax.swing.JLabel t1;
    private javax.swing.JLabel t2;
    private javax.swing.JLabel t3;
    private javax.swing.JLabel t4;
    // End of variables declaration//GEN-END:variables

   
}
