package Model;

import DAO.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmployeModel {
    private EmployeImpl dao;
    private ImportData da;

    public EmployeModel(EmployeImpl dao,ImportData da) {
    	this.da = da;
    	this.dao = dao;
    }

    // M�thode pour ajouter un employ�
    public boolean addEmploye(String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        if (salaire <= 0) {
            System.out.println("Le salaire doit �tre sup�rieur � 0 !");
            return false;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("L'email n'est pas valide !");
            return false;
        }
        Employe nvEmploye = new Employe(0, nom, prenom, email, telephone, salaire, role, poste);
        dao.add(nvEmploye);
        return true;
    }

    // M�thode pour modifier un employ� sans g�rer son solde
    public boolean updateEmploye(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {
        Employe emp = new Employe(id, nom, prenom, email, telephone, salaire, role, poste);
        dao.update(emp);  // Mise � jour de l'employ� dans la base de donn�es sans toucher au solde
        return true;
    }

    // M�thode pour supprimer un employ�
    public boolean deleteEmploye(int id) {
        dao.delete(id);
        return true;
    }

    // M�thode pour r�cup�rer tous les employ�s
    public List<Object[]> getAllElements() {
        List<Object[]> data = new ArrayList<>();
        try {
            List<Employe> employees = dao.getAll();
            for (Employe element : employees) {
                Object[] row = {
                    element.getId(),
                    element.getNom(),
                    element.getPrenom(),
                    element.getTelephone(),
                    element.getEmail(),
                    element.getSalaire(),
                    element.getRole(),
                    element.getPoste(),
                    element.getSolde(),  // Conserver l'affichage du solde dans le tableau, mais ne pas le modifier
                };
                data.add(row);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
        }
        return data;
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

//M�thode pour exporter des donn�es dans un fichier
public void exportData(String filePath, List<Employe> data) {
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
