package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigSet {

    @SerializedName("users") private List<User> users;

    public ConfigSet(List<User> users){
        this.users = users;
    }

    public List<User> getUsers(){
        return users;
    }

}