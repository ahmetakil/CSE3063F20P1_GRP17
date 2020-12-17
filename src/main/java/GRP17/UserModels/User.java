package GRP17.UserModels;

import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class User {

    @SerializedName("user id")
    private int id;
    @SerializedName("user name")
    private String name;
    @SerializedName("user type")
    private String type;

    private List<AssignedInstance> labellingRequests;
    private Map<Label,Integer> frequency;

    private List<Double> timeSpendings;

    public User(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        labellingRequests = new ArrayList<AssignedInstance>();
        frequency = new HashMap<Label,Integer>();
        timeSpendings = new ArrayList<Double>();
    }

    private void addFrequencyLabel(Label newLabel){

        if (frequency.containsKey(newLabel)) {
            int currentFrequncy = frequency.get(newLabel);
            currentFrequncy++;
            frequency.put(newLabel, currentFrequncy);
            return;
        }

        frequency.put(newLabel, 1);
    }

    public void addFrequencyLabelList(List<Label> labels){

        for(Label label: labels){
            addFrequencyLabel(label);
        }
    }

    public double getAverageTimeSpending(){
        //TODO
        return 0;
    }

    public double getStandartDeviation(){
        //TODO
        return 0;
    }

    public void addTimeSpending(double timeSpending){
        this.timeSpendings.add(timeSpending);
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<AssignedInstance> getLabellingRequests() {
        return labellingRequests;
    }

    protected void tryLabellingAgainWithRandom(){


    }

    public abstract AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance);
}