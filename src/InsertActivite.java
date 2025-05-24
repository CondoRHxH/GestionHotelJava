import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class InsertActivite {
    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Ajouter Activité");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Libelle
        JLabel libelleLabel = new JLabel("Libelle:");
        libelleLabel.setBounds(10, 20, 80, 25);
        frame.add(libelleLabel);

        JTextField libelleField = new JTextField();
        libelleField.setBounds(100, 20, 200, 25);
        frame.add(libelleField);

        // Prix
        JLabel prixLabel = new JLabel("Prix:");
        prixLabel.setBounds(10, 60, 80, 25);
        frame.add(prixLabel);

        JTextField prixField = new JTextField();
        prixField.setBounds(100, 60, 200, 25);
        frame.add(prixField);

        // Bouton
        JButton insertButton = new JButton("Ajouter");
        insertButton.setBounds(100, 100, 100, 30);
        frame.add(insertButton);

        // Action du bouton
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String libelle = libelleField.getText();
                String prix = prixField.getText();

                String url = "jdbc:mariadb://localhost:3308/projethotel";
                String user = "root";
                String password = "";

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "INSERT INTO activite (libelle, prix) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, libelle);
                    stmt.setString(2, prix);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Activité ajoutée !");
                        libelleField.setText("");
                        prixField.setText("");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur: " + ex.getMessage());
                }
            }
        });

        // Affichage
        frame.setVisible(true);
    }
}
