/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import zitouni_project.Object.Medecin;
import zitouni_project.Object.Personne;

/**
 *
 * @author Yagoubi
 */
public class Db {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Db() {
        try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connect = DriverManager.getConnection("jdbc:mysql://localhost/" + "mpr2" + "?"
                    + "user=admin&password=");
            statement = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ********************************
     */
    /*
    fetch all data in patient table in database
    
     */
    public ResultSet getSelect(String selectQuery) {
        try {
            resultSet = statement.executeQuery(selectQuery);
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;

    }

    public ResultSet getAllPatient() {
        return getSelect("SELECT tab2.*,personne.nom nom_medecin,personne.prenom prenom_medecin "
                + "FROM (SELECT * FROM(SELECT patient.id id_patient, personne.id id_personne_,  personne.nom, personne.prenom,personne.date_naissance,\n"
                + "                personne.adresse,personne.num_tel,personne.sexe,patient.nmbr_seance,patient.date_visit,\n"
                + "                unite.nom nom_unite,\n"
                + "                 lettre.diagno ,\n"
                + "                patient.id_medecin\n"
                + "                 FROM personne \n"
                + "                JOIN patient \n"
                + "                ON patient.id_personne= personne.id\n"
                + "                JOIN lettre \n"
                + "                ON lettre.id_patient=patient.id \n"
                + "                JOIN unite\n"
                + "                ON lettre.id_unite=unite.id) tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + "ORDER BY tab2.id_personne_ DESC"
        );
    }

    ResultSet getPatietsWithNom(String nom) {
        return  getSelect(returnQuerySelectWithDate(" WHERE tab2.nom LIKE '%" + nom + "%' "));
       
    }

    String returnQuerySelectWithDate(String selected){
         String  query = "SELECT tab2.*,personne.nom nom_medecin,personne.prenom prenom_medecin "
                + "FROM (SELECT * FROM(SELECT patient.id id_patient, personne.id id_personne_,  personne.nom, personne.prenom,personne.date_naissance,\n"
                + "                personne.adresse,personne.num_tel,personne.sexe,patient.nmbr_seance,patient.date_visit,\n"
                + "                unite.nom nom_unite,\n"
                + "                 lettre.diagno ,\n"
                + "                patient.id_medecin\n"
                + "                 FROM personne \n"
                + "                JOIN patient \n"
                + "                ON patient.id_personne= personne.id\n"
                + "                JOIN lettre \n"
                + "                ON lettre.id_patient=patient.id \n"
                + "                JOIN unite\n"
                + "                ON lettre.id_unite=unite.id) tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                +  selected
                + " ORDER BY tab2.id_personne_ DESC";
         return query;
    }
    ResultSet getPatietsWithdate(String day, String mois, String annee) {

       
        if (day != null && mois == null && annee == null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " DAY(date_visit)=" + day) );
        } else if (day == null && mois != null && annee == null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " +  " MONTH(date_visit)=" + mois) );
           
        } else if (day == null && mois == null && annee != null) {
          return  getSelect(returnQuerySelectWithDate( " WHERE " + " YEAR(date_visit)=" + annee) );
            
        } else if (day != null && mois != null && annee == null) {
           return getSelect(returnQuerySelectWithDate(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND DAY(date_visit)=" + day) );
         
        } else if (day == null && mois != null && annee != null) {
          return  getSelect(returnQuerySelectWithDate(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND YEAR(date_visit)=" + annee) );
           
        } else if (day != null && mois == null && annee != null) {
          return  getSelect(returnQuerySelectWithDate(" WHERE " +  " DAY(date_visit)=" + day
                    + " AND YEAR(date_visit)=" + annee) );
           
        } else if (day != null && mois != null && annee != null) {
           return getSelect(returnQuerySelectWithDate("WHERE MONTH(date_visit)=" + mois
                    + " OR YEAR(date_visit)=" + annee + " OR DAY(date_visit)=" + day) );
       
        } else {
         return   getSelect(returnQuerySelectWithDate( "") );
            
        }

    }

    String Auth(String nom, String password) {
        String type = "no";
        String md5 = "";
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            md5 = number.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }

        ResultSet res = getSelect("SELECT * FROM user JOIN personne ON user.id_personne=personne.id WHERE personne.nom='" + nom + "'  AND user.password='" + md5 + "' LIMIT 1");
        try {
            while (res.next()) {
                type = res.getString("type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }

    public boolean insertPatient(String nom, String prenom, String date_naissance,
            String adresse, String sexe, String num_tel, int nmbr_seance,
            String nom_medecin_consult, String dg, String date_dg, String unite, int id_medecin) {
        // the mysql insert statement
        String query = " insert into personne (`nom`, `prenom`, `date_naissance`, `adresse`, `sexe`, `num_tel`)"
                + " values (?, ?, ?, ?, ? , ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, nom);
            preparedStmt.setString(2, prenom);
            preparedStmt.setString(3, date_naissance);
            preparedStmt.setString(4, adresse);
            preparedStmt.setString(5, sexe);
            preparedStmt.setString(6, num_tel);

            preparedStmt.executeUpdate();
            int id_personne = 0;
            ResultSet rs = preparedStmt.getGeneratedKeys();
            while (rs.next()) {
                id_personne = rs.getInt(1);
            }

            System.out.println(id_personne + "  id_personne");

            if (id_personne > 0) {
                Date date = new Date();

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);

                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String date_visit = year + "-"
                        + (month >= 10 ? month : "0" + month)
                        + "-" + (day >= 10 ? day : "0" + day);
                query = " insert into patient (`id_personne`, `nmbr_seance`, `id_medecin` ,`date_visit` )"
                        + " values (? , ? , ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt2;
                try {
                    preparedStmt2 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    preparedStmt2.setInt(1, id_personne);
                    preparedStmt2.setInt(2, nmbr_seance);
                    preparedStmt2.setInt(3, id_medecin);
                    preparedStmt2.setString(4, date_visit);
                    preparedStmt2.executeUpdate();
                    int id_patient = 0;
                    rs = preparedStmt2.getGeneratedKeys();
                    while (rs.next()) {
                        id_patient = rs.getInt(1);
                    }
                    System.out.println(id_patient + "  id_patient");

                    if (id_patient > 0) {
                        int id_unite = getIdUniteFromName(unite);
                        System.out.println(id_unite + "  id_unite");
                        query = " insert into lettre (`id_patient`, `nom_medecin_consult`, `diagno`, `date`, `id_unite` )"
                                + " values (? , ? , ? , ? , ?)";

                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt3;
                        try {
                            preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                            preparedStmt3.setInt(1, id_patient);
                            preparedStmt3.setString(2, nom_medecin_consult);
                            preparedStmt3.setString(3, dg);
                            preparedStmt3.setString(4, date_dg);
                            preparedStmt3.setInt(5, id_unite);
                            preparedStmt3.executeUpdate();
                            int id_lettre = 0;
                            rs = preparedStmt3.getGeneratedKeys();
                            if (rs.next()) {
                                id_lettre = rs.getInt(1);
                            }
                            System.out.println(id_lettre + "  id_lettre");
                        } catch (SQLException ex) {

                            return false;
                        }
                    } else {
                        return false;
                    }

                } catch (SQLException ex) {

                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

        // execute the preparedstatement
    }

    public int getIdUniteFromName(String nom) {
        int id = 0;
        ResultSet res = getSelect("SELECT id FROM unite WHERE nom='" + nom + "'");
        try {
            while (res.next()) {
                id = res.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    ArrayList<Medecin> getMedecinParUnite(String unite, String nuitOrJour) {

        ArrayList<Medecin> medecins = new ArrayList<>();
        int id_unite = getIdUniteFromName(unite);

        ResultSet res = getSelect("SELECT * FROM medecin "
                + "JOIN personne "
                + "ON personne.id=medecin.id_personne "
                + "WHERE medecin.id_unite=" + id_unite
                + " AND medecin.mode_travail='" + nuitOrJour + "'");
        try {
            while (res.next()) {
                System.out.println(id_unite);

                medecins.add(new Medecin(
                        res.getInt("id"),
                        new Personne(res.getString("nom"), res.getString("prenom")),
                        nuitOrJour));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return medecins;
    }

    int getIdMedecinFromNom(String nom_medecin) {
        int id = 0;
        ResultSet res = getSelect("SELECT medecin.id FROM medecin "
                + "JOIN personne "
                + "ON personne.id=medecin.id_personne "
                + " "
                + " WHERE CONCAT('Dr ',personne.nom,' ' ,personne.prenom)='" + nom_medecin + "'");
        try {
            while (res.next()) {
                id = res.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    boolean deletePatient(int id_patient) {
      String query = "delete from patient where id = ?";
      PreparedStatement preparedStmt;
        try {
            preparedStmt = connect.prepareStatement(query);
             preparedStmt.setInt(1, id_patient);
              preparedStmt.execute();
              
              return true;
        } catch (SQLException ex) {
            
            return false;
        }
     

      // execute the preparedstatement
     
    }

}
