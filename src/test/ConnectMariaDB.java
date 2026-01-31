package test;

import java.sql.*;

public class ConnectMariaDB {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3308/testjava"; // smiya dyal base
        String user = "root";
        String password = ""; // password dyalk

        try {
            // load driver
            Class.forName("org.mariadb.jdbc.Driver");

            // connect
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Tsiftna mzyan l MariaDB!");

            // query simple
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                System.out.println("Nom: " + rs.getString("name") + ", Email: " + rs.getString("email"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("⛔ Error: " + e.getMessage());
        }
    }
}
