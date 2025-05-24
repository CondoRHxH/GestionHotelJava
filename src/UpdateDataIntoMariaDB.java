import java.sql.*;
import java.util.Scanner;

public class UpdateDataIntoMariaDB {

    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mariadb://localhost:3308/projethotel";  // database name
        String user = "root";  // username
        String password = "";  // password

        Scanner scanner = new Scanner(System.in);

        // Take input from the user
        System.out.print("Entrer Id d'activite de modifer: ");
        int id_activite = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Entrer nouvelle nom: ");
        String libelle = scanner.nextLine();
        
        System.out.print("Entrer nouvelle prix : ");
        String prix = scanner.nextLine();

        try {
            // Load the MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            // Establish the connection to the database
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MariaDB successfully!");

            // SQL query to update data
            String sql = "UPDATE activite SET libelle = ?, prix = ? WHERE id_activite = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the values for the placeholders
            stmt.setString(1, libelle);
            stmt.setString(2, prix);
            stmt.setInt(3,id_activite);  // Update based on the user's id

            // Execute the query
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Data updated successfully!");
            } else {
                System.out.println("❌ No matching record found to update!");
            }

            // Close connections
            stmt.close();
            conn.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
