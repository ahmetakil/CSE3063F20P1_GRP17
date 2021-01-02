package GRP17;

import GRP17.Models.ConfigSet;
import GRP17.UserModels.HumanUser;
import GRP17.UserModels.User;


import java.util.Scanner;

public class LoginController {
    private static LoginController loginInstance = null;
    private static ConfigSet configSet = null;
    private static User loggedInUser = null;

    public static LoginController getInstance() {
        if (loginInstance == null)
            loginInstance = new LoginController();
        return loginInstance;
    }

    public User getUser() {
        return loggedInUser;
    }

    void setConfigSet(ConfigSet cSet) {
        configSet = cSet;
    }

    void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (username.equals("") && password.equals("")) {
            return;
        }
        for (User user : configSet.getUsers()) {
            if (user instanceof HumanUser && ((HumanUser) user).checkAccount(username, password)) {
                System.out.println("Logged in as " + username);
                loggedInUser = user;
                return;
            }
        }
        System.out.println("Invalid username or password.");
        login();
    }

    boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
