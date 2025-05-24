import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AfficherEmployes {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Liste des Employés");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du tableau
        String[] columnNames = {"Nom Complet", "Rôle"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);

        // Connexion à la base de données
        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "SELECT nomEmploye, role FROM employe";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nomEmploye");
                String role = rs.getString("role");
                model.addRow(new Object[]{nom, role});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erreur: " + e.getMessage());
        }

        frame.setVisible(true);
    }
}
