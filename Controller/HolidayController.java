package Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.*;
import View.HolidayView;

import java.util.ArrayList;
import java.util.List;

public class HolidayController {
    private HolidayView view;
    private HolidayModel model;

    public HolidayController(HolidayView view, HolidayModel model) {
        this.view = view;
        this.model = model;
        initializeListeners();
        refreshDisplay();
    }
    public void handleExport(String fileName, List<Holiday> holidays) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // �crire l'en-t�te
            writer.write("idEmploye,startDate,endDate,Typeh");
            writer.newLine();

            // �crire les donn�es
            for (Holiday holiday : holidays) {
                try {
                    // Validation des donn�es
                    if (holiday.getIdEmployee() <= 0) {
                        throw new IllegalArgumentException("idEmploye doit �tre un entier positif.");
                    }
                    if (holiday.getStartDate() == null || holiday.getEndDate() == null) {
                        throw new IllegalArgumentException("Les dates ne peuvent pas �tre nulles.");
                    }
                    if (holiday.getConge() == null ) {
                        throw new IllegalArgumentException("Typeh (conge) ne peut pas �tre null ou vide.");
                    }

                    // Construire la ligne � �crire
                    String line = String.format("%d,%s,%s,%s",
                            holiday.getIdEmployee(),
                            holiday.getStartDate(),
                            holiday.getEndDate(),
                            holiday.getConge());
                    writer.write(line);
                    writer.newLine();
                } catch (IllegalArgumentException e) {
                    System.err.println("Erreur de format pour un Holiday : " + e.getMessage());
                }
            }

            System.out.println("Donn�es export�es avec succ�s vers " + fileName);
        } catch (IOException e) {
            showError("Erreur lors de l'exportation : " + e.getMessage());
        }
    }

    public void handleImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "csv", "txt"));

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                model.importData(filePath); // Assurez-vous que `model` est correctement initialis�
                showMessage("Importation r�ussie !", filePath);
            } catch (Exception e) {
                showError("Erreur lors de l'importation : " + e.getMessage());
            }
        }
    }

    private void initializeListeners() {
        view.getajouterButton().addActionListener(e -> {
            System.out.println("Clic sur ajouterButton");
            try {
                addh(false);
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        view.getmodButton().addActionListener(e -> {
            System.out.println("Modification du cong�");
            addh(true); // true pour une mise � jour
        });

        view.getsuppButton().addActionListener(e -> {
            System.out.println("Suppression du cong�");
            deleteh();
        });

        view.getHolidayTable().getSelectionModel().addListSelectionListener(e -> {
            System.out.println("S�lection de la ligne dans le tableau de cong�s.");
            populateFieldsFromTable();
        });

        view.getRefButton().addActionListener(e -> {
            System.out.println("Rafra�chissement de l'affichage des cong�s.");
            refreshDisplay();
        });

        // Ajouter les �couteurs pour les nouveaux boutons
        view.bimport.addActionListener(e -> handleImport());

        view.bexport.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers CSV", "csv"));
            if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                handleExport(filePath, model.getAllHolidays());// Utilisez une m�thode pour obtenir la liste des cong�s
            }
        });
    }
    private void addh(boolean isUpdate) {
        try {
            int holidayId = isUpdate ? getSelectedHolidayId() : 0;
            int employeeId = getSelectedEmployeeId();
            LocalDate startDate = parseDate(view.getStartDateField().getText().trim());
            LocalDate endDate = parseDate(view.getEndDateField().getText().trim());
            Typeh holidayType = (Typeh) view.getHolidayTypeComboBox().getSelectedItem();

            // V�rification des dates : la date de d�but doit �tre actuelle ou future
            if (startDate.isBefore(LocalDate.now())) {
                showError("La date de d�but doit �tre �gale ou post�rieure � la date actuelle.");
                return;
            }

            // V�rification que la date de d�but est ant�rieure � la date de fin
            if (!startDate.isBefore(endDate)) {
                showError("La date de d�but doit �tre ant�rieure � la date de fin.");
                return;
            }

            // V�rification que la date de fin est �gale ou post�rieure � la date de d�but
            if (endDate.isBefore(startDate)) {
                showError("La date de fin doit �tre �gale ou post�rieure � la date de d�but.");
                return;
            }

            // Appel au mod�le pour ajouter ou mettre � jour le cong�
            boolean success = model.addh(holidayId, employeeId, startDate, endDate, holidayType, isUpdate);
            if (success) {
                showMessage(isUpdate ? "Cong� mis � jour avec succ�s !" : "Cong� ajout� avec succ�s !", "Confirmation");
                refreshDisplay();
            } else {
                showError("�chec de l'op�ration sur le cong� : Solde insuffisant ou cong� trop long.");
            }
        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
        }
    }
    private void deleteh() {
        try {
            int holidayId = getSelectedHolidayId();
            if (model.deleteh(holidayId)) {
                showMessage("Cong� supprim� avec succ�s !", "Confirmation");
                refreshDisplay();
            } else {
                showError("�chec de la suppression du cong�.");
            }
        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
        }
    }

    private void populateFieldsFromTable() {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow < 0) return;

        try {
            // Remplir les champs avec les valeurs extraites du tableau
            Object employeeValue = view.getHolidayTable().getValueAt(selectedRow, 1);
            for (int i = 0; i < view.getEmployeeComboBox().getItemCount(); i++) {
                if (view.getEmployeeComboBox().getItemAt(i).toString().equals(employeeValue.toString())) {
                    view.getEmployeeComboBox().setSelectedItem(view.getEmployeeComboBox().getItemAt(i));
                    break;
                }
            }
            view.getStartDateField().setText(view.getHolidayTable().getValueAt(selectedRow, 2).toString());
            view.getEndDateField().setText(view.getHolidayTable().getValueAt(selectedRow, 3).toString());
            view.getHolidayTypeComboBox().setSelectedItem(view.getHolidayTable().getValueAt(selectedRow, 4).toString());
        } catch (Exception e) {
            showError("Erreur lors du remplissage des champs : " + e.getMessage());
        }
    }
    private void refreshDisplay() {
        List<Object[]> holidays = model.getHolidays();
        view.getTableModelh().setRowCount(0);
        for (Object[] holiday : holidays) {
            view.getTableModelh().addRow(holiday);
        }
    }
    private int getSelectedHolidayId() throws Exception {
        int selectedRow = view.getHolidayTable().getSelectedRow();
        if (selectedRow < 0) throw new Exception("Aucun cong� s�lectionn�.");
        return parseInteger(view.getHolidayTable().getValueAt(selectedRow, 0));
    }

    private int getSelectedEmployeeId() throws Exception {
        Object selectedEmployee = view.getEmployeeComboBox().getSelectedItem();
        if (selectedEmployee instanceof Employe) {
            return ((Employe) selectedEmployee).getId();
        }
        throw new Exception("Employ� s�lectionn� invalide.");
    }

    private LocalDate parseDate(String dateText) {
        return LocalDate.parse(dateText);
    }

    private int parseInteger(Object value) throws Exception {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        throw new Exception("Valeur entre invalide : " + value);
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
