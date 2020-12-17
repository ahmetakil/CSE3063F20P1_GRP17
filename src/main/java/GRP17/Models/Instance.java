package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Instance {
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("instance")
    private String instance;
    private Map<Label, Integer> frequency; // This name is not convinient. Change later.
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
            int currentFrequncy = frequency.get(newLabel);
            currentFrequncy++;
            frequency.put(newLabel, currentFrequncy);
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
        // Another loop for put the labels that have value of max frequnecy to array list
        ArrayList<Label> maxlabels = new ArrayList<>();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() == max){
                maxlabels.add(entry.getKey());
            }
        }
        //Select random from Array List
        finallabel = maxlabels.get( random.nextInt(maxlabels.size()));
    }

    //This method returns recurring percentages of assigned labels to a instance as in hashmap format.
    public HashMap<Label,Double> labelPercentage(){
        double totalSize = frequency.size();
        HashMap<Label,Double> percentage = new HashMap<Label,Double> ();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            percentage.put(entry.getKey(), (entry.getValue()/totalSize)*100);
        }
        return percentage;
    }

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

    //This method calculates entropy and returns it.
    public Double entropy(){
        Double entropy = 0;
        HashMap<Label,Double> percentage = labelPercentage();
        int size = frequency.size();
        for (HashMap.Entry<Label, Double> entry : frequency.entrySet()) {
            double propotion = entry.getValue()/size;
            entropy += propotion*Math.log(propotion)/Math.log(2);
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

}
