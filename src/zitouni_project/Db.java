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
import zitouni_project.Object.Kine;
import zitouni_project.Object.Medecin;
import zitouni_project.Object.Patient;
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
 /*1*/ public ResultSet getSelect(String selectQuery) {
        try {
            resultSet = statement.executeQuery(selectQuery);
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;

    }

    /*2*/ public ResultSet getAllPatient() {
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
        return getSelect(returnQuerySelectWithDate(" WHERE tab2.nom LIKE '%" + nom + "%' "));

    }

    String returnQuerySelectWithDate(String selected) {
        String query = "SELECT tab2.*,personne.nom nom_medecin,personne.prenom prenom_medecin "
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
                + selected
                + " ORDER BY tab2.id_personne_ DESC";
        return query;
    }

    ResultSet getPatietsWithdate(String day, String mois, String annee) {

        if (day != null && mois == null && annee == null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " DAY(date_visit)=" + day));
        } else if (day == null && mois != null && annee == null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " MONTH(date_visit)=" + mois));

        } else if (day == null && mois == null && annee != null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " YEAR(date_visit)=" + annee));

        } else if (day != null && mois != null && annee == null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND DAY(date_visit)=" + day));

        } else if (day == null && mois != null && annee != null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND YEAR(date_visit)=" + annee));

        } else if (day != null && mois == null && annee != null) {
            return getSelect(returnQuerySelectWithDate(" WHERE " + " DAY(date_visit)=" + day
                    + " AND YEAR(date_visit)=" + annee));

        } else if (day != null && mois != null && annee != null) {
            return getSelect(returnQuerySelectWithDate("WHERE MONTH(date_visit)=" + mois
                    + " OR YEAR(date_visit)=" + annee + " OR DAY(date_visit)=" + day));

        } else {
            return getSelect(returnQuerySelectWithDate(""));

        }

    }

    String convertToMd5(String password) {
        String md5 = "";
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            md5 = number.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md5;
    }

    String Auth(String nom, String password) {
        String type = "no";
        String md5 = convertToMd5(password);

        ResultSet res = getSelect("SELECT * FROM user"
                + " JOIN personne"
                + " ON user.id_personne=personne.id"
                + " WHERE personne.nom='" + nom + "'  "
                + "AND user.password='" + md5 + "' LIMIT 1");
        try {
            while (res.next()) {
                type = res.getString("type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }

    int getIdPersonneFromUsers(String nom, String password) {
        int id_personne = -1;
        String md5 = convertToMd5(password);

        ResultSet res = getSelect("SELECT * FROM user"
                + " JOIN personne"
                + " ON user.id_personne=personne.id"
                + " WHERE personne.nom='" + nom + "'  "
                + "AND user.password='" + md5 + "' LIMIT 1");
        try {
            while (res.next()) {
                id_personne = res.getInt("id_personne");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_personne;
    }

    int getIdMedecinFomrIdpersonne(int id_personne) {
        int id_medecin = -1;

        ResultSet res = getSelect("SELECT * FROM medecin WHERE id_personne=" + id_personne);
        try {
            while (res.next()) {
                id_medecin = res.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_medecin;
    }

    public boolean insertPatient(String nom, String prenom, String date_naissance,
            String adresse, String sexe, String num_tel, int nmbr_seance,
            String nom_medecin_consult, String dg, String date_dg, int id_medecin, int id_unite) {
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
                query = " insert into patient (`id_personne`, `nmbr_seance`, `id_medecin` ,`date_visit` , `id_unite`)"
                        + " values (? , ? , ?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt2;
                try {
                    preparedStmt2 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    preparedStmt2.setInt(1, id_personne);
                    preparedStmt2.setInt(2, nmbr_seance);
                    preparedStmt2.setInt(3, id_medecin);
                    preparedStmt2.setString(4, date_visit);
                    preparedStmt2.setInt(5, id_unite);
                    preparedStmt2.executeUpdate();
                    int id_patient = 0;
                    rs = preparedStmt2.getGeneratedKeys();
                    while (rs.next()) {
                        id_patient = rs.getInt(1);
                    }
                    System.out.println(id_patient + "  id_patient");

                    if (id_patient > 0) {

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

    int insertMedecin(String nom, String prenom, String date_naissance, String adresse,
            String sexe, String tele, int id_unite, String grad, String profession, String mode_travail) {

        int id_personne = -1;
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
            preparedStmt.setString(6, tele);

            preparedStmt.executeUpdate();

            ResultSet rs = preparedStmt.getGeneratedKeys();
            while (rs.next()) {
                id_personne = rs.getInt(1);
            }

            System.out.println(id_personne + "  id_personne");

            if (id_personne > 0) {
                // add to medecin table

                query = " INSERT INTO `medecin`( `id_personne`, `id_unite`, `grade`, `profession`, `mode_travail`)"
                        + " values (? , ? , ? , ? , ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt3;
                try {
                    preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    preparedStmt3.setInt(1, id_personne);
                    preparedStmt3.setInt(2, id_unite);
                    preparedStmt3.setString(3, grad);
                    preparedStmt3.setString(4, profession);
                    preparedStmt3.setString(5, mode_travail);
                    preparedStmt3.executeUpdate();
                    int id_medecin = 0;
                    rs = preparedStmt3.getGeneratedKeys();
                    if (rs.next()) {
                        id_medecin = rs.getInt(1);
                    }
                    System.out.println(id_medecin + "  id_medecin");
                } catch (SQLException ex) {

                    return -1;
                }

            }

        } catch (SQLException ex) {

            return -1;
        }
        return id_personne;

    }

    int insertkine(String nom, String prenom, String date_naissance, String adresse, String sexe, String tele, String mode_travail) {

        int id_personne = -1;
        String query = " insert into personne (`nom`, `prenom`, `date_naissance`, `adresse`, `sexe`, `num_tel`)"
                + " values (?, ?, ?, ?, ? , ?)";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, nom);
            preparedStmt.setString(2, prenom);
            preparedStmt.setString(3, date_naissance);
            preparedStmt.setString(4, adresse);
            preparedStmt.setString(5, sexe);
            preparedStmt.setString(6, tele);

            preparedStmt.executeUpdate();

            ResultSet rs = preparedStmt.getGeneratedKeys();
            while (rs.next()) {
                id_personne = rs.getInt(1);
            }

            System.out.println(id_personne + "  id_personne");

            if (id_personne > 0) {
                // add to medecin table

                query = " INSERT INTO `kine`( `id_personne`, `mode_travail`)"
                        + " values (? , ? )";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt3;
                try {
                    preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    preparedStmt3.setInt(1, id_personne);

                    preparedStmt3.setString(2, mode_travail);
                    preparedStmt3.executeUpdate();
                    int id_kine = 0;
                    rs = preparedStmt3.getGeneratedKeys();
                    if (rs.next()) {
                        id_kine = rs.getInt(1);
                    }
                    System.out.println(id_kine + "  id_medecin");
                } catch (SQLException ex) {

                    System.out.println(ex.getMessage());
                    return -1;
                }

            }

        } catch (SQLException ex) {

            return -1;
        }
        return id_personne;

    }

    ResultSet getAllKine() {
        return getSelect("SELECT * FROM kine JOIN personne ON personne.id= kine.id_personne");

    }

    ResultSet getAllMedecins() {
        return getSelect(""
                + "SELECT personne.*,unite.nom nom_unite,medecin.grade,medecin.id id_medecin,medecin.profession,medecin.mode_travail"
                + " FROM medecin JOIN personne"
                + " ON personne.id=medecin.id_personne "
                + "JOIN unite ON medecin.id_unite=unite.id");
    }

    boolean deleteMedecin(int id_medecin) {
        String query = "delete from medecin where id = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, id_medecin);
            preparedStmt.execute();

            return true;
        } catch (SQLException ex) {

            return false;
        }

    }

    boolean deleteKine(int id_kine) {

        String query = "delete from kine where id = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connect.prepareStatement(query);
            preparedStmt.setInt(1, id_kine);
            preparedStmt.execute();

            return true;
        } catch (SQLException ex) {

            return false;
        }
    }

    public int getIdPersonneFromIdMedecin(int id_medecin) {
        ResultSet res = getSelect("SELECT id_personne FROM medecin  WHERE id=" + id_medecin);
        int id_personne = -1;
        try {
            while (res.next()) {
                id_personne = res.getInt("id_personne");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_personne;
    }

    public int getIdPersonneFromIdkine(int id_kine) {
        ResultSet res = getSelect("SELECT id_personne FROM kine  WHERE id=" + id_kine);
        int id_personne = -1;
        try {
            while (res.next()) {
                id_personne = res.getInt("id_personne");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_personne;
    }

    boolean updateMedecin(int id_medecin, String nom, String prenom,
            String date_naissance, String adresse, String sexe, String num_tel, int id_unite, String grade, String profession, String mode_travail) {
        int id_personne = getIdPersonneFromIdMedecin(id_medecin);
        if (id_personne > 0) {
            String query = " update   personne set `nom` = ?, `prenom` = ?, `date_naissance` = ?, `adresse` = ?,"
                    + " `sexe` = ?, `num_tel`= ? "
                    + "  WHERE id=" + id_personne;

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

                if (id_personne > 0) {

                    query = " UPDATE `medecin` SET `id_personne`= ?, `id_unite`=?, `grade`=?, `profession`=?, `mode_travail`=? "
                            + " WHERE id=" + id_medecin;

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt3;
                    try {
                        preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        preparedStmt3.setInt(1, id_personne);
                        preparedStmt3.setInt(2, id_unite);
                        preparedStmt3.setString(3, grade);
                        preparedStmt3.setString(4, profession);
                        preparedStmt3.setString(5, mode_travail);
                        preparedStmt3.executeUpdate();

                    } catch (SQLException ex) {

                        return false;
                    }

                }

            } catch (SQLException ex) {

                return false;
            }
            return true;
        }
        return true;

    }

    boolean updatekine(int id_kine, String nom, String prenom, String date_naissance, String adresse, String sexe, String num_tel, String mode_travail) {
        int id_personne = getIdPersonneFromIdkine(id_kine);

        System.out.println("id kine = " + id_kine + " id personne = " + id_personne);
        if (id_personne > 0) {
            String query = " update   personne set `nom` = ?, `prenom` = ?, `date_naissance` = ?, `adresse` = ?,"
                    + " `sexe` = ?, `num_tel`= ? "
                    + "  WHERE id=" + id_personne;

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

                if (id_personne > 0) {

                    query = " UPDATE `kine` SET `id_personne`= ?, `mode_travail`=? "
                            + " WHERE id=" + id_kine;

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt3;
                    try {
                        preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        preparedStmt3.setInt(1, id_personne);

                        preparedStmt3.setString(2, mode_travail);
                        preparedStmt3.executeUpdate();

                    } catch (SQLException ex) {

                        System.out.println(ex.getMessage());
                        return false;
                    }

                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
            return true;
        }
        return true;
    }

    ResultSet getAllPatientInKine() {

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
                + "                ON lettre.id_unite=unite.id "
                + "AND unite.nom='kinésithérapie' "
                + ") tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + "ORDER BY tab2.id_personne_ DESC"
        );
    }

    Patient getPatientParDateSuiviAndTime(int id_kine, String data_suivi, String heure) {
        Patient patient = null;
        ResultSet res = getSelect("SELECT tab2.*,personne.nom nom_medecin,personne.prenom prenom_medecin "
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
                + "                ON lettre.id_unite=unite.id "
                + "AND unite.nom='kinésithérapie' "
                + "JOIN suivi "
                + "ON suivi.id_patient=patient.id AND suivi.date='" + data_suivi + "' AND suivi.heure='" + heure + "' AND suivi.id_kine=" + id_kine + " "
                + ") tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + "ORDER BY tab2.id_personne_ DESC "
                + ""
        );
        try {
            while (res.next()) {
                patient = new Patient(res.getInt("id_patient"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("diagno"), res.getInt("nmbr_seance"),
                        res.getString("nom_medecin"),
                        res.getString("prenom_medecin"), res.getString("date_visit"));
                System.out.println(res.getString("nom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patient;

    }

    boolean updateSuiviParDateAndTime(String date, String heure, int id_patient) {
        String query = " UPDATE `suivi` SET `id_patient`= ? "
                + " WHERE date=? AND heure=? ";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt3;
        try {
            preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt3.setInt(1, id_patient);

            preparedStmt3.setString(2, date);
            preparedStmt3.setString(3, heure);
            preparedStmt3.executeUpdate();

        } catch (SQLException ex) {

            System.out.println("hena " + ex.getMessage());
            return false;
        }

        return true;
    }

    int getIdMedecinFromIdPatient(int id_patient) {

        int id = 0;
        ResultSet res = getSelect("SELECT id_medecin FROM patient WHERE id=" + id_patient);
        try {
            while (res.next()) {
                id = res.getInt("id_medecin");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    boolean createSuivi(int id_unite, int id_patient, String date, String heure, int id_medecin, int id_kine) {
        String query = " INSERT INTO suivi (`id_unite`, `id_patient`, `date`, `heure`, `id_medecin`, `id_kine` )"
                + " values (? , ? , ? , ? , ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt3;
        try {
            preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt3.setInt(1, id_unite);
            preparedStmt3.setInt(2, id_patient);
            preparedStmt3.setString(3, date);
            preparedStmt3.setString(4, heure);
            preparedStmt3.setInt(5, id_medecin);
            preparedStmt3.setInt(6, id_kine);
            preparedStmt3.executeUpdate();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    ResultSet getAllSuiviInKineFromDateAndIdKine(int id_kine, String date) {
        return getSelect("SELECT * FROM `suivi` \n"
                + "	JOIN patient\n"
                + "	ON suivi.id_patient=patient.id \n"
                + "	JOIN personne \n"
                + "	ON personne.id=patient.id_personne \n"
                + "WHERE suivi.date = '" + date + "' AND suivi.id_kine=" + id_kine
        );
    }

    Kine getKine(int id_kine) {
        Kine kine = null;
        ResultSet res = getSelect("SELECT * FROM kine JOIN personne "
                + "ON personne.id=kine.id_personne WHERE kine.id=" + id_kine);
        try {
            while (res.next()) {
                kine = new Kine(res.getInt("id"), new Personne(res.getInt("id"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("adresse"), res.getString("num_tel"),
                        res.getString("date_naissance"),
                        res.getString("sexe")), res.getString("mode_travail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kine;
    }

    boolean insertUser(int id_personne, String unite) {

        if (id_personne > 0) {
            // add to medecin table

            String query = " INSERT INTO `user`( `id_personne`, `type`, `password`)"
                    + " values (? , ? , ? )";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt3;
            try {
                String type = unite.equals("medecin") ? "medecin" : unite + "_agent";
                String hash = convertToMd5("123");
                preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt3.setInt(1, id_personne);
                preparedStmt3.setString(2, type);
                preparedStmt3.setString(3, hash);

                preparedStmt3.executeUpdate();

            } catch (SQLException ex) {

                return false;
            }
        }

        return true;
    }

    ResultSet getAllPatientByIdMedecin(int id_medecin) {
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
                + "                ON lettre.id_patient=patient.id \n "
                + "                JOIN unite\n"
                + "                ON lettre.id_unite=unite.id WHERE patient.id_medecin=" + id_medecin + " "
                + ") tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + "ORDER BY tab2.id_personne_ DESC"
        );
    }

    ResultSet getPatietsWithNomByIdMedecin(int id_medecin, String nom) {
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
                + "                ON lettre.id_patient=patient.id \n "
                + "                JOIN unite\n"
                + "                ON lettre.id_unite=unite.id WHERE patient.id_medecin=" + id_medecin + " "
                + ") tab1\n"
                + "JOIN medecin\n"
                + "ON medecin.id=tab1.id_medecin) tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + " WHERE tab2.nom LIKE '%" + nom + "%'   "
                + "ORDER BY tab2.id_personne_ DESC"
        );
    }

    boolean updatePassword(int id_personne, String new_password) {

        String hash = convertToMd5(new_password);
        String query = " UPDATE `user` SET `password`=? "
                + " WHERE id_personne=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt3;
        try {
            preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt3.setString(1, hash);
            preparedStmt3.setInt(2, id_personne);

            preparedStmt3.executeUpdate();

        } catch (SQLException ex) {

            return false;
        }

        return true;
    }

    ResultSet getPatietsWithdateAndIdMedecin(int id_medecin, String day, String mois, String annee) {
        if (day != null && mois == null && annee == null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " DAY(date_visit)=" + day, id_medecin
            ));
        } else if (day == null && mois != null && annee == null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " MONTH(date_visit)=" + mois, id_medecin));

        } else if (day == null && mois == null && annee != null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " YEAR(date_visit)=" + annee, id_medecin));

        } else if (day != null && mois != null && annee == null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND DAY(date_visit)=" + day, id_medecin));

        } else if (day == null && mois != null && annee != null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " MONTH(date_visit)=" + mois
                    + " AND YEAR(date_visit)=" + annee, id_medecin));

        } else if (day != null && mois == null && annee != null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin(" WHERE " + " DAY(date_visit)=" + day
                    + " AND YEAR(date_visit)=" + annee, id_medecin));

        } else if (day != null && mois != null && annee != null) {
            return getSelect(returnQuerySelectWithDateAndIdMedecin("WHERE MONTH(date_visit)=" + mois
                    + " OR YEAR(date_visit)=" + annee + " OR DAY(date_visit)=" + day, id_medecin));

        } else {
            return getSelect(returnQuerySelectWithDateAndIdMedecin("", id_medecin));

        }
    }

    String returnQuerySelectWithDateAndIdMedecin(String selected, int id_medecin) {
        String query = "SELECT tab2.*,personne.nom nom_medecin,personne.prenom prenom_medecin "
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
                + "ON medecin.id=tab1.id_medecin AND medecin.id=" + id_medecin
                + ") tab2\n"
                + "\n"
                + "JOIN personne \n"
                + "ON tab2.id_personne=personne.id "
                + selected
                + " ORDER BY tab2.id_personne_ DESC";
        return query;
    }

    int getIdKineFromIdPersonne(int id_personne) {
        int id_kine = -1;

        ResultSet res = getSelect("SELECT * FROM kine WHERE id_personne=" + id_personne);
        try {
            while (res.next()) {
                id_kine = res.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_kine;
    }

    boolean updatePatient(int id_patient, String nom, String prenom, String dg, int nb_seance,
            String date_visit) {

        int id_personne = getIdPersonneFromIdPatient(id_patient);
        System.out.println(id_personne);
        if (id_personne > 0) {
            String query = " update   personne set `nom` = ?, `prenom` = ? "
                    + "  WHERE id=" + id_personne;

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt;
            try {
                preparedStmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setString(1, nom);
                preparedStmt.setString(2, prenom);

                preparedStmt.executeUpdate();

                if (id_personne > 0) {

                    query = " UPDATE `patient` SET  `nmbr_seance`=?, `date_visit`=? "
                            + " WHERE id=" + id_patient;

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt3;
                    try {
                        preparedStmt3 = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                        preparedStmt3.setInt(1, nb_seance);
                        preparedStmt3.setString(2, date_visit);

                        preparedStmt3.executeUpdate();

                    } catch (SQLException ex) {

                        System.out.println(ex.getMessage());
                        return false;
                    }

                }

            } catch (SQLException ex) {

                return false;
            }
            return true;
        }
        return true;
    }

    private int getIdPersonneFromIdPatient(int id_patient) {

        ResultSet res = getSelect("SELECT id_personne FROM patient  WHERE id=" + id_patient);
        int id_personne = -1;
        try {
            while (res.next()) {
                id_personne = res.getInt("id_personne");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_personne;
    }

}
