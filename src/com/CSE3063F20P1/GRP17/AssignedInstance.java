package com.CSE3063F20P1.GRP17;

import com.CSE3063F20P1.GRP17.UserModels.User;

import java.util.Date;
import java.util.List;

public class AssignedInstance {
    private Instance instance;
    private List<Label> labels;
    private User user;
    private Date date;

    public AssignedInstance(User user, Instance instance,List<Label> labels,Date date) {
        this.instance = instance;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }
}
