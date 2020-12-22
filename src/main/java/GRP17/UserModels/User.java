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

    private List<Instance> labellingRequests;
    private Map<Label, Integer> frequency;
    private List<Double> timeSpendings;
    private List<Integer> datasetIds;

    public User(int id, String name, String type, double consistencyCheckProbability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.consistencyCheckProbability = consistencyCheckProbability;
        labellingRequests = new ArrayList<AssignedInstance>();
        frequency = new HashMap<Label, Integer>();
        timeSpendings = new ArrayList<Double>();
    }


    public Integer getNumberOfDatasets() {
        return datasets.size();
    }

    public void setDatasets(List<DataSet> datasets) {
       this.datasets = datasets;
    }

    //A-2
    public Map<DataSet, Double> listUsersDatasetWithCompletenessPercentage(){
        Map<DataSet, Double> consistencies = new HashMap<>();

        for (DataSet dataSet: datasets){
            int counter = 0;
            for(Instance instance: dataSet.getInstances()){
                if (instance.getLabelledUsers().contains(this)){
                    counter++;
                }
            }
            consistencies.put(dataSet ,(double) counter / (double)(dataSet.getInstances().size()) );
        }
        return consistencies;
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

    //A-3
    public List<AssignedInstance> getInstances(){
        return labellingRequests;
    }

    //A-4

    public Instance getRandomLabelledInstance(){

        Random random = new Random();
        List<Instance> uniqueInstances = getUniqueInstances();

        int length = uniqueInstances.size();
        int randomlySelectedIndex = random.nextInt(length);

        return uniqueInstances.get(randomlySelectedIndex);
    }

    public ArrayList<Instance> getUniqueInstances() {

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


    public AssignedInstance relabelAlreadyLabelledInstance(List<Label> allLabels,int maxNumberOfLabelsPerInstance){

        Instance previouslyLabelledInstance = getRandomLabelledInstance();
        return assignLabel(previouslyLabelledInstance,allLabels ,maxNumberOfLabelsPerInstance);

    }

    public AssignedInstance assignLabel(Instance instance, List<Label> allLabels, int maxNumberOfLabelsPerInstance) {


        long timerStart = currentTimeMillis();

        List<Label> subset = pickLabel(allLabels, maxNumberOfLabelsPerInstance);

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


}