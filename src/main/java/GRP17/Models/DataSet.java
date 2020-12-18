package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.annotations.SerializedName;

import java.sql.SQLOutput;
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
    private List<User> users; //TODO

    public double getCompleteness() {
        //TODO [C-1] Loop the instances use isEmpty calculate percantage.
        double completeness = 0;
        for (Instance instance : instances) {
            if (instance.isLabelled()) {
                completeness += 1;
            }
        }
        return (completeness / instances.size()) * 100;
    }


    private void updateDistributionLabelList(Map<Label, Integer> distribution) {
        //TODO [C-2]
        for (Label label : labels) {
            if (distribution.containsKey(label)) {
                int currentDistributionValue = distribution.get(label);
                distribution.put(label, ++currentDistributionValue);
            } else {
                distribution.put(label, 1);
            }
        }
    }

    private Map<Label, Integer> mapFinalLabels() {
        //TODO [C-2]
        Map<Label, Integer> distributions = new HashMap<Label, Integer>();
        updateDistributionLabelList(distributions);
        for (HashMap.Entry<Label, Integer> entry : distributions.entrySet()) {
            for (Instance instance : instances) {
                if (entry.getKey().equals(instance.getFinalLabel())) {
                    int currentEntryValue = entry.getValue();
                    entry.setValue(++currentEntryValue);
                }
            }
        }
        return distributions;
    }

    public void getClassDistributionWithRespectToFinalLabels() {
        //TODO [C-2] Loop through the instances use final labels.
        //noOfFinalLabels
        int noOFFinalLabels = instances.size();
        Map<Label, Integer> finalLabelMap = mapFinalLabels();
        System.out.println();
        for (HashMap.Entry<Label, Integer> entry : finalLabelMap.entrySet()) {
            if (entry.getValue() > 0) {
                System.out.print("%" + ((entry.getValue() / noOFFinalLabels) * 100) + " " + entry.getKey().getName() + ", ");
            }
        }
    }


    public Map<Label, Integer> getUniqueInstancesForLabels() {
        // C-3
        Map<Label, Integer> unique = new HashMap<Label, Integer>();

        for (Label label : labels) {
            unique.put(label, 0);
        }
        for (Instance instance : instances) {
            Map<Label, Integer> frequency = instance.getFrequency();
            Set<Label> labels = frequency.keySet();
            for (Label label : labels) {
                unique.put(label, frequency.get(label) + 1);
            }
        }
        return unique;
    }

    public int noOfUsersAssignedToThisDataset() {
        //TODO [C-4]
        return users.size();
    }

    public Map<User, Integer> getUniqueInstancesForEachUser(){
        //TODO [C-5]
        Map<User, Integer> unique = new HashMap<User, Integer>();
        for(User user: users){
            unique.put(user, user.NEWgetUniqueInstances().size());
        }
        return unique;
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


}
