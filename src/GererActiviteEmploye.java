import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;

public class GererActiviteEmploye {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Assigner Activité à un Employé");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Labels
        JLabel employeLabel = new JLabel("Employe:");
        employeLabel.setBounds(30, 30, 80, 25);
        frame.add(employeLabel);

        JLabel activiteLabel = new JLabel("Activite:");
        activiteLabel.setBounds(30, 80, 80, 25);
        frame.add(activiteLabel);

        // ComboBoxes
        JComboBox<String> employeCombo = new JComboBox<>();
        employeCombo.setBounds(120, 30, 300, 25);
        frame.add(employeCombo);

        JComboBox<String> activiteCombo = new JComboBox<>();
        activiteCombo.setBounds(120, 80, 300, 25);
        frame.add(activiteCombo);

        JButton assignButton = new JButton("Assigner");
        assignButton.setBounds(180, 140, 120, 30);
        frame.add(assignButton);

        // Maps pour relier noms aux IDs
        HashMap<String, Integer> employeMap = new HashMap<>();
        HashMap<String, Integer> activiteMap = new HashMap<>();

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

            // Charger les activités
            PreparedStatement actStmt = conn.prepareStatement("SELECT id_activite, libelle FROM activite");
            ResultSet actRs = actStmt.executeQuery();
            while (actRs.next()) {
                String libelle = actRs.getString("libelle");
                int id = actRs.getInt("id_activite");
                activiteMap.put(libelle, id);
                activiteCombo.addItem(libelle);
            }

            empRs.close();
            actRs.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erreur chargement données: " + ex.getMessage());
        }

        // Bouton
        assignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEmp = (String) employeCombo.getSelectedItem();
                String selectedAct = (String) activiteCombo.getSelectedItem();

                int idEmp = employeMap.get(selectedEmp);
                int idAct = activiteMap.get(selectedAct);

                try {
                    Connection conn = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO gereractivite (id_employe, id_activite) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, idEmp);
                    stmt.setInt(2, idAct);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Activité assignée avec succès !");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur insertion: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
