package com.CSE3063F20P1.GRP17.UserModels;


import com.CSE3063F20P1.GRP17.AssignedInstance;
import com.CSE3063F20P1.GRP17.Instance;
import com.CSE3063F20P1.GRP17.Label;

import java.util.List;
import java.util.Random;

public class RandomLabellingUser extends User {

    public RandomLabellingUser(int id, String type) {
        super(id, type);
    }

    @Override
    public AssignedInstance assignLabel(Instance instance, List<Label> labels) {
        //TODO IMPLEMENT ASSIGN LABEL
       Random random = new Random();
       return null;
    }
}
