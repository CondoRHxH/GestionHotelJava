import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ModifierChambre {
	public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mariadb://localhost:3308/projethotel";  // database name
        String user = "root";  // username
        String password = "";  // password

        Scanner scanner = new Scanner(System.in);

        // Take input from the user
        System.out.print("Entrer Id du chambre de modifer: ");
        int id_chambre = scanner.nextInt();
        scanner.nextLine();  
        
        System.out.print("Enter Type : ");
        String type = scanner.nextLine();

        System.out.print("Enter prix : ");
        String prix = scanner.nextLine();
        
        System.out.print("Enter Disponibilte : ");
        String disponibilte = scanner.nextLine();

        try {
            // Load the MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            // Establish the connection to the database
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MariaDB successfully!");

            // SQL query to update data
            String sql = "UPDATE chambre SET  type = ?, prix = ?, disponibilite = ? WHERE id_chambre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set the values for the placeholders
            stmt.setString(1, type);
            stmt.setString(2, prix);
            stmt.setString(3, disponibilte);
            stmt.setInt(4,id_chambre);  // Update based on the user's id

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
