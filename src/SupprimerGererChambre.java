import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

public class SupprimerGererChambre{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Supprimer Gestion Chambres");
        frame.setSize(500, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //fenetre	
        frame.setLayout(null);

        JLabel employeLabel = new JLabel("Employé:");
        employeLabel.setBounds(30, 30, 80, 25); //les taiulles,ou les dimensions
        frame.add(employeLabel);

        JLabel chambreLabel = new JLabel("Type Chambre:");  // inputs
        chambreLabel.setBounds(30, 80, 100, 25);
        frame.add(chambreLabel);

        JComboBox<String> employeCombo = new JComboBox<>(); // les menus deroualntes
        employeCombo.setBounds(140, 30, 300, 25);
        frame.add(employeCombo);

        JComboBox<String> chambreCombo = new JComboBox<>();
        chambreCombo.setBounds(140, 80, 300, 25);
        frame.add(chambreCombo);

        JButton deleteButton = new JButton("Supprimer Gestion");
        deleteButton.setBounds(160, 140, 180, 30);
        frame.add(deleteButton);

        HashMap<String, Integer> employeMap = new HashMap<>();
        HashSet<String> chambreTypes = new HashSet<>();

        String url = "jdbc:mariadb://localhost:3308/projethotel";  //java standr api driver database 
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Charger employés
            PreparedStatement empStmt = conn.prepareStatement("SELECT id_employe, nomEmploye FROM employe");
            ResultSet empRs = empStmt.executeQuery();
            while (empRs.next()) {
                String nom = empRs.getString("nomEmploye");
                int id = empRs.getInt("id_employe");
                employeMap.put(nom, id);  //map pour chercher
                employeCombo.addItem(nom);
            }

            // Charger types de chambres distincts
            PreparedStatement chambreStmt = conn.prepareStatement("SELECT DISTINCT type FROM chambre"); // preparation dyal requetes baxh prener les info dans un menu
            ResultSet chambreRs = chambreStmt.executeQuery();
            while (chambreRs.next()) {
                String type = chambreRs.getString("type");  //ca pour les types
                chambreTypes.add(type);
                chambreCombo.addItem(type);
            }

            empRs.close(); //pour arreter lae perd des ressources, fermeture du requete et cnx avec la bd
            chambreRs.close();
            conn.close();

        } catch (Exception ex) {  // gestio d'erreur
            JOptionPane.showMessageDialog(frame, "Erreur chargement données: " + ex.getMessage());
        }

        // Action bouton "Supprimer"
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedEmp = (String) employeCombo.getSelectedItem();
                String selectedType = (String) chambreCombo.getSelectedItem();
                int idEmp = employeMap.get(selectedEmp);

                try {
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String deleteQuery = "DELETE FROM gererchambre WHERE id_employe = ? AND id_chambre IN (SELECT id_chambre FROM chambre WHERE type = ?)"; //suppretion
                    PreparedStatement stmt = conn.prepareStatement(deleteQuery);
                    stmt.setInt(1, idEmp);
                    stmt.setString(2, selectedType);

                    int deleted = stmt.executeUpdate();
                    stmt.close();
                    conn.close();

                    if (deleted > 0) {
                        JOptionPane.showMessageDialog(frame, "" + deleted + " chambre(s) de type '" + selectedType + "' supprimées de la gestion."); //DesMessages
                    } else {
                        JOptionPane.showMessageDialog(frame, " Cet employé ne gère aucune chambre de ce type.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur suppression: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
