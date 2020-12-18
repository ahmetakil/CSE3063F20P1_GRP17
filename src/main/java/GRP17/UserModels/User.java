package GRP17.UserModels;

import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.annotations.SerializedName;

import java.util.*;


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

    public ArrayList<AssignedInstance> getUniqueInstances() {
        int counter = 0;
        ArrayList<AssignedInstance> uniqueRequests = new ArrayList<>();
        for (AssignedInstance i: labellingRequests){
            if (!uniqueRequests.contains(i)){
                uniqueRequests.add(i);
            }
        }
        return uniqueRequests;
    }

    public int consistency(){
        //TODO
        return 0;
    }


    public void addFrequencyLabelList(List<Label> labels){
        for(Label label: labels){
            addFrequencyLabel(label);
        }
    }

    public double getAverageTimeSpending(){
        double sumAllValues = 0;
        for (Double timeSpending : timeSpendings) {
            sumAllValues += timeSpending;
        }
        return sumAllValues / timeSpendings.size();
    }

    public double getStandardDeviation(){
        double var = 0;
        double averageTime = getAverageTimeSpending();
        for (Double timeSpending : timeSpendings) {
            var += Math.pow((timeSpending - averageTime), 2);
        }
        return Math.sqrt((1.0/timeSpendings.size()) * var);
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
        double randomNumber = Math.random();
        if(randomNumber > this.consistencyCheckProbability){
            //TODO Assign again
            tryLabellingAgainWithRandom();
        }
    }

    public abstract AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance);
}