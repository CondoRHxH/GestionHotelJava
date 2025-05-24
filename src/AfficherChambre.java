import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AfficherChambre {
	public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
          

            // SELECT query
            String sql = "SELECT * FROM chambre ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Display the results
            System.out.println("\n Lists des Chambres:");
            while (rs.next()) {
                int id_chambre = rs.getInt("id_chambre");
                String type = rs.getString("type");
                String prix = rs.getString("prix");
                String disponibilite = rs.getString("disponibilite");
                

                System.out.println("Id est : "+id_chambre);
                
                System.out.println(" Libelle est : "+type);
                
                System.out.println(" et Prix est : "+prix);
                
                System.out.println(" et Prix est : "+disponibilite);
                
                System.out.println("-----------------Autre Chambres -------------------");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
