package GRP17.UserModels;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.annotations.SerializedName;

import java.util.*;

import static java.lang.System.currentTimeMillis;


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
    private Map<Label, Integer> frequency;
    private List<Double> timeSpendings;

    private List<Integer> datasetIDS;

    public User(int id, String name, String type, double consistencyCheckProbability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.consistencyCheckProbability = consistencyCheckProbability;
        labellingRequests = new ArrayList<AssignedInstance>();
        frequency = new HashMap<Label, Integer>();
        timeSpendings = new ArrayList<Double>();
    }


    // TODO A1 NEW
    public int getDatasetIDS() {
        return datasetIDS.size();
    }

    public void setDatasetIDS(int id) {
       datasetIDS.add(id);
    }

    // TODO A2 NEW
    public void listUsersDatasetWithCompletenessPercentage(){
        Map<DataSet, Double> consistencies = new HashMap<>();
        for(int id: datasetIDS){
            //DataSet dataSet = getWithID();
           // consistencies.put(dataSet,dataSet.getCompleteness());
        }
    }

    public double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }


    private void addFrequencyLabel(Label newLabel) {

        if (frequency.containsKey(newLabel)) {
            int currentFrequncy = frequency.get(newLabel);
            currentFrequncy++;
            frequency.put(newLabel, currentFrequncy);
            return;
        }
        frequency.put(newLabel, 1);
    }
    //I am not sure a-1 and a-2 please check if they are correct.
    //A-1
    public int getNumOfDatasets(){
        ArrayList<DataSet> numOfDataset = new ArrayList<>();
        for (DataSet i : numOfDataset) {
            if (!numOfDataset.contains(i)) {
                numOfDataset.add(i);
            }
        }
        return numOfDataset.size();
    }
    //A-2
    public ArrayList<DataSet> listAllDatasets(){
        ArrayList<DataSet> dataset = new ArrayList<>();
        for (DataSet i : dataset) {
            if (!dataset.contains(i)) {
                dataset.add(i);
            }
        }
        return dataset;
    }
    //A-3
    public int getInstances(){
        return labellingRequests.size();
    }

    //A-4
    public int getUniqueInstances() {
        ArrayList<AssignedInstance> uniqueRequests = new ArrayList<>();
        for (AssignedInstance i : labellingRequests) {
            if (!uniqueRequests.contains(i)) {
                uniqueRequests.add(i);
            }
        }
        return uniqueRequests.size();
    }

    public Instance getRandomLabelledInstance(int i){
        return labellingRequests.get(i).getInstance();
    }

    public ArrayList<Instance> NEWgetUniqueInstances() {
        ArrayList<Instance> uniqueInstances = new ArrayList<>();
        for (AssignedInstance assignedInstance : labellingRequests) {
            if (!uniqueInstances.contains(assignedInstance.getInstance())) {
                uniqueInstances.add(assignedInstance.getInstance());
            }
        }
        return uniqueInstances;
    }

    //A-5
    public double getConsistencyPercentage() {
        //TODO
        Map<Label, Integer> labelCount = new HashMap<>();

        for (AssignedInstance assignedInstance : labellingRequests) {
            List<Label> labels = assignedInstance.getLabels();

            for (Label label : labels) {
                if (!labelCount.containsKey(label)) {
                    labelCount.put(label, 1);
                } else {
                    labelCount.put(label, labelCount.get(label) + 1);
                }
            }
        }

        Map.Entry<Label, Integer> maxEntry = null;

        double sum = 0;
        for (Map.Entry<Label, Integer> entry : labelCount.entrySet()) {
            sum += entry.getValue();
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null)
            return 0;

        return (maxEntry.getValue() / sum) * 100;
    }


    public void addFrequencyLabelList(List<Label> labels) {
        for (Label label : labels) {
            addFrequencyLabel(label);
        }
    }
    //A-6
    public double getAverageTimeSpending() {
        double sumAllValues = 0;
        for (Double timeSpending : timeSpendings) {
            sumAllValues += timeSpending;
        }
        return sumAllValues / timeSpendings.size();
    }
    //A-7
    public double getStandardDeviation() {
        double var = 0;
        double averageTime = getAverageTimeSpending();
        for (Double timeSpending : timeSpendings) {
            var += Math.pow((timeSpending - averageTime), 2);
        }
        return Math.sqrt((1.0 / timeSpendings.size()) * var);
    }


    public void addTimeSpending(double timeSpending) {
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

    protected void tryLabellingAgainWithRandom() {
        double randomNumber = Math.random();
        if (randomNumber > this.consistencyCheckProbability) {
            //TODO Assign again
            tryLabellingAgainWithRandom();
        }
    }

    public AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance) {


        long timerStart = currentTimeMillis();

        if (hasInstance(instance)) {
            if (shouldReLabelAlreadyLabelledInstance()) {
                return null;
            }
        }

        List<Label> subset = pickLabel(labels, maxNumberOfLabelsPerInstance);

        AssignedInstance assignedInstance = new AssignedInstance(this, instance, subset, new Date());

        instance.updateFrequencyLabelList(subset);
        instance.addUser(this);


        this.addFrequencyLabelList(subset);

        this.getLabellingRequests().add(assignedInstance);

        long timerEnd = currentTimeMillis();

        double timeSpending = (timerEnd - timerStart) / 1000.0;
        this.addTimeSpending(timeSpending);


        return assignedInstance;
    }

    public abstract List<Label> pickLabel(List<Label> labels, int maxNumberOfLabelsPerInstance);

    boolean hasInstance(Instance i) {
        //this.getLabellingRequests().contains(i)
        return true;
    }

    boolean shouldReLabelAlreadyLabelledInstance() {
        int rand = (int) (Math.random() * (100) + 1);
        if (rand < 10) {
            return true;
        }
        return false;
    }
}