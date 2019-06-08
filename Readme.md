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
 Finally : open Medecin Fram with the ID of the medecin :
 
 ```
  MedecinFram medecinFram = new MedecinFram();
  medecinFram.setId_medecin(id_medecin);
  medecinFram.setVisible(true);
  dispose();
```

``` dispose();``` this method close the Frame.

 3.User is "kiné"
 
 ```
 else if (type.equals("kinésithérapie_agent")) {
            //Kiné
            ...
 ```
 First : Get the Id personne via the "password" and "nom" from user table
```
 int id_personne = db.getIdPersonneFromUsers(nom, password);
 ```
 Second  :  Get the Id Kine via Id "personne" From kine table
 ```
 int id_kine = db.getIdKineFromIdPersonne(id_personne);
 ```
 
 Finally : open the EmploiDuTemps Frame via kine ID : 
 
 ```
EmploiDuTemps emploiDuTemps = new EmploiDuTemps();
emploiDuTemps.setIdKine(id_kine);
emploiDuTemps.setVisible(true);   
dispose();
 ```
 4.  else => chef de service ```OR``` Réceptionniste
 Open rhe Main Frame :
```
   //MainFrame
   MainFrame mf = new MainFrame();
   mf.setType_User(type);
   mf.setVisible(true);
dispose();
```

 ## MedecinFram : 
 in the constructor we initial the 'patients' array and db Object of the DB class :
 
 ```sh
  public MedecinFram() {
    initComponents(); /* this function is made by netbeans is initial the Frame and the his child*/
        
    patients = new ArrayList<>();
    db=new Db();
       
    }
 ```
 #### setId_medecin : 
 set the Id to MedecinFrame after this we can use the id in the MedecinFrame class.
 ```
 public void setId_medecin(int id_medecin){
        this.id_medecin = id_medecin;
         getAllPatients();
    }
 ```
 ofter the set of the id of the medecin we execute getAllPatients() function for display the table of the "patients" in the MedecinFram
 hint :  get only the patients that visit this medecin we use the Id medecin.
 ```
    public void getAllPatients() {
        ResultSet res = db.getAllPatientByIdMedecin(this.id_medecin);

        try {
            patients.clear(); // clear the patints array after that the array is empty

            while (res.next()) {
                patients.add(new Patient(res.getInt("id_patient"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("diagno"), res.getInt("nmbr_seance"),
                        res.getString("nom_medecin"),
                        res.getString("prenom_medecin"), res.getString("date_visit")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayPatients();
    }
 ```
### In class Db : 
getAllPatientByIdMedecin : get the information about the patients that visit the medecin who have this id_medecin : 
```sh
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
```
 
 
 #### displayPatients => display patients array in the table
 ```sh
  public void displayPatients() {

          //set the header of the table
        DefaultTableModel dm = new DefaultTableModel(0, 0);
        String header[] = new String[]{"", "Nom:", "Prénom:", "Dg:", "Séance:", "Date visit"};
        dm.setColumnIdentifiers(header);
        jTable1.setModel(dm);

        
       
        //set the data in the table =>rows

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = patients.get(i);
            Vector<Object> data = new Vector<Object>();
            data.add(patient.getId());
            data.add(patient.getNom());
            data.add(patient.getPrenom());
            data.add(patient.getDg());
            data.add(patient.getNombre_seance());
            data.add(patient.getDate_visit());
            dm.addRow(data);
            
        }
    }
 ```
 #### the serch input by the name of the patient 
 
 ```
 public void searchPatientsWitheNom(String nom) {
        ResultSet res = db.getPatietsWithNomByIdMedecin(this.id_medecin,nom);
        try {
            patients.clear();

            while (res.next()) {
                patients.add(new Patient(res.getInt("id_patient"),
                        res.getString("nom"), res.getString("prenom"),
                        res.getString("diagno"), res.getInt("nmbr_seance"),
                        res.getString("nom_medecin"),
                        res.getString("prenom_medecin"), res.getString("date_visit")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        displayPatients();
    }
 ```
 
 ### In class Db : 
 
 is the same like getAllPatientByIdMedecin but we use ```WHERE``` to find only patients that have name like the name we pass it  =>``` WHERE tab2.nom LIKE '%" + nom + "%'   "```
 
 ```
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

 ```
 
 ### Event in the MedecinFrame :
 jTextField1KeyReleased => when the user type somthing in the search input:
 ```
  private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {                              
       searchPatientsWitheNom(jTextField1.getText());
    }   
 ```
jTextField1.getText() => get The value of the input

### Change password Button :
when u click the change password button you execute the event : change_password_btnActionPerformed

```
 int id_personne = db.getIdPersonneFromIdMedecin(id_medecin);
 NouveauMotDePasseFram nouveauMotDePasseFram = new NouveauMotDePasseFram();
 nouveauMotDePasseFram.setId_Personne(id_personne);
 nouveauMotDePasseFram.setVisible(true);
```