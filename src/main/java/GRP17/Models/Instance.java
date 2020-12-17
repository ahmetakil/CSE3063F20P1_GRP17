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

    public void determineFinalLabel(){
        int max = 0;
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() > max){
                max = entry.getValue();
            }
        }
        ArrayList<Label> maxlabels = new ArrayList<>();
        for (HashMap.Entry<Label, Integer> entry : frequency.entrySet()) {
            //System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getValue() == max){
                maxlabels.add(entry.getKey());
            }
        }
        finallabel = frequency.get( random.nextInt(frequency.size()));
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
