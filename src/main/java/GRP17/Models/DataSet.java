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



    private Map<Label, Integer> getClassDistributionsBasedOnFinalInstanceLabels() {
        //TODO [C-2] NEW
        Map<Label, Integer> distributions = new HashMap<Label, Integer>();

        for (Instance instance : instances) {
            Label finalLabel = instance.getFinalLabel();
            if (!distributions.containsKey(finalLabel)) {
                distributions.put(finalLabel, 1);
            } else {
                distributions.put(finalLabel, distributions.get(finalLabel) + 1);
            }

        }
        return distributions;
    }

    public void getClassDistributionWithRespectToFinalLabels() {
        //TODO [C-2] Loop through the instances use final labels.
        //noOfFinalLabels
        int noOFFinalLabels = instances.size();
        Map<Label, Integer> finalLabelMap = getClassDistributionsBasedOnFinalInstanceLabels();
        System.out.println();
        int index=0;
        for (HashMap.Entry<Label, Integer> entry : finalLabelMap.entrySet()) {
            if(finalLabelMap.entrySet().size() - 1 == index){
                System.out.print("%" + ((entry.getValue() / noOFFinalLabels) * 100) + " " + entry.getKey().getName());
            }else{
                System.out.print("%" + ((entry.getValue() / noOFFinalLabels) * 100) + " " + entry.getKey().getName() + ", ");
            }
                index++;
        }
    }


    public Map<Label, Integer> getUniqueInstancesForLabels() {
        // TODO [C-3]
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

    public Map<User, Integer> getUniqueInstancesForEachUser() {
        //TODO [C-5]
        Map<User, Integer> unique = new HashMap<User, Integer>();
        for (User user : users) {
            unique.put(user, user.NEWgetUniqueInstances().size());
        }
        return unique;
    }

    public Map<User, Double> getListOfUsersWithConsistencyPercentage() {
        //TODO [C-6]
        Map<User, Double> consistencyPercentage = new HashMap<>();

        for (User user : users) {
            consistencyPercentage.put(user, user.getConsistencyPercentage());
        }

        return consistencyPercentage;
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
