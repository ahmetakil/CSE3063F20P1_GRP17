package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.annotations.SerializedName;

import java.security.Key;
import java.util.*;

public class DataSet {

    @SerializedName("dataset id")
    private int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMAX_NUMBER_OF_LABELS_PER_INSTANCE(int MAX_NUMBER_OF_LABELS_PER_INSTANCE) {
        this.MAX_NUMBER_OF_LABELS_PER_INSTANCE = MAX_NUMBER_OF_LABELS_PER_INSTANCE;
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
    public double getCompleteness() {
        double completeness = 0;
        for (Instance instance : instances) {
            if (instance.isLabelled()) {
                completeness += 1;
            }
        }
        return (completeness / instances.size()) * 100;
    }

    public Map<Label, Double> getClassDistributionsBasedOnFinalInstanceLabels() {
        //TODO [C-2] NEW
        Map<Label, Double> numOfLabels = new HashMap<>();
        Map<Label, Double> distributions = new HashMap<>();

        double total=0;

        for (Instance instance : instances) {
            Label finalLabel = instance.getFinalLabel();
            if (finalLabel != null) {
                if (!numOfLabels.containsKey(finalLabel)) {
                    numOfLabels.put(finalLabel, 1.0);
                } else {
                    numOfLabels.put(finalLabel, numOfLabels.get(finalLabel) + 1);
                }
                total+=1;
            }
        }


        for(Map.Entry label: numOfLabels.entrySet()){
            Double dist = ((Double) label.getValue()/total)*100.0;
            distributions.put((Label) label.getKey(),dist);
        }
        return distributions;
    }

    public String printLabelToReport(Map<Label, Integer> map) {
        //TODO [C-2] Loop through the instances use final labels.
        //noOfFinalLabels
        int size = instances.size();
        System.out.println();
        int index = 0;
        StringBuilder returnStr = new StringBuilder();
        for (Map.Entry<Label, Integer> entry : map.entrySet()) {
            index = getIndex(size, index, returnStr, map.entrySet().size(), entry.getValue(), (entry.getKey()).getName());
        }
        return returnStr.toString();
    }

    public String printUserToReport(Map<User, Integer> map) {
        //TODO [C-2] Loop through the instances use final labels.
        //noOfFinalLabels
        int size = instances.size();
        System.out.println();
        int index = 0;
        StringBuilder returnStr = new StringBuilder();
        for (HashMap.Entry<User, Integer> entry : map.entrySet()) {
            index = getIndex(size, index, returnStr, map.entrySet().size(), entry.getValue(), (entry.getKey()).getName());
        }
        return returnStr.toString();
    }

    private int getIndex(int size, int index, StringBuilder returnStr, int size2, Integer value, String name) {
        if (size2 - 1 == index) {

            returnStr.append("%").append((value / size) * 100).append(" ").append(name);

        } else {
            returnStr.append("%").append((value / size) * 100).append(" ").append(name).append(", ");
        }
        index++;
        return index;
    }


    public Map<Label, Integer> getUniqueInstancesForLabels() {
        // TODO [C-3]
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


    public int getId() {
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

}
