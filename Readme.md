# AuthFram


Get the name and the password from the textField:
```
String nom = jTextField1.getText();
String password = jPasswordField1.getText();
```

Check The user if exist or not. if not return "no" if exist return the type of the user
users :("chef de service","medecin","kinésithérapie_agent","Réceptionniste") : 

```
String type = db.Auth(nom, password);
```


### In class Db : 
```sh
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
```

### convertToMd5
take a string and converted to MD5 hash :

```
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
```

### In class AuthFram in ActionPerformed of connexion button :
 1.User doesn't exist :
 ```
 if (type.equals("no")) {
          // show a alert to the user**********************
            JOptionPane.showMessageDialog(this, 
         "le nom ou le mot de passe incorect",
         " Erreur ",
         JOptionPane.WARNING_MESSAGE);
            jTextField1.setText("");
            jPasswordField1.setText("");
        } 
```
2:user is a "medecin" : 
First : Get the Id personne via the "password" and "nom" from user table
```
 int id_personne = db.getIdPersonneFromUsers(nom, password);
 ```
 
 #### In Db class
 ```
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

 ```
 
 
 Second  :  Get the Id medecin via Id "personne" From medecin table

 ```
 int id_medecin = db.getIdMedecinFomrIdpersonne(id_personne);
 ```
 
 
 
