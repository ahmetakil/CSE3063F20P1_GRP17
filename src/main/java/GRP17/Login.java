package GRP17;

import GRP17.Models.ConfigSet;
import GRP17.UserModels.HumanUser;
import GRP17.UserModels.User;

public class Login {
    private static Login loginInstance = null;
    private static ConfigSet configSet = null;

    public static Login getInstance(ConfigSet configset) {
        if (loginInstance == null)
            loginInstance = new Login();
        if (configSet == null) {
            configSet = configset;
        }
        return loginInstance;
    }

    private User login(String username, String password) {
        for (User user : configSet.getUsers()) {
            if (user instanceof HumanUser && ((HumanUser) user).checkAccount(username, password)) {
                return user;
            }
        }
        return null;
    }
}
