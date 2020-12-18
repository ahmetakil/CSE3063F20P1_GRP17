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
    @SerializedName("consistency check probability")
    private double consistencyCheckProbability;

    private List<AssignedInstance> labellingRequests;
    private Map<Label,Integer> frequency;

    private List<Double> timeSpendings;


    public User(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.consistencyCheckProbability = 0.1;
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

    public int noOfInstance() {
        return labellingRequests.size();

    }

    public int noOfUniqueInstance() {
        int counter = 0;
        ArrayList<AssignedInstance> uniqueRequests = new ArrayList<>();
        for (Integer i: labellingRequests){
            if (!uniqueRequests.contains(labellingRequests.get(i))){
                uniqueRequests.add(labellingRequests.get(i));
            }
        }
        return uniqueRequests.size();
    }

    public int consistency(){
        //TODO
    }


    public void addFrequencyLabelList(List<Label> labels){

        for(Label label: labels){
            addFrequencyLabel(label);
        }
    }

    public double getAverageTimeSpending(){
        double sumAllValues = 0;
        for(int i = 0 ; i < timeSpendings.size() ; i++){
            sumAllValues += timeSpendings.get(i);
        }
        double averageTime = sumAllValues / timeSpendings.size();
        return averageTime;
    }

    public double getStandardDeviation(){
        double var = 0;
        double averageTime = getAverageTimeSpending();
        for(int i = 0 ; i < timeSpendings.size() ; i++){
            var += Math.pow((timeSpendings.get(i) - averageTime),2);
        }
        double sd = (1/timeSpendings.size()) * var;
        sd = Math.sqrt(sd);
        return sd;
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