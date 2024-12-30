package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.*;
import Controller.LoginController;
import Model.LoginModel;

public class LoginView extends JFrame {
    private JTextField txtNom, txtPrenom;
    private JButton btnLogin, btnReset;
    private JLabel label1, label2;

    public LoginView() {
        // Configuration de la fenêtre principale (JFrame)
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Container pour les composants
        Container c = getContentPane();
        c.setBackground(Color.WHITE);

        // Initialisation des composants
        label1 = new JLabel("Nom :");
        label2 = new JLabel("Prénom :");
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        btnLogin = new JButton("Login");
        btnReset = new JButton("Reset");

        // Personnalisation des boutons
        btnLogin.setBackground(Color.BLUE);
        btnLogin.setForeground(Color.WHITE);
        btnReset.setBackground(Color.BLUE);
        btnReset.setForeground(Color.WHITE);

        // Placement des composants dans la fenêtre
        label1.setBounds(50, 50, 100, 20);
        txtNom.setBounds(150, 50, 150, 25);

        label2.setBounds(50, 100, 100, 20);
        txtPrenom.setBounds(150, 100, 150, 25);

        btnLogin.setBounds(50, 150, 100, 30);
        btnReset.setBounds(200, 150, 100, 30);

        // Ajouter les composants au Container
        add(label1);
        add(label2);
        add(txtNom);
        add(txtPrenom);
        add(btnLogin);
        add(btnReset);

        // Connexion avec le modèle et le contrôleur
        LoginModel loginModel = new LoginModel();
        new LoginController(this, loginModel);  // Connexion de la vue et du contrôleur

        // Affichage de la fenêtre
        setVisible(true);
    }

    // Méthode pour effacer les champs
    public void clearFields() {
        txtNom.setText("");
        txtPrenom.setText("");
    }

    // Méthode pour obtenir le nom
    public String getUsername() {
        return txtNom.getText();
    }

    // Méthode pour obtenir le prénom (mot de passe dans ce cas)
    public String getPassword() {
        return txtPrenom.getText();
    }

    // Méthode pour ajouter un écouteur d'événement pour le bouton Login
    public void addLoginListener(ActionListener listenLogin) {
        btnLogin.addActionListener(listenLogin);
    }

    // Méthode pour ajouter un écouteur d'événement pour le bouton Reset
    public void addResetListener(ActionListener listenReset) {
        btnReset.addActionListener(listenReset);
    }

    // Méthode pour afficher un message à l'utilisateur
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Méthode pour récupérer la fenêtre de login (frame)
    public JFrame getLoginFrame() {
        return this;  // Retourne l'instance de la fenêtre actuelle
    }

    // Méthode main pour démarrer l'application
    public static void main(String[] args) {
        // Initialiser LoginView et afficher la fenêtre de login
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        new LoginController(loginView, loginModel);  // Connexion de la vue et du modèle
        loginView.setVisible(true);
    }
}
