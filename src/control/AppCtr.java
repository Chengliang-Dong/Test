/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import static java.lang.Class.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.FileReaderWriter;

/**
 *
 * @author dongcl
 */
public class AppCtr {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException,IOException,ParseException, SQLException {
        jdbcToJSON();
    }
    public static void jdbcToJSON() throws ClassNotFoundException, SQLException{
                Class.forName("oracle.jdbc.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@144.217.163.57:1521:XE",
                "hr","inf5180");
        Statement stm = con.createStatement();
        String sql = "select e.employee_id,e.first_name,e.last_name " 
                + ",d.department_name,j.job_title from "
                + "employees e join departments d on e.department_id=d.department_id " 
                + "join jobs j on j.job_id = e.job_id";
        ResultSet rs = stm.executeQuery(sql);
        int id;
        String prenom,nom,titre,department;
        JSONArray liste = new JSONArray();
        JSONObject employe = new JSONObject();
        while(rs.next()){
            id= rs.getInt(1);
            prenom = rs.getString(2);
            nom = rs.getString(3);
            department = rs.getString(4);
            titre = rs.getString(5);
            System.out.println(id + " " + prenom + " " + nom + " " + department + " " + titre);
            employe.accumulate("id", id);
            employe.accumulate("prenom", prenom);
            employe.accumulate("nom", nom);
            employe.accumulate("department", department);
            employe.accumulate("titre", titre);
            liste.add(employe);
            employe.clear();
        }
        FileReaderWriter.saveStringInfoToFile("file/employes.json", liste.toString());
    }
    public static void readWriteJSON() throws ParseException{
                String myJSON = FileReaderWriter.loadFileIntoString("file/student.json");
        JSONObject student = JSONObject.fromObject(myJSON);
        int id = student.getInt("student_id");
        String prenom = student.getString("first_name");
        String nom = student.getString("last_name");
        String dateNaissance = student.getString("date_birth");
        JSONArray resultats = student.getJSONArray("results");
        JSONObject simpleResultat;
        double note;
        double somme = 0;
        for (int i = 0; i < resultats.size(); i++) {
            simpleResultat = resultats.getJSONObject(i);
            note = simpleResultat.getDouble("mark");
            somme += note;
            System.out.println(note + "----");
        }
        System.out.println(somme + "");
        double moyenne = somme / resultats.size();
        System.out.println(moyenne + "");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = formatDate.parse(dateNaissance);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int annee = cal.get(Calendar.YEAR);
        System.out.println(annee + "");

        JSONObject sortie = new JSONObject();
        sortie.accumulate("id", id);
        sortie.accumulate("prenom", prenom);
        sortie.accumulate("nom", nom);
        sortie.accumulate("annee", annee);
        sortie.accumulate("nbrCours", resultats.size());
        sortie.accumulate("moyyen", moyenne);
        FileReaderWriter.saveStringInfoToFile("file/sortie.json", sortie.toString());
        //System.out.println("" + myJSON);
        //writeJSONToFile();
        
    }

    public static void writeJSONToFile() {
        JSONObject student = new JSONObject();
        student.accumulate("id", 123);
        student.accumulate("nom", "Zaier");
        student.accumulate("prenom", "Zied");
        student.accumulate("moyenne", 73.44);
        student.accumulate("actif", true);

        JSONObject adresse = new JSONObject();
        adresse.accumulate("numero", 80);
        adresse.accumulate("rue", "BDEB");
        adresse.accumulate("codePostal", "H3S-1K9");
        student.accumulate("adresse", adresse);

        JSONArray listeCours = new JSONArray();
        JSONObject cours = new JSONObject();
        cours.accumulate("sigle", "A18");
        cours.accumulate("titre", "Jenkins");
        cours.accumulate("resultat", 80.88);

        listeCours.add(cours);
        //cours = new JSONObject();
        cours.clear();
        cours.accumulate("sigle", "A10");
        cours.accumulate("titre", "BD insterface");
        cours.accumulate("resultat", 50.88);
        listeCours.add(cours);

        student.accumulate("listeCours", listeCours);
        FileReaderWriter.saveStringInfoToFile("file/etudiant.json", student.toString());
//System.out.println(student);

    }

    public static void readJSONFromFile() {
        // TODO code application logic here
        String myJSON = FileReaderWriter.loadFileIntoString("file/catalog.json");
        JSONArray catalogue = JSONArray.fromObject(myJSON);
        JSONObject singleItem;
        int id, year;
        double price;
        String title, author;
        boolean available;
        for (int i = 0; i < catalogue.size(); i++) {
            singleItem = catalogue.getJSONObject(i);
            available = singleItem.getBoolean("available");
            year = singleItem.getInt("year");
            if (year > 2009 && available) {
                id = singleItem.getInt("id");
                title = singleItem.getString("title");
                author = singleItem.getString("author");
                price = singleItem.getDouble("price");
                System.out.println(id + " - " + title + " - " + author + " - " + available + " - " + year + " - " + price);

            }
        }
    }

}
