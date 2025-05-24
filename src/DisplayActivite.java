import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DisplayActivite {
    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Liste des Activités");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // Table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Libelle");
        model.addColumn("Prix");

        // Connexion à la base de données
        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM activite";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Remplissage du tableau
            while (rs.next()) {
                int id = rs.getInt("id_activite");
                String libelle = rs.getString("libelle");
                String prix = rs.getString("prix");

                model.addRow(new Object[]{id, libelle, prix});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }

        // Table
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Affichage
        frame.setVisible(true);
    }
}
