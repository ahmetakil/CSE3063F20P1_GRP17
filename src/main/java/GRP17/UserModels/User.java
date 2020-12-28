package GRP17.UserModels;

import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.*;

import static java.lang.System.currentTimeMillis;


public abstract class User implements Serializable {

    @SerializedName("user id")
    private Integer id;
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

    public User(Integer id, String name, String type, double consistencyCheckProbability) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.consistencyCheckProbability = consistencyCheckProbability;
        labellingRequests = new ArrayList<>();
        frequency = new HashMap<>();
        timeSpendings = new ArrayList<>();
        datasetIds = new ArrayList<>();
    }



    public List<Integer> getDatasetIds() {
        return datasetIds;
    }

    public void addDatasetID(Integer id) {
        if (!datasetIds.contains(id))
            datasetIds.add(id);
    }
    //A-1
    public Integer getNumberOfDatasets() {
        if (datasetIds == null)
            return 0;
        return datasetIds.size();
    }
    public Double getConsistencyCheckProbability() {
        return consistencyCheckProbability;
    }

    public void setConsistencyCheckProbability(double consistencyCheckProbability) {
        this.consistencyCheckProbability = consistencyCheckProbability;
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
    public List<Instance> getInstances() {
        return labellingRequests;
    }
    //A-4
    private Instance getRandomLabelledInstance() {
        Random random = new Random();
        List<Instance> uniqueInstances = getUniqueInstances();

        int length = uniqueInstances.size();
        int randomlySelectedIndex = random.nextInt(length);

        return uniqueInstances.get(randomlySelectedIndex);
    }
    private void addFrequencyLabelList(List<Label> labels) {
        for (Label label : labels) {
            addFrequencyLabel(label);
        }
    }
    //A-6
    public Double getAverageTimeSpending() {
        double sumAllValues = 0;
        for (Double timeSpending : timeSpendings) {
            sumAllValues += timeSpending;
        }
        return sumAllValues / timeSpendings.size();
    }
    //A-7
    public Double getStandardDeviation() {
        double var = 0;
        double averageTime = getAverageTimeSpending();
        for (Double timeSpending : timeSpendings) {
            var += Math.pow((timeSpending - averageTime), 2);
        }
        return Math.sqrt((1.0 / timeSpendings.size()) * var);
    }

    private void addTimeSpending(double timeSpending) {
        this.timeSpendings.add(timeSpending);
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public AssignedInstance relabelAlreadyLabelledInstance(List<Label> allLabels, int maxNumberOfLabelsPerInstance) {
        Instance previouslyLabelledInstance = getRandomLabelledInstance();
        return assignLabel(previouslyLabelledInstance, allLabels, maxNumberOfLabelsPerInstance);

    }
    public AssignedInstance assignLabel(Instance instance, List<Label> allLabels, int maxNumberOfLabelsPerInstance) {
        try {
            long timerStart = currentTimeMillis();

            List<Label> subset = pickLabel(allLabels, maxNumberOfLabelsPerInstance);
            AssignedInstance assignedInstance = new AssignedInstance(this, instance, subset, new Date());
            instance.updateFrequencyLabelList(subset);
            instance.determineFinalLabel();
            this.addFrequencyLabelList(subset);
            addInstance(instance);

            Thread.sleep(100);
            long timerEnd = currentTimeMillis();
            double timeSpending = (timerEnd - timerStart) / 1000.0;
            this.addTimeSpending(timeSpending);

            return assignedInstance;

        } catch (Exception e) {
            System.out.println("Something went wrong with assignLabel");
            return null;
        }
    }
    private void addInstance(Instance instance) {
        labellingRequests.add(instance);
    }

    public List<Instance> getUniqueInstances() {
        List<Instance> uniqueInstances = new ArrayList<>();
        for (Instance instance : labellingRequests) {
            if (!uniqueInstances.contains(instance)) {
                uniqueInstances.add(instance);
            }
        }
        return uniqueInstances;
    }

    public boolean hasLabelledInstance() {
        return getUniqueInstances().size() > 0;
    }

    public void setUserFields(User otherUser){
        this.labellingRequests = otherUser.labellingRequests;
        this.frequency = otherUser.frequency;
        this.datasetIds = otherUser.datasetIds;
        this.timeSpendings = otherUser.timeSpendings;
    }

    public abstract List<Label> pickLabel(List<Label> labels, int maxNumberOfLabelsPerInstance);


}