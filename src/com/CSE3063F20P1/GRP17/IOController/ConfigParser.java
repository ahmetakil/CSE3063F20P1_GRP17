package com.CSE3063F20P1.GRP17.IOController;

import com.CSE3063F20P1.GRP17.UserModels.User;

import java.util.ArrayList;
import java.util.List;

public class ConfigParser extends Parser {


    private String fileName;

    public ConfigParser(String fileName){
        this.fileName = fileName;
    }

    public List<User> getUsers(){

        ArrayList<User> users  = new ArrayList<>();
        return users;
    }


    @Override
    Object parse() {
        return null;
    }
}
