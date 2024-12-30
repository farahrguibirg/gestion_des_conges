package Controller;

import Model.LoginModel;
import View.LoginView;
import View.MainView;
import View.HolidayView;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginController(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;
        // Ajouter un écouteur pour le bouton Login
        this.loginView.addLoginListener(new LoginListener());
        // Ajouter un écouteur pour le bouton Reset
        this.loginView.addResetListener(new ResetListener());
    }

    // ActionListener pour le bouton Login
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            // Valider l'utilisateur et récupérer le rôle
            String role = loginModel.validateLogin(username, password);
            if (role != null) {
                System.out.println("Rôle de l'utilisateur : " + role);
                if (role.equals("User")) {
                    loginView.showMessage("Connexion réussie !");
                    // Ouvrir la vue des congés (HolidayView) et fermer la fenêtre de login
                    SwingUtilities.invokeLater(() -> {
                        HolidayView holidayView = new HolidayView();
                        loginView.dispose();  // Fermer la fenêtre de login
                        holidayView.setVisible(true);  // Afficher la fenêtre HolidayView
                    });
                } else if (role.equals("Admin") || role.equals("RH1")) {
                    loginView.showMessage("Connexion réussie !");
                    // Afficher MainView pour l'Admin et RH
                    SwingUtilities.invokeLater(() -> {
                        MainView mainView = new MainView(); // MainView pour admin et RH
                        loginView.dispose();  // Fermer la fenêtre de login
                        mainView.setVisible(true);  // Afficher MainView
                    });
                } else {
                    loginView.showMessage("Rôle inconnu !");
                }
            } else {
                loginView.showMessage("Nom ou mot de passe incorrect !");
            }
        }
    }
    // ActionListener pour le bouton Reset
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginView.showMessage("Champs réinitialisés");
            loginView.clearFields();
        }
    }
}
