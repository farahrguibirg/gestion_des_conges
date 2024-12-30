package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    public static final String url = "jdbc:mysql://localhost:3306/gestion_des_employees";
    public static final String user = "root";
    public static final String password = "";
    private static Connection conn = null;

    public static Connection getConnexion() {
        if (conn == null) { 
            try {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion etablie avec succes !");
            } catch (SQLException e) {
                System.out.println("Erreur de connexion !!!!!");
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnexion() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; 
                System.out.println("Connexion ferme avec succes !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion !!!!!");
                e.printStackTrace();
            }
        }
    }
}