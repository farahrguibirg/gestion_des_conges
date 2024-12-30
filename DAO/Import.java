package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import Model.*;

public class Import implements DataImportExport<Holiday> {
    private Connection conn;
    
    public Import() {
        this.conn = Connexion.getConnexion();
    }
    
    @Override
    public void importData(String filePath) {
        String checkQuery = "SELECT COUNT(*) FROM employe WHERE idEmploye = ?";
        String insertQuery = "INSERT INTO holiday (idEmploye, startDate, endDate, Typeh) VALUES (?, ?, ?, ?)";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
             
            String line = reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int idEmploye = Integer.parseInt(data[0].trim());
                    
                    // Vérifiez si l'idEmploye existe dans la table employe
                    checkStmt.setInt(1, idEmploye);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            insertStmt.setInt(1, idEmploye);
                            insertStmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(data[1].trim())));
                            insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.parse(data[2].trim())));
                            insertStmt.setString(4, data[3].trim());
                            insertStmt.addBatch();
                        } else {
                            System.err.println("idEmploye inexistant : " + idEmploye);
                        }
                    }
                } else {
                    System.err.println("Format de ligne invalide : " + line);
                }
            }
            
            insertStmt.executeBatch();
            System.out.println("Congés importés avec succès.");
            
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Erreur lors de l'importation des données : " + e.getMessage(), e);
        }
    }
    @Override
    public void exportData(String fileName, List<Holiday> holidays) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write header
            writer.write("idEmploye,startDate,endDate,Typeh");
            writer.newLine();
            
            // Write data
            for (Holiday holiday : holidays) {
                String line = String.format("%d,%s,%s,%s",
                    holiday.getIdEmployee(),
                    holiday.getStartDate(),
                    holiday.getEndDate(),
                    holiday.getConge()
                );
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Données exportées avec succès vers " + fileName);
        }
    }
}