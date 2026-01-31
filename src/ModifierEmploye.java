import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ModifierEmploye {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Modifier Employé");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // ID
        JLabel idLabel = new JLabel("ID Employé:");
        idLabel.setBounds(10, 20, 100, 25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120, 20, 150, 25);
        frame.add(idField);

        JButton chercherButton = new JButton("Rechercher");
        chercherButton.setBounds(280, 20, 100, 25);
        frame.add(chercherButton);

        // Nom complet
        JLabel nomLabel = new JLabel("Nom complet:");
        nomLabel.setBounds(10, 70, 100, 25);
        frame.add(nomLabel);

        JTextField nomField = new JTextField();
        nomField.setBounds(120, 70, 240, 25);
        frame.add(nomField);

        // Rôle
        JLabel roleLabel = new JLabel("Rôle:");
        roleLabel.setBounds(10, 110, 100, 25);
        frame.add(roleLabel);

        JTextField roleField = new JTextField();
        roleField.setBounds(120, 110, 240, 25);
        frame.add(roleField);

        // Bouton modifier
        JButton modifierButton = new JButton("💾 Modifier");
        modifierButton.setBounds(140, 170, 120, 30);
        frame.add(modifierButton);

        // Action bouton chercher
        chercherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = idField.getText();
                try {
                    int id = Integer.parseInt(idStr);
                    String url = "jdbc:mariadb://localhost:3308/projethotel";
                    String user = "root";
                    String password = "";

                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "SELECT nomEmploye, role FROM employe WHERE id_employe = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        nomField.setText(rs.getString("nomEmploye"));
                        roleField.setText(rs.getString("role"));
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Aucun employé trouvé.");
                        nomField.setText("");
                        roleField.setText("");
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(frame, "⚠️ ID invalide.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage());
                }
            }
        });

        // Action bouton modifier
        modifierButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idStr = idField.getText();
                String nomComplet = nomField.getText();
                String role = roleField.getText();

                try {
                    int id = Integer.parseInt(idStr);
                    String url = "jdbc:mariadb://localhost:3308/projethotel";
                    String user = "root";
                    String password = "";

                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "UPDATE employe SET nomEmploye = ?, role = ? WHERE id_employe = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nomComplet);
                    stmt.setString(2, role);
                    stmt.setInt(3, id);

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Employé modifié avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Échec de la modification.");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
