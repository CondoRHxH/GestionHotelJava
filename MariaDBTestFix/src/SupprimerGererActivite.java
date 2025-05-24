import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;

public class SupprimerGererActivite {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Supprimer gestion activité");
        frame.setSize(500, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel employeLabel = new JLabel("Employé:");
        employeLabel.setBounds(30, 30, 80, 25);
        frame.add(employeLabel);

        JLabel activiteLabel = new JLabel("Activité:");
        activiteLabel.setBounds(30, 80, 80, 25);
        frame.add(activiteLabel);

        JComboBox<String> employeCombo = new JComboBox<>();
        employeCombo.setBounds(120, 30, 300, 25);
        frame.add(employeCombo);

        JComboBox<String> activiteCombo = new JComboBox<>();
        activiteCombo.setBounds(120, 80, 300, 25);
        frame.add(activiteCombo);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBounds(180, 130, 120, 30);
        frame.add(deleteButton);

        HashMap<String, Integer> employeMap = new HashMap<>();
        HashMap<String, Integer> activiteMap = new HashMap<>();

        String url = "jdbc:mariadb://localhost:3308/projethotel";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Load employés
            PreparedStatement empStmt = conn.prepareStatement("SELECT id_employe, nomEmploye FROM employe");
            ResultSet empRs = empStmt.executeQuery();
            while (empRs.next()) {
                String nom = empRs.getString("nomEmploye");
                int id = empRs.getInt("id_employe");
                employeMap.put(nom, id);
                employeCombo.addItem(nom);
            }

            // Load activités
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

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEmp = (String) employeCombo.getSelectedItem();
                String selectedAct = (String) activiteCombo.getSelectedItem();

                int idEmp = employeMap.get(selectedEmp);
                int idAct = activiteMap.get(selectedAct);

                try {
                    Connection conn = DriverManager.getConnection(url, user, password);
                    String sql = "DELETE FROM gereractivite WHERE id_employe = ? AND id_activite = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, idEmp);
                    stmt.setInt(2, idAct);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Activité désassignée !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Aucune correspondance trouvée.");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur suppression: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
