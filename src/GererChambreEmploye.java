import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

public class GererChambreEmploye {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Assigner Chambres à un Employé");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel employeLabel = new JLabel("Employé:");
        employeLabel.setBounds(30, 30, 80, 25);
        frame.add(employeLabel);

        JLabel chambreLabel = new JLabel("Type Chambre:");
        chambreLabel.setBounds(30, 80, 100, 25);
        frame.add(chambreLabel);

        JComboBox<String> employeCombo = new JComboBox<>();
        employeCombo.setBounds(140, 30, 300, 25);
        frame.add(employeCombo);

        JComboBox<String> chambreCombo = new JComboBox<>();
        chambreCombo.setBounds(140, 80, 300, 25);
        frame.add(chambreCombo);

        JButton assignButton = new JButton("Assigner");
        assignButton.setBounds(180, 140, 120, 30);
        frame.add(assignButton);

        HashMap<String, Integer> employeMap = new HashMap<>();
        HashSet<String> chambreTypes = new HashSet<>();

        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Charger les employés
            PreparedStatement empStmt = conn.prepareStatement("SELECT id_employe, nomEmploye FROM employe");
            ResultSet empRs = empStmt.executeQuery();
            while (empRs.next()) {
                String nom = empRs.getString("nomEmploye");
                int id = empRs.getInt("id_employe");
                employeMap.put(nom, id);
                employeCombo.addItem(nom);
            }

            // Charger les types de chambres distincts
            PreparedStatement chambreStmt = conn.prepareStatement("SELECT DISTINCT type FROM chambre");
            ResultSet chambreRs = chambreStmt.executeQuery();
            while (chambreRs.next()) {
                String type = chambreRs.getString("type");
                chambreTypes.add(type);
                chambreCombo.addItem(type);
            }

            empRs.close();
            chambreRs.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erreur chargement données: " + ex.getMessage());
        }

        assignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEmp = (String) employeCombo.getSelectedItem();
                String selectedType = (String) chambreCombo.getSelectedItem();

                int idEmp = employeMap.get(selectedEmp);

                try {
                    Connection conn = DriverManager.getConnection(url, user, password);

                    // Trouver toutes les chambres de ce type
                    String chambreQuery = "SELECT id_chambre FROM chambre WHERE type = ?";
                    PreparedStatement stmt = conn.prepareStatement(chambreQuery);
                    stmt.setString(1, selectedType);
                    ResultSet rs = stmt.executeQuery();

                    int count = 0;
                    while (rs.next()) {
                        int idChambre = rs.getInt("id_chambre");

                        // Insertion dans gererchambre
                        String insert = "INSERT INTO gererchambre (id_employe, id_chambre) VALUES (?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insert);
                        insertStmt.setInt(1, idEmp);
                        insertStmt.setInt(2, idChambre);
                        insertStmt.executeUpdate();
                        insertStmt.close();

                        count++;
                    }

                    rs.close();
                    stmt.close();
                    conn.close();

                    if (count > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Employé assigné à " + count + " chambre(s) de type " + selectedType + " !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "⚠️ Aucune chambre de ce type trouvée.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
