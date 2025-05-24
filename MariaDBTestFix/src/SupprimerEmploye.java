import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SupprimerEmploye {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Supprimer Employé par ID");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Champ ID
        JLabel idLabel = new JLabel("ID Employé:");
        idLabel.setBounds(20, 30, 100, 25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(120, 30, 180, 25);
        frame.add(idField);

        // Bouton Supprimer
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBounds(110, 80, 120, 30);
        frame.add(deleteButton);

        // Action du bouton
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idEmploye;

                try {
                    idEmploye = Integer.parseInt(idField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "❌ ID invalide !");
                    return;
                }

                String url = "jdbc:mariadb://localhost:3308/projethotel";
                String user = "root";
                String password = "";

                try {
                    Class.forName("org.mariadb.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);

                    String sql = "DELETE FROM employe WHERE id_employe = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, idEmploye);

                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Employé supprimé avec succès !");
                        idField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Aucun employé trouvé avec cet ID.");
                    }

                    stmt.close();
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}
