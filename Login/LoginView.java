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
        // Configuration de la fen�tre principale (JFrame)
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
        label2 = new JLabel("Pr�nom :");
        txtNom = new JTextField();
        txtPrenom = new JTextField();
        btnLogin = new JButton("Login");
        btnReset = new JButton("Reset");

        // Personnalisation des boutons
        btnLogin.setBackground(Color.BLUE);
        btnLogin.setForeground(Color.WHITE);
        btnReset.setBackground(Color.BLUE);
        btnReset.setForeground(Color.WHITE);

        // Placement des composants dans la fen�tre
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

        // Connexion avec le mod�le et le contr�leur
        LoginModel loginModel = new LoginModel();
        new LoginController(this, loginModel);  // Connexion de la vue et du contr�leur

        // Affichage de la fen�tre
        setVisible(true);
    }

    // M�thode pour effacer les champs
    public void clearFields() {
        txtNom.setText("");
        txtPrenom.setText("");
    }

    // M�thode pour obtenir le nom
    public String getUsername() {
        return txtNom.getText();
    }

    // M�thode pour obtenir le pr�nom (mot de passe dans ce cas)
    public String getPassword() {
        return txtPrenom.getText();
    }

    // M�thode pour ajouter un �couteur d'�v�nement pour le bouton Login
    public void addLoginListener(ActionListener listenLogin) {
        btnLogin.addActionListener(listenLogin);
    }

    // M�thode pour ajouter un �couteur d'�v�nement pour le bouton Reset
    public void addResetListener(ActionListener listenReset) {
        btnReset.addActionListener(listenReset);
    }

    // M�thode pour afficher un message � l'utilisateur
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // M�thode pour r�cup�rer la fen�tre de login (frame)
    public JFrame getLoginFrame() {
        return this;  // Retourne l'instance de la fen�tre actuelle
    }

    // M�thode main pour d�marrer l'application
    public static void main(String[] args) {
        // Initialiser LoginView et afficher la fen�tre de login
        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel();
        new LoginController(loginView, loginModel);  // Connexion de la vue et du mod�le
        loginView.setVisible(true);
    }
}
