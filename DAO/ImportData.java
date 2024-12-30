package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Model.Employe;

public class ImportData implements DataImportExport<Employe> {
    private Connection conn;

    public ImportData() {
        this.conn = Connexion.getConnexion();
    }

    @Override
    public void importData(String filePath) {
        String query = "INSERT INTO employe(nom, prenom, email, telephone, salaire, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            String line = reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // Vérifier que la ligne a le bon nombre de colonnes
                if (data.length == 7) {
                    // Traitement des données
                    ps.setString(1, data[0].trim()); // nom
                    ps.setString(2, data[1].trim()); // prenom
                    ps.setString(3, data[2].trim()); // email
                    ps.setString(4, data[3].trim()); // telephone
                    
                    // Vérification et validation du salaire avec BigDecimal
                    try {
                        BigDecimal salaire = new BigDecimal(data[4].trim());
                        // Assurez-vous que le salaire est dans un intervalle acceptable
                        // Par exemple, vérifier que le salaire ne dépasse pas la capacité de la base de données
                        if (salaire.compareTo(BigDecimal.ZERO) < 0 || salaire.compareTo(new BigDecimal("99999999.99")) > 0) {
                            System.err.println("Salaire invalide : " + salaire);
                        } else {
                            ps.setBigDecimal(5, salaire); // Utilisation de BigDecimal pour insérer le salaire
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Format de salaire invalide : " + data[4].trim());
                    }
                    
                    ps.setString(6, data[5].trim()); // role
                    ps.setString(7, data[6].trim()); // poste
                    
                    // Ajouter à la batch
                    ps.addBatch();
                } else {
                    System.err.println("Format de ligne invalide : " + line);
                }
            }

            // Exécuter le batch d'insertion
            ps.executeBatch();
            System.out.println("Employés importés avec succès.");
            
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportData(String fileName, List<Employe> data) throws IOException {
        // Cette méthode reste inchangée pour l'exportation
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("nom,prenom,email,telephone,salaire,role,poste");
            writer.newLine();
            for (Employe employee : data) {
                String line = String.format("%s,%s,%s,%s,%.2f,%s,%s",
                        employee.getNom(),
                        employee.getPrenom(),
                        employee.getEmail(),
                        employee.getTelephone(),
                        employee.getSalaire(),
                        employee.getRole(),
                        employee.getPoste());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Données exportées avec succès.");
        }
    }
}
