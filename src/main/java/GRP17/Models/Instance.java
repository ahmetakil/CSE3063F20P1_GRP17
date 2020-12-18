package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Instance {
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("instance")
    private String instance;
    private Map<Label, Integer> frequency; // This name is not convenient. Change later.
    private List<User> labelledUsers;

    private Label finalLabel;

    public Instance(int id, String content) {
        this.id = id;
        this.instance = content;
        this.frequency = new HashMap<Label, Integer>();
        this.labelledUsers = new ArrayList<User>();
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

    public void updateFrequencyLabelList(List<Label> labels){
        for(Label label: labels){
            updateFrequency(label);
        }
    }


    //This method takes all labels in the instance at the map and changes the value of finalLabel to most recurring label. (If equal selects randomly).
    public void determineFinalLabel(){
        int max = 0;
        //For find max value we loop once.
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() > max){
                max = entry.getValue();
            }
        }
        // Another loop for put the labels that have value of max frequency to array list
        ArrayList<Label> maxlabels = new ArrayList<>();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() == max){
                maxlabels.add(entry.getKey());
            }
        }
        //Select random from Array List
        Random random = new Random();
        finalLabel = maxlabels.get(random.nextInt(maxlabels.size()));
    }
    //B-1
    //This method assist from label hashmap and makes a summation of values corresponds to each label.
    public int noOfLabelAssignments(){
        int noOfLabelAssignments = 0;
        for(HashMap.Entry<Label, Integer> entry : frequency.entrySet()){
            noOfLabelAssignments += entry.getValue();
        }
        return noOfLabelAssignments;
    }

    //B-2
    //This method counts labels only if they have value of 1 in hashmap for differing unique labels.
    public int noOfUniqueLabelAssignments(){
        int noOfUniqueLabelAssignments = 0;
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            if(entry.getValue() == 1){
                noOfUniqueLabelAssignments++;
            }
        }
        return noOfUniqueLabelAssignments;
    }

    //B-3
    //This method returns size of the list of the user that labelled this particular instance
    public int noOfUniqueUsers(){
         Set<User> labelledUsers = new HashSet<User>(getLabelledUsers());
        return labelledUsers.size();
    }
    //B-4
    //This method returns most recurring label(s)? with using labelpercentage method.
    public HashMap<Label,Double> mostFrequentLabel(){
        double max = 0;
        HashMap<Label,Double> percentage = labelPercentage();
        HashMap<Label,Double> mostFrequent = new HashMap<Label,Double> ();
        for (HashMap.Entry<Label, Double> entry : percentage.entrySet()) {
            if(entry.getValue() > max){
                mostFrequent.clear();
                mostFrequent.put(entry.getKey(), entry.getValue());
            }   
        }
        return mostFrequent;
    }
    //B-5
    //This method returns recurring percentages of assigned labels to a instance as in hashmap format.
    public HashMap<Label,Double> labelPercentage(){
        double totalSize = frequency.size();
        HashMap<Label,Double> percentage = new HashMap<Label,Double> ();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            percentage.put(entry.getKey(), (entry.getValue()/totalSize)*100);
        }
        return percentage;
    }
    //B-6
    //This method calculates entropy and returns it.
    public Double entropy(){
        Double entropy = 0.0;
        HashMap<Label,Double> percentage = labelPercentage();
        int size = frequency.size();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            double proportion = (double)entry.getValue()/(double)size;
            entropy += proportion*Math.log(proportion)/Math.log(2);
        }
        return entropy;
    }



    public boolean isLabelled(){
        return !frequency.isEmpty();
    }

    public void addUser(User newUser){
        this.labelledUsers.add(newUser);
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


    public JsonObject getMetrics(){
        return null;
    }

    public Label getFinalLabel(){
        return this.finalLabel;
    }

    public List<User> getLabelledUsers(){
        return this.labelledUsers;
    }
}
