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
        // Ajouter un �couteur pour le bouton Login
        this.loginView.addLoginListener(new LoginListener());
        // Ajouter un �couteur pour le bouton Reset
        this.loginView.addResetListener(new ResetListener());
    }

    // ActionListener pour le bouton Login
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            // Valider l'utilisateur et r�cup�rer le r�le
            String role = loginModel.validateLogin(username, password);
            if (role != null) {
                System.out.println("R�le de l'utilisateur : " + role);
                if (role.equals("User")) {
                    loginView.showMessage("Connexion r�ussie !");
                    // Ouvrir la vue des cong�s (HolidayView) et fermer la fen�tre de login
                    SwingUtilities.invokeLater(() -> {
                        HolidayView holidayView = new HolidayView();
                        loginView.dispose();  // Fermer la fen�tre de login
                        holidayView.setVisible(true);  // Afficher la fen�tre HolidayView
                    });
                } else if (role.equals("Admin") || role.equals("RH1")) {
                    loginView.showMessage("Connexion r�ussie !");
                    // Afficher MainView pour l'Admin et RH
                    SwingUtilities.invokeLater(() -> {
                        MainView mainView = new MainView(); // MainView pour admin et RH
                        loginView.dispose();  // Fermer la fen�tre de login
                        mainView.setVisible(true);  // Afficher MainView
                    });
                } else {
                    loginView.showMessage("R�le inconnu !");
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
            loginView.showMessage("Champs r�initialis�s");
            loginView.clearFields();
        }
    }
}
