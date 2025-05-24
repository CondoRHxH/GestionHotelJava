import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AjouterEmploye {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ajouter Employé");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Nom complet
        JLabel nomLabel = new JLabel("Nom complet:");
        nomLabel.setBounds(10, 30, 100, 25);
        frame.add(nomLabel);

        JTextField nomField = new JTextField();
        nomField.setBounds(120, 30, 240, 25);
        frame.add(nomField);

        // Rôle
        JLabel roleLabel = new JLabel("Rôle:");
        roleLabel.setBounds(10, 70, 100, 25);
        frame.add(roleLabel);

        JTextField roleField = new JTextField();
        roleField.setBounds(120, 70, 240, 25);
        frame.add(roleField);

        // Bouton ajouter
        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.setBounds(140, 130, 120, 30);
        frame.add(ajouterButton);

        // Action du bouton
        ajouterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomComplet = nomField.getText();
                String role = roleField.getText();

                String url = "jdbc:mariadb://localhost:3308/projethotel";
                String user = "root";
                String password = "";

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "INSERT INTO employe (nomEmploye, role) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nomComplet);
                    stmt.setString(2, role);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Employé ajouté avec succès !");
                        nomField.setText("");
                        roleField.setText("");
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
