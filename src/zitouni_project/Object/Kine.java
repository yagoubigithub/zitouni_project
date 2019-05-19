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
public class Kine {
   private  int id;
  private  Personne personne;
  private String mode_travail;

    public Kine(int id, Personne personne, String mode_travail) {
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

    public String getMode_travail() {
        return mode_travail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public void setMode_travail(String mode_travail) {
        this.mode_travail = mode_travail;
    }
  
  
}
