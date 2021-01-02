package GRP17.Models;

import GRP17.UserModels.HumanUser;
import GRP17.UserModels.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.*;

public class DataSet implements Serializable {

    @SerializedName("dataset id")
    private Integer id;
    @SerializedName("dataset name")
    private String name;
    @SerializedName("maximum number of labels per instance")
    private int MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    @SerializedName("class labels")
    private List<Label> labels;

    private List<Instance> instances; // Since the json key name is also the same we can skip the annotation
    private List<User> users;

    public DataSet() {
        this.users = new ArrayList<>();
        this.instances = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(List<User> users) {
        this.users.addAll(users);
    }


    //C-1
    public Double getCompleteness() {
        double completeness = 0;
        for (Instance instance : instances) {
            if (instance.isLabelled()) {
                completeness += 1;
            }
        }
        return (completeness / instances.size()) * 100;
    }

    //C-2
    public Map<Label, Double> getClassDistributionsBasedOnFinalInstanceLabels() {
        Map<Label, Double> numOfLabels = new HashMap<>();
        Map<Label, Double> distributions = new HashMap<>();

        double total = 0;

        for (Instance instance : instances) {
            Label finalLabel = instance.getFinalLabel();
            if (finalLabel != null) {
                if (!numOfLabels.containsKey(finalLabel)) {
                    numOfLabels.put(finalLabel, 1.0);
                } else {
                    numOfLabels.put(finalLabel, numOfLabels.get(finalLabel) + 1);
                }
                total += 1;
            }
        }
        for (Map.Entry label : numOfLabels.entrySet()) {
            Double dist = ((Double) label.getValue() / total) * 100.0;
            distributions.put((Label) label.getKey(), dist);
        }
        return distributions;
    }

    //C-3
    public Map<Label, Integer> getUniqueInstancesForLabels() {
        Map<Label, Integer> unique = new HashMap<>();
        for (Instance instance : instances) {
            Map<Label, Integer> frequency = instance.getFrequency();
            Set<Label> labels = frequency.keySet();
            for (Label label : labels) {
                if (unique.containsKey(label))
                    unique.put(label, frequency.get(label) + 1);
                else
                    unique.put(label, 1);
            }
        }
        return unique;
    }

    //C-4
    public int noOfUsersAssignedToThisDataset() {
        return users.size();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberLabels() {
        return MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> getBotUsers() {
        List<User> botUsers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof HumanUser) {
                continue;
            }
            botUsers.add(user);
        }
        return botUsers;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public void setFields(DataSet cachedDataset) {
        this.labels = cachedDataset.getLabels();
        this.users = cachedDataset.getUsers();
        this.instances = cachedDataset.getInstances();
    }

    public void updateInstance(Instance updatedInstance) {
        for (Instance instance : instances) {
            if (updatedInstance.getId().equals(instance.getId())) {
                instance.setFrequency(updatedInstance.getFrequency());
            }
        }
    }

    public void updateDataset(DataSet configDataset) {
        MAX_NUMBER_OF_LABELS_PER_INSTANCE = configDataset.getMaxNumberLabels();
        List<User> existingUsers = configDataset.getUsers();

        for (User user : existingUsers) {
            if (!this.users.contains(user)) {
                this.users.add(user);
            }
        }

        List<Instance> existingInstances = configDataset.getInstances();

        for (Instance instance : existingInstances) {
            if (!this.instances.contains(instance)) {
                this.instances.add(instance);
            }
        }

    }

    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof DataSet)) {
            return false;
        }

        return ((DataSet) o).getId().equals(this.id);

    }

}
