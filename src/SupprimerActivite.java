import java.sql.*;
import java.util.Scanner;

public class SupprimerActivite {
	public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        Scanner scanner = new Scanner(System.in);

        // Ask for the ID to delete
        System.out.print("Entrer Id de service que voulez supprimer: ");
        int id_activite = scanner.nextInt();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MariaDB successfully!");

            // SQL DELETE statement
            String sql = "DELETE FROM activite WHERE id_activite = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_activite);

            // Execute deletion
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ User deleted successfully!");
            } else {
                System.out.println("❌ No user found with this ID!");
            }

            stmt.close();
            conn.close();
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
