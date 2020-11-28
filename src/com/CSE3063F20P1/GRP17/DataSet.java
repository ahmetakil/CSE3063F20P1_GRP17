package com.CSE3063F20P1.GRP17;

import java.util.List;

public class DataSet {
    private int id;
    private String name;
    private int MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    private List<Label> labels;
    private List<Instance> instances;

    public DataSet(int id, String name, int MAX_NUMBER_OF_LABELS_PER_INSTANCE) {
        this.id = id;
        this.name = name;
        this.MAX_NUMBER_OF_LABELS_PER_INSTANCE = MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMAX_NUMBER_OF_LABELS_PER_INSTANCE() {
        return MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    }

    public void setMAX_NUMBER_OF_LABELS_PER_INSTANCE(int MAX_NUMBER_OF_LABELS_PER_INSTANCE) {
        this.MAX_NUMBER_OF_LABELS_PER_INSTANCE = MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }



}
