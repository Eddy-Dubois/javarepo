package employee;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnection {

    public static Connection con = null ;
    public static String corpKey ;
    public static String name ;
    public static String tribe ;
    public static String email ;
    public static Personne personne = new Personne(corpKey, name, tribe, email) ;

    public MySqlConnection () {

        openDB();
        closeDB();

    }

    public static void openDB () {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver")  ;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "6513") ;

        } catch (Exception e) {
            System.out.println("error open : "+ e);
        }
    }

    public static void closeDB () {

        try {
            con.close();
        } catch (Exception e){
            System.out.println("error close : " + e);
        }
    }

    public static Personne searchEmployee (String corpKeyToSearch)  {

        try {

            openDB();

            personne.corpKey = null ;
            personne.name = null ;

            Statement stmt = con.createStatement();
            String requeteSearch = "select * from employeedetails WHERE CORPKEY = '"+corpKeyToSearch+"'" ;
            ResultSet rs = stmt.executeQuery(requeteSearch);

            while (rs.next()) {

                personne.corpKey = rs.getString(1) ;
                personne.name = rs.getString(2) ;
                personne.tribe = rs.getString(3) ;
                personne.email = rs.getString(4) ;

            }
            closeDB();

        } catch (Exception e) {
            System.out.println("error read : "+ e);
        }
        return new Personne (personne.corpKey, personne.name, personne.tribe, personne.email) ;
    }

    public static void readAllEmployee ()  {

        try {

            openDB();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from employeedetails ");

            while (rs.next()) {

                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
            closeDB();

        } catch (Exception e) {
            System.out.println("error read all : "+ e);
        }
    }

    public static void insertEmployee (String corpKey, String name, String tribe, String email)  {

        try {

            openDB();

            Statement stmt = con.createStatement();
            String requeteAdd = "INSERT INTO employeedetails VALUES ('"+corpKey+"', '"+name+"', '"+tribe+"','"+email+"')" ;
            stmt.executeUpdate(requeteAdd);

            closeDB();

        } catch (Exception e) {
            System.out.println("error write : "+ e);
        }
    }

    public static void main(String[] args) {

        new MySqlConnection() ;
        }

}

