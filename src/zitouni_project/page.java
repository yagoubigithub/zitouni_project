/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import zitouni_project.Object.Patient;

/**
 *
 * @author Yagoubi
 */
public class page extends JPanel{
    
    ArrayList<Patient> patients;
    public page( ArrayList<Patient> patients ,int pageNumber){
      
       
            this.patients = patients;
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(900, 900));
             setLayout(null);

             JLabel pageNumberLabel = new JLabel("" + pageNumber);
             
             Dimension pageNumberLabel_size = pageNumberLabel.getPreferredSize();
            pageNumberLabel.setBounds(850, 850, (int) pageNumberLabel_size.getWidth(), (int)pageNumberLabel_size.getHeight());
             
           add(pageNumberLabel);
           
           
            JLabel title = new JLabel("المركز الاستشفائي الجامعي وهران ",SwingConstants.CENTER);
            title.setFont(new Font(title.getFont().getName(), Font.BOLD, 26));
            title.setPreferredSize(new Dimension(800, 100));
            title.setBorder(new LineBorder(Color.BLACK, 3));
             Dimension title_size = title.getPreferredSize();
            title.setBounds(0, 0, (int) title_size.getWidth(), (int)title_size.getHeight());
            
            add(title);
            
            
           
            
            //"nom:", "Prénom:", "Dg:", "Séancec:", "Médecin", "date visit"
            Cellule cel1 = new Cellule("nom:");
            Cellule cel2 = new Cellule( "Prénom:");
            Cellule cel3 = new Cellule("Dg:");
            Cellule cel4 = new Cellule("Séancec:");
            Cellule cel5 = new Cellule("Médecin");
            Cellule cel6 = new Cellule("date visit");
            
            cel1.setBorder(new LineBorder(Color.BLACK, 3));
            cel2.setBorder(new LineBorder(Color.BLACK, 3));
            cel3.setBorder(new LineBorder(Color.BLACK, 3));
            cel4.setBorder(new LineBorder(Color.BLACK, 3));
            cel5.setBorder(new LineBorder(Color.BLACK, 3));
            cel6.setBorder(new LineBorder(Color.BLACK, 3));
            
            
            JPanel table = new JPanel();
            table.setLayout(new GridLayout(0, 6));
            
            
            table.add(cel1);
            table.add(cel2);
            table.add(cel3);
            table.add(cel4);
            table.add(cel5);
            table.add(cel6);
            
            
            
            for (int i = 0; i < patients.size(); i++) {
                Patient get = patients.get(i);
                       cel1 = new Cellule(this.patients.get(i).getNom());
                cel2 = new Cellule(this.patients.get(i).getPrenom());
                cel3 = new Cellule(this.patients.get(i).getDg());
                cel4 = new Cellule(this.patients.get(i).getNombre_seance() + "");
                cel5 = new Cellule("Dr " + this.patients.get(i).getNom_medecin() + " " + 
                        this.patients.get(i).getPrenom_medecin());
                cel6 = new Cellule(this.patients.get(i).getDate_visit());
                  table.add(cel1);
            table.add(cel2);
            table.add(cel3);
            table.add(cel4);
            table.add(cel5);
            table.add(cel6);
                System.out.println("zitouni_project.page.<init>()");
                
            }
            Dimension table_size = table.getPreferredSize();
            table.setBounds(100, 100, (int) table_size.getWidth(), (int)table_size.getHeight());
              add(table);
              
          
          
       
                
             
    }
}
