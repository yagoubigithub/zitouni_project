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
public class Medecin {
    
    int id;
    
    Personne personne;
    String grad;
    String profession;
    String mode_travail;

    public Medecin(int id, Personne personne, String mode_travail) {
        this.id = id;
        this.personne = personne;
        this.mode_travail = mode_travail;
    }

    public int getId() {
        return id;
    }

    public Personne getPersonne() {
        return personne;
    }

    public String getGrad() {
        return grad;
    }

    public String getProfession() {
        return profession;
    }

    public String getMode_travail() {
        return mode_travail;
    }

  
    
    
}
