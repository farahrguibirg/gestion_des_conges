package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Employe;
import Model.*;

public class EmployeImpl implements GenericDAO<Employe> {
    private Connection conn;

    public EmployeImpl() {
        this.conn = Connexion.getConnexion();
    }

    @Override
    public void add(Employe E) {
        String query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, E.getNom());
            stmt.setString(2, E.getPrenom());
            stmt.setString(3, E.getEmail());
            stmt.setString(4, E.getTelephone());
            stmt.setDouble(5, E.getSalaire());
            stmt.setString(6, E.getRole().name());
            stmt.setString(7, E.getPoste().name());
            stmt.executeUpdate();
            System.out.println("Employ� ajout� avec succ�s !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'employ� : " + e.getMessage());
        }
    }

    @Override
    public Employe findById(int employeId) {
        String query = "SELECT * FROM Employe WHERE idEmploye = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, employeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employe(
                        rs.getInt("idEmploye"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getDouble("salaire"),
                        Role.valueOf(rs.getString("role")),
                        Poste.valueOf(rs.getString("poste"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'employ� par ID : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT * FROM Employe";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employes.add(new Employe(
                    rs.getInt("idEmploye"),                
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getDouble("salaire"),
                   Role.valueOf(rs.getString("role")),
                    Poste.valueOf(rs.getString("poste"))
                ));
                rs.getInt("Solde");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la r�cup�ration de tous les employ�s : " + e.getMessage());
        }
        return employes;
    }


    @Override
    public void update(Employe emp) {
        String sql = emp.getSolde() == 25 
            ? "UPDATE employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ? WHERE idEmploye = ?"
            : "UPDATE employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ?, solde = ? WHERE idEmploye = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, emp.getNom());
            st.setString(2, emp.getPrenom());
            st.setString(3, emp.getEmail());
            st.setString(4, emp.getTelephone());
            st.setDouble(5, emp.getSalaire());
            st.setString(6, emp.getRole().name());
            st.setString(7, emp.getPoste().name());
            if (emp.getSolde() != 25) {
                st.setInt(8, emp.getSolde());
                st.setInt(9, emp.getId());
            } else {
                st.setInt(8, emp.getId());
            }
            st.executeUpdate();
            System.out.println("Employee updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Employe WHERE idEmploye = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Employ� supprim� avec succ�s !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'employ� : " + e.getMessage());
        }
    }
}
