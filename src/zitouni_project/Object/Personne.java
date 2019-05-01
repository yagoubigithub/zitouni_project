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
public class Personne {
   private int id;
   private String nom;
   private String prenom;
  private String adresse;
   private String num_tele;
   private String date_naissance;
   private String sexe;

    public Personne( String nom, String prenom) {
        
        this.nom = nom;
        this.prenom = prenom;
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

    public String getAdresse() {
        return adresse;
    }

    public String getNum_tele() {
        return num_tele;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public String getSexe() {
        return sexe;
    }

   
   
   
    public Personne(int id, String nom, String prenom, String adresse, String num_tele, String date_naissance, String sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.num_tele = num_tele;
        this.date_naissance = date_naissance;
        this.sexe = sexe;
    }
   
   
   
    
    
}
