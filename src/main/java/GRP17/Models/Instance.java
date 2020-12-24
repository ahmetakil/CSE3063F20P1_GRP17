package GRP17.Models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Instance {
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("instance")
    private String instance;
    private Map<Label, Integer> frequency; // This name is not convenient. Change later.
    private Label finalLabel;

    public Instance() {
        this.frequency = new HashMap<Label, Integer>();
    }


    Map<Label, Integer> getFrequency() {
        return frequency;
    }

    private void updateFrequency(Label newLabel) {
        if (frequency.containsKey(newLabel)) {
            int currentFrequency = frequency.get(newLabel);
            currentFrequency++;
            frequency.put(newLabel, currentFrequency);
            return;
        }
        frequency.put(newLabel, 1);
    }

    public void updateFrequencyLabelList(List<Label> labels) {
        for (Label label : labels) {
            updateFrequency(label);
        }
    }


    //This method takes all labels in the instance at the map and changes the value of finalLabel to most recurring label. (If equal selects randomly).
    public void determineFinalLabel() {
        int max = 0;

        //For find max value we loop once.
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }

        // Another loop for put the labels that have value of max frequency to array list
        ArrayList<Label> maxlabels = new ArrayList<>();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() == max) {
                maxlabels.add(entry.getKey());
            }
        }

        //Select random from Array List
        Random random = new Random();
        finalLabel = maxlabels.get(random.nextInt(maxlabels.size()));
    }

    //B-1
    //This method assist from label hashmap and makes a summation of values corresponds to each label.
    public int noOfLabelAssignments() {
        int noOfLabelAssignments = 0;
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            noOfLabelAssignments += entry.getValue();
        }
        return noOfLabelAssignments;
    }

    //B-2
    //This method counts labels only if they have value of 1 in hashmap for differing unique labels.
    public int noOfUniqueLabelAssignments() {
        int noOfUniqueLabelAssignments = 0;
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == 1) {
                noOfUniqueLabelAssignments++;
            }
        }
        return noOfUniqueLabelAssignments;
    }

    //B-4
    //This method returns most recurring label(s)? with using labelpercentage method.
    public Map.Entry<Label, Double> mostFrequentLabel() {
        double max = 0;
        HashMap<Label, Double> percentage = labelPercentage();
        HashMap<Label, Double> mostFrequent = new HashMap<>();
        for (HashMap.Entry<Label, Double> entry : percentage.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostFrequent.clear();
                mostFrequent.put(entry.getKey(), entry.getValue());
            }
        }

        for (HashMap.Entry<Label, Double> entry : mostFrequent.entrySet()) {
            return entry;
        }
        return null;
    }

    //B-5
    //This method returns recurring percentages of assigned labels to a instance as in hashmap format.
    public HashMap<Label, Double> labelPercentage() {
        double totalSize = 0;
        HashMap<Label, Double> percentage = new HashMap<>();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            totalSize += entry.getValue();
            percentage.put(entry.getKey(), entry.getValue() * 100.0);
        }

        for (HashMap.Entry<Label, Double> entry : percentage.entrySet()) {
            percentage.put(entry.getKey(), entry.getValue() / totalSize);
        }

        return percentage;
    }


    //B-6
    //This method calculates entropy and returns it.
    public Double entropy() {
        double entropy = 0.0;
        HashMap<Label, Double> percentage = labelPercentage();
        for (HashMap.Entry<Label, Double> entry : percentage.entrySet()) {
            double proportion = entry.getValue() / 100;
            entropy += -1 * (proportion) * Math.log(proportion) / Math.log(2);
        }
        return entropy;
    }


    public boolean isLabelled() {
        return !frequency.isEmpty();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String toString() {
        return this.instance;
    }


    public JsonObject getMetrics() {
        //TODO Implement getMetrics
        return null;
    }

    public Label getFinalLabel() {
        return this.finalLabel;
    }


}
