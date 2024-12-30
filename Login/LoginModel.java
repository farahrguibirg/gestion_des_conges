package Model;

import DAO.LoginImpl;

public class LoginModel {

    public String validateLogin(String username, String password) {
        // Create an instance of LoginImpl
        LoginImpl loginImpl = new LoginImpl();

        // Get the role of the user
        return loginImpl.getUserRole(username, password);
    }
}
