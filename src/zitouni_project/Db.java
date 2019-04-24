/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zitouni_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yagoubi
 */
public class Db {
    
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public Db(){
         try {

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connect = DriverManager.getConnection("jdbc:mysql://localhost/" + "mpr" + "?"
                    + "user=admin&password=");
            statement = connect.createStatement();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    /***********************************/
    
    /*
    fetch all data in patient table in database
    
    */
    
    public ResultSet getSelect(String selectQuery){
          try {
            resultSet = statement.executeQuery(selectQuery );
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        

         return resultSet;
      
    }
    public ResultSet getAllPatient(){
      return getSelect("SELECT * FROM patient");
    }

    ResultSet getPatietsWithNom(String nom) {
        return getSelect("SELECT * FROM patient WHERE nom LIKE '%" + nom  + "%'");
    }
}
