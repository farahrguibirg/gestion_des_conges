package Model;

import DAO.*;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HolidayModel {
    private HolidayImpl DAOh;
    private EmployeImpl DAOe;
    private Import da;

    public HolidayModel(HolidayImpl DAOh, EmployeImpl DAOe,Import da) {
        this.DAOh = DAOh;
        this.DAOe = DAOe;
        this.da = da;
    }

    public boolean addh(int holidayId, int idEmployee, LocalDate startDate, LocalDate endDate, Typeh conge, boolean isUpdate) {
        Employe emp = DAOe.findById(idEmployee);
        if (emp == null) {
            System.err.println("Erreur : Employ� introuvable.");
            return false;
        }

        int day = calculateDaysBetween(startDate, endDate);
        if (day > 25 || emp.getSolde() < day) {
            System.err.println("Erreur : Solde insuffisant ou dur�e trop longue.");
            return false;
        }

        if (isUpdate) {
            Holiday existingHoliday = DAOh.findById(holidayId);
            if (existingHoliday == null) {
                System.err.println("Erreur : Cong� introuvable.");
                return false;
            }

            int previousDuration = calculateDaysBetween(existingHoliday.getStartDate(), existingHoliday.getEndDate());
            emp.setSolde(emp.getSolde() + previousDuration);
        }

        int newSolde = emp.getSolde() - day;

        // V�rification du solde avant de le mettre � jour
        if (newSolde <= 0) {
            System.err.println("Erreur : Le solde de l'employ� ne peut pas �tre �gal ou inf�rieur � z�ro.");
            return false;
        }

        emp.setSolde(newSolde);
        DAOe.update(emp);

        Holiday holiday = new Holiday(holidayId, idEmployee, startDate, endDate, conge);
        if (isUpdate) {
            DAOh.update(holiday);
        } else {
            DAOh.add(holiday);
        }
        return true;
    }

    public boolean deleteh(int holidayId) {
        Holiday holiday = DAOh.findById(holidayId);
        if (holiday == null) {
            System.err.println("Erreur : Cong� introuvable.");
            return false;
        }

        Employe emp = DAOe.findById(holiday.getIdEmployee());
        if (emp != null) {
            int dayDiff = calculateDaysBetween(holiday.getStartDate(), holiday.getEndDate());
            emp.setSolde(emp.getSolde() + dayDiff);
            DAOe.update(emp);
        }

        DAOh.delete(holidayId);
        return true;
    }

    public List<Object[]> getHolidays() {
        List<Object[]> data = new ArrayList<>();
        for (Holiday holiday : DAOh.getAll()) {
            Employe emp = DAOe.findById(holiday.getIdEmployee());
            String employeeName = (emp != null) ? emp.getNom() + " " + emp.getPrenom() : "Inconnu";
            data.add(new Object[] {
                holiday.getId(),
                employeeName,
                holiday.getStartDate(),
                holiday.getEndDate(),
                holiday.getConge()
            });
        }
        return data;
    }

    private int calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
private boolean checkFileExits(File file) {
		
		if(!file.exists()) {
			throw new IllegalArgumentException ("le fichier n'existe pas "+file.getPath());
			
		}
		return true;
		
	}
private boolean checkIsFile(File file) {
	
	if(!file.isFile()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas un fichier "+file.getPath());
		
	}
	return true;
	
}
private boolean checkIsReadebal(File file) {
	
	if(!file.canRead()) {
		throw new IllegalArgumentException ("le chemin specifie nest pas lisibles "+file.getPath());
		
	}
	return true;
	
}
//M�thode pour importer des donn�es � partir d'un fichier
public void importData(String filePath) {
 try {
     File file = new File(filePath);

     // V�rifications des conditions
     checkFileExits(file);
     checkIsFile(file);
     checkIsReadebal(file);

     // Logique d'importation
     da.importData(filePath);
     System.out.println("Donn�es import�es avec succ�s !");
 } catch (IllegalArgumentException e) {
     System.err.println("Erreur lors de l'importation : " + e.getMessage());
 } catch (Exception e) {
     System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
 }
}
public List<Holiday> getAllHolidays() {
    return DAOh.getAll(); // Retourne toutes les donn�es depuis la DAO
}

//M�thode pour exporter des donn�es dans un fichier
public void exportData(String filePath, List<Holiday> data) {
 try {
     if (data == null || data.isEmpty()) {
         throw new IllegalArgumentException("Aucune donn�e � exporter !");
     }

     File file = new File(filePath);

     // V�rification du fichier
     if (!file.getName().endsWith(".txt") && !file.getName().endsWith(".csv")) {
         throw new IllegalArgumentException("Le fichier doit avoir une extension .txt ou .csv !");
     }

     // Logique d'exportation
     da.exportData(filePath, data);
     System.out.println("Donn�es export�es avec succ�s !");
 } catch (IllegalArgumentException e) {
     System.err.println("Erreur lors de l'exportation : " + e.getMessage());
 } catch (Exception e) {
     System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
 }
}
}
