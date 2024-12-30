package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.EmployeImpl;
import DAO.HolidayImpl;
import Model.Employe;
import Model.*;
import java.awt.event.ActionListener;

public class HolidayView extends JPanel {
    private JComboBox<Employe> employeeComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JComboBox<Typeh> holidayTypeComboBox;
    private JTable holidayTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton refreshButton;
	public JButton bimport;
	public JButton bexport;

    public HolidayView() {
        setLayout(null); // Utilisation de Layout null pour positionnement absolu

        // Panneau pour les champs de saisie
        employeeComboBox = new JComboBox<>();
        startDateField = new JTextField();
        endDateField = new JTextField();
        holidayTypeComboBox = new JComboBox<>(Typeh.values());

        addLabelAndField("Employee:", 20, 20, employeeComboBox, 600);
        addLabelAndField("Start Date:", 20, 60, startDateField, 600);
        addLabelAndField("End Date:", 20, 100, endDateField, 600);
        addLabelAndField("Holiday Type:", 20, 140, holidayTypeComboBox, 600);

        addButton = addButton("Add", 20, 450); // Position initiale pour le bouton Add
        deleteButton = addButton("Delete", 180, 450); // Position ajust�e avec un espace uniforme
        updateButton = addButton("Update", 340, 450); // Position align�e sur la m�me ligne
        refreshButton = addButton("Refresh",500, 450); // Suit la m�me logique d'espacement
        bimport = addButton("Import", 660, 450); // Aligne Import sur la m�me ligne
        bexport = addButton("Export", 820, 450); // Dernier bouton de la rang�e

        // Tableau des cong�s
        String[] columns = {"ID", "Employee", "Start Date", "End Date", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        holidayTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(holidayTable);
        scrollPane.setBounds(20, 180, 940, 150); // Positionner le tableau correctement
        add(scrollPane);

        // Remplissage du JComboBox avec les employ�s
        EmployeImpl employeeModel = new EmployeImpl();
        for (Employe emp : employeeModel.getAll()) {
            employeeComboBox.addItem(emp);
        }

        // S�lection dans le tableau
        holidayTable.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = holidayTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Remplir avec l'objet Employe complet, pas seulement une String
                Object employeeValue = holidayTable.getValueAt(selectedRow, 1);
                for (int i = 0; i < employeeComboBox.getItemCount(); i++) {
                    if (employeeComboBox.getItemAt(i).toString().equals(employeeValue.toString())) {
                        employeeComboBox.setSelectedItem(employeeComboBox.getItemAt(i));
                        break;
                    }
                }
                startDateField.setText(holidayTable.getValueAt(selectedRow, 2).toString());
                endDateField.setText(holidayTable.getValueAt(selectedRow, 3).toString());
                holidayTypeComboBox.setSelectedItem(holidayTable.getValueAt(selectedRow, 4).toString());
            }
        });
    }

    // M�thode pour ajouter un label et un champ
    private void addLabelAndField(String label, int x, int y, JComponent component, int width) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 100, 30);
        add(lbl);
        component.setBounds(x + 100, y, width, 30);
        add(component);
    }

    // M�thode pour ajouter un bouton
    private JButton addButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 30);
        add(button);
        return button;
    }

    // M�thode pour remplir la table des cong�s
    public void populateHolidayTable(HolidayImpl daoHoliday, EmployeImpl daoEmployee) {
        tableModel.setRowCount(0);
        for (Holiday holiday : daoHoliday.getAll()) {
            Employe emp = daoEmployee.findById(holiday.getIdEmployee());
            String employeeName = (emp != null) ? emp.getNom() + " " + emp.getPrenom() : "Inconnu";
            tableModel.addRow(new Object[]{
                holiday.getId(), employeeName, holiday.getStartDate(),
                holiday.getEndDate(), holiday.getConge()
            });
        }
    }

    // M�thode pour mettre � jour le JComboBox des employ�s
    public void populateEmployeeComboBox(EmployeImpl daoEmployee) {
        employeeComboBox.removeAllItems();
        for (Employe emp : daoEmployee.getAll()) {
            employeeComboBox.addItem(emp);
        }
    }

    // Getters pour les composants graphiques
    public JComboBox<Employe> getEmployeeComboBox() {
        return employeeComboBox;
    }

    public JTextField getStartDateField() {
        return startDateField;
    }

    public JTextField getEndDateField() {
        return endDateField;
    }

    public JComboBox<Typeh> getHolidayTypeComboBox() {
        return holidayTypeComboBox;
    }

    public JTable getHolidayTable() {
        return holidayTable;
    }

    public DefaultTableModel getTableModelh() {
        return tableModel;
    }

    public JButton getajouterButton() {
        return addButton;
    }

    public JButton getsuppButton() {
        return deleteButton;
    }

    public JButton getmodButton() {
        return updateButton;
    }

    public JButton getRefButton() {
        return refreshButton;
    }

    // M�thodes pour ajouter les listeners aux boutons
    public void addButtonListeners(ActionListener addListener, ActionListener deleteListener, ActionListener updateListener, ActionListener refreshListener) {
        addButton.addActionListener(addListener);
        deleteButton.addActionListener(deleteListener);
        updateButton.addActionListener(updateListener);
        refreshButton.addActionListener(refreshListener);
    }
}
