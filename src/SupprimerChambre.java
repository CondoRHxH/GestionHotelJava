import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class SupprimerChambre {
	public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        Scanner scanner = new Scanner(System.in);

        // Ask for the ID to delete
        System.out.print("Entrer Id du chambre que voulez supprimer: ");
        int id_chambre = scanner.nextInt();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MariaDB successfully!");

            // SQL DELETE statement
            String sql = "DELETE FROM chambre WHERE id_chambre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_chambre);

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
