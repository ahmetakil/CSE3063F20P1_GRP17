package GRP17;

import GRP17.Models.ConfigSet;
import GRP17.UserModels.HumanUser;
import GRP17.UserModels.User;

import java.util.Scanner;

public class LoginController {
    private static LoginController loginInstance = null;
    private static ConfigSet configSet = null;

    public static LoginController getInstance(ConfigSet configset) {
        if (loginInstance == null)
            loginInstance = new LoginController();
        if (configSet == null) {
            configSet = configset;
        }
        return loginInstance;
    }

    private User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (username.equals("") && password.equals("")) {
            return null;
        }
        for (User user : configSet.getUsers()) {
            if (user instanceof HumanUser && ((HumanUser) user).checkAccount(username, password)) {
                System.out.println("Logged in as " + username);
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return login();
    }
}
