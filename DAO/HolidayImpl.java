package DAO;

import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.*;
public class HolidayImpl implements GenericDAO<Holiday> {
    private String query = null;

    @Override
    public void update(Holiday holiday) {
        query = "UPDATE holiday SET startDate = ?, endDate = ?, Typeh = ?, idEmploye = ? WHERE id = ?";
        try (PreparedStatement stm = Connexion.getConnexion().prepareStatement(query)) {
            stm.setString(1, holiday.getStartDate().toString());
            stm.setString(2, holiday.getEndDate().toString());
            stm.setString(3, holiday.getConge().name());
            stm.setInt(4, holiday.getIdEmployee());
            stm.setInt(5, holiday.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise � jour du cong� : " + e.getMessage());
        }
    }

    @Override
    public List<Holiday> getAll() {
        query = "SELECT * FROM holiday";
        List<Holiday> holidays = new ArrayList<>();
        try (Statement st = Connexion.getConnexion().createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                holidays.add(new Holiday(
                    rs.getInt("id"),
                    rs.getInt("idEmploye"),
                    LocalDate.parse(rs.getString("startDate")),
                    LocalDate.parse(rs.getString("endDate")),
                    Typeh.valueOf(rs.getString("Typeh"))
                ));
                
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la r�cup�ration des cong�s : " + e.getMessage());
        }
        return holidays;
    }

    @Override
    public void delete(int id) {
        query = "DELETE FROM holiday WHERE id = ?";
        try (PreparedStatement st = Connexion.getConnexion().prepareStatement(query)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du cong� : " + e.getMessage());
        }
    }

    @Override
    public void add(Holiday holiday) {
        query = "INSERT INTO holiday (idEmploye, startDate, endDate, Typeh) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = Connexion.getConnexion().prepareStatement(query)) {
            stm.setInt(1, holiday.getIdEmployee());
            stm.setString(2, holiday.getStartDate().toString());
            stm.setString(3, holiday.getEndDate().toString());
            stm.setString(4, holiday.getConge().name());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du cong� : " + e.getMessage());
        }
    }

    @Override
    public Holiday findById(int id) {
        query = "SELECT * FROM holiday WHERE id = ?";
        Holiday holiday = null;
        try (PreparedStatement st = Connexion.getConnexion().prepareStatement(query)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    holiday = new Holiday(
                        rs.getInt("id"),
                        rs.getInt("idEmploye"),
                        LocalDate.parse(rs.getString("startDate")),
                        LocalDate.parse(rs.getString("endDate")),
                        Typeh.valueOf(rs.getString("Typeh"))
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la r�cup�ration du cong� par ID : " + e.getMessage());
        }
        return holiday;
    }
}
