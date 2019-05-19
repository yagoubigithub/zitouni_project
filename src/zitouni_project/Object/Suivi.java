/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project.Object;

/**
 *
 * @author Yagoubi
 */
public class Suivi {
 int id;
 int id_unite;
 int id_patient;
 String date;
 String heure;
 int id_medecin;
 int id_kine;
 Personne patient;

    public Suivi(int id, int id_unite, int id_patient, String date, String heure, int id_medecin, int id_kine, Personne patient) {
        this.id = id;
        this.id_unite = id_unite;
        this.id_patient = id_patient;
        this.date = date;
        this.heure = heure;
        this.id_medecin = id_medecin;
        this.id_kine = id_kine;
        this.patient = patient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_unite(int id_unite) {
        this.id_unite = id_unite;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public void setId_kine(int id_kine) {
        this.id_kine = id_kine;
    }

    public void setPatient(Personne patient) {
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public int getId_unite() {
        return id_unite;
    }

    public int getId_patient() {
        return id_patient;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public int getId_kine() {
        return id_kine;
    }

    public Personne getPatient() {
        return patient;
    }
 
 
}
