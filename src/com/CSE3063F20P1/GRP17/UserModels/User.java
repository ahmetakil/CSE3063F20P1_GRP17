package com.CSE3063F20P1.GRP17.UserModels;

import com.CSE3063F20P1.GRP17.AssignedInstance;
import com.CSE3063F20P1.GRP17.Instance;
import com.CSE3063F20P1.GRP17.Label;

import java.util.List;

public abstract class User {
    private int id;
    private String type;
    private List <AssignedInstance> labellingRequests;

    public User(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public List<AssignedInstance> getLabellingRequests() {
        return labellingRequests;
    }

    public void setLabellingRequests(List<AssignedInstance> labellingRequests) {
        this.labellingRequests = labellingRequests;
    }

    public void addLabellingRequest(AssignedInstance labellingRequest){
        labellingRequests.add(labellingRequest);
    }

    public abstract AssignedInstance assignLabel(Instance instance, List<Label> labels);
}