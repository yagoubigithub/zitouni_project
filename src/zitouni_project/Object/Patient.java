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
public class Patient {

   private  int id;
    private String nom;
    private String prenom;

    private String dg;

    private int nombre_seance;
    private String nom_medecin;
    private String prenom_medecin;
    private String date_visit;

    public Patient(int id, String nom, String prenom, String dg, int nombre_seance, String nom_medecin, String prenom_medecin, String date_visit) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dg = dg;
        this.nombre_seance = nombre_seance;
        this.nom_medecin = nom_medecin;
        this.prenom_medecin = prenom_medecin;
        this.date_visit = date_visit;
    }

    public void setNombre_seance(int nombre_seance) {
        this.nombre_seance = nombre_seance;
    }

    public int getNombre_seance() {
        return nombre_seance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDg(String dg) {
        this.dg = dg;
    }

    public void setNom_medecin(String nom_medecin) {
        this.nom_medecin = nom_medecin;
    }

    public void setPrenom_medecin(String prenom_medecin) {
        this.prenom_medecin = prenom_medecin;
    }

    public void setDate_visit(String date_visit) {
        this.date_visit = date_visit;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDg() {
        return dg;
    }

    public String getNom_medecin() {
        return nom_medecin;
    }

    public String getPrenom_medecin() {
        return prenom_medecin;
    }

    public String getDate_visit() {
        return date_visit;
    }
    
    
    

}
