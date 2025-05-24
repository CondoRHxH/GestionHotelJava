import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class InsereChambre {
    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Ajouter Une Nouvelle Chambre");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Label & Field: Type
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(10, 20, 80, 25);
        frame.add(typeLabel);

        JTextField typeField = new JTextField();
        typeField.setBounds(120, 20, 200, 25);
        frame.add(typeField);

        // Label & Field: Prix
        JLabel prixLabel = new JLabel("Prix:");
        prixLabel.setBounds(10, 60, 80, 25);
        frame.add(prixLabel);

        JTextField prixField = new JTextField();
        prixField.setBounds(120, 60, 200, 25);
        frame.add(prixField);

        // Label & Field: Disponibilité
        JLabel dispoLabel = new JLabel("Disponibilité:");
        dispoLabel.setBounds(10, 100, 100, 25);
        frame.add(dispoLabel);

        JTextField dispoField = new JTextField();
        dispoField.setBounds(120, 100, 200, 25);
        frame.add(dispoField);

        // Bouton Ajouter
        JButton insertButton = new JButton("Ajouter");
        insertButton.setBounds(120, 150, 100, 30);
        frame.add(insertButton);

        // Action bouton
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = typeField.getText();
                String prix = prixField.getText();
                String disponibilite = dispoField.getText();

                String url = "jdbc:mariadb://localhost:3308/projethotel";
                String user = "root";
                String password = "";

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "INSERT INTO chambre (type, prix, disponibilite) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, type);
                    stmt.setString(2, prix);
                    stmt.setString(3, disponibilite);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Chambre ajoutée avec succès !");
                        typeField.setText("");
                        prixField.setText("");
                        dispoField.setText("");
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
