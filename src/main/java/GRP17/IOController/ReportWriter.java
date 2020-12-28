package GRP17.IOController;

import GRP17.MetricController;
import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ReportWriter {
    private MetricController metricController;
    private String reportName;
    private Gson gson;

    public ReportWriter(String fileName) {
        this.metricController = new MetricController();
        this.gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        this.reportName = fileName;
    }
    private JsonObject UserMetrics(User user, List<DataSet> allDatasets, List<AssignedInstance> allAssignedInstances) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user id: ", user.getId());
        jsonObject.addProperty("user name: ", user.getName());
        System.out.println("USER METRICS");
        System.out.println("user id : " + user.getId() + " user name: " + user.getName());
        //1
        jsonObject.addProperty("Number of datasets: ", user.getNumberOfDatasets());
        System.out.println("Number of datasets: " + user.getNumberOfDatasets());
        //2
        JsonArray jsonArray = new JsonArray();
        for (Map.Entry<DataSet, Double> dataSetMap : metricController.listUsersDatasetWithCompletenessPercentage(allDatasets, user).entrySet()) {
            jsonArray.add("dataset id" + dataSetMap.getKey().getId() + " : " + (int)(dataSetMap.getValue() *100) /100.0);
        }
        jsonObject.add("List of datasets and their completeness percentage: ", jsonArray);

        System.out.println("List of datasets and their completeness percentage: " + jsonObject.get("List of datasets and their completeness percentage: ").toString());
        //3
        jsonObject.addProperty("Total number of instances labeled :", user.getInstances().size());
        System.out.println("Total number of instances labeled :" + user.getInstances().size());
        //4
        jsonObject.addProperty("Total number of unique instances labeled :", user.getUniqueInstances().size());
        System.out.println("Total number of unique instances labeled :" + user.getUniqueInstances().size());
        //5
        jsonObject.addProperty("Consistency percentage :", metricController.getConsistencyPercentage(allAssignedInstances, user));
        System.out.println("Consistency percentage :" + metricController.getConsistencyPercentage(allAssignedInstances, user));
        //6
        jsonObject.addProperty("Average time spent in labeling an instance :", (int)(user.getAverageTimeSpending()*100000)/100000.0);
        System.out.println("Average time spent in labeling an instance :" + (int)(user.getAverageTimeSpending()*100000)/100000.0);
        //7
        jsonObject.addProperty("Std. dev. of  time spent in labeling an instance : ", (int)(user.getStandardDeviation()*100000)/100000.0);
        System.out.println("Std. dev. of  time spent in labeling an instance : " + (int)(user.getStandardDeviation()*100000)/100000.0);

        return jsonObject;
    }

    private JsonObject InstanceMetrics(Instance instance, List<AssignedInstance> allAssignedInstances) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("instance id: ", instance.getId());
        jsonObject.addProperty("instance: ", instance.getInstance());
        jsonObject.addProperty("Total number of label assignments: ", instance.noOfLabelAssignments()); //1
        jsonObject.addProperty("Number of unique label assignments: ", instance.noOfUniqueLabelAssignments());//2
        jsonObject.addProperty("Number of unique users: ", metricController.noOfUniqueUsersForInstance(allAssignedInstances, instance)); //3
        if (instance.mostFrequentLabel() == null) {
            jsonObject.addProperty("All class label percentage: ", 0);
        } else {
            jsonObject.addProperty("Most frequent class label and percentage: ",
                    instance.mostFrequentLabel().getKey().getName() + ", " + (int)(instance.mostFrequentLabel().getValue()*100)/100.0 + "%");
        }

        Map<Label, Double> labelPercentage = instance.labelPercentage(); //5
        JsonArray jsonArray = new JsonArray();
        for (Map.Entry<Label, Double> entry : labelPercentage.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + (int)(entry.getValue() *100)/100.0 + "%");
        }
        jsonObject.add("label name and percentage: ", jsonArray);

        jsonObject.addProperty("Entropy: ", instance.entropy()); //6

        return jsonObject;
    }

    private JsonObject DatasetMetrics(DataSet dataSet, List<AssignedInstance> allAssignedInstances) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("dataset id: ", dataSet.getId());
        //1
        jsonObject.addProperty("Completeness percentage: ", dataSet.getCompleteness());
        //2
        Map<Label, Double> classDistributionsBasedFinalLabels = dataSet.getClassDistributionsBasedOnFinalInstanceLabels();
        JsonArray jsonArray = new JsonArray();
        for (Map.Entry<Label, Double> entry : classDistributionsBasedFinalLabels.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + entry.getValue() + "%");
        }
        jsonObject.add("Label distributions: ", jsonArray);
        //3
        Map<Label, Integer> uniqueInstancesForLabels = dataSet.getUniqueInstancesForLabels();
        jsonArray = new JsonArray();
        for (Map.Entry<Label, Integer> entry : uniqueInstancesForLabels.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ": " + entry.getValue());
        }
        jsonObject.add("Number of unique instances for each class label: ", jsonArray);
        //4
        jsonObject.addProperty("Number of users assigned to this dataset: ", dataSet.noOfUsersAssignedToThisDataset());
        //5
        Map<User, Double> usersWithCompletenessPercentage = metricController.getUsersWithCompletenessPercentageForDataset(dataSet, allAssignedInstances);

        jsonArray = new JsonArray();
        for (Map.Entry<User, Double> entry : usersWithCompletenessPercentage.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + entry.getValue() + "%");
        }
        jsonObject.add("List of users assigned and their completeness percentage: ", jsonArray);
        //6
        jsonArray = new JsonArray();
        Map<User, Double> userConsistencyPercentage = metricController.getListOfUsersWithConsistencyPercentage(allAssignedInstances, dataSet);
        for (Map.Entry<User, Double> entry : userConsistencyPercentage.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + entry.getValue() + "%");
        }
        jsonObject.add(" List of users assigned and their consistency percentage:  ", jsonArray);

        return jsonObject;
    }

    public void Write(List<DataSet> currentDataSets, List<User> users, List<Instance> instances, List<AssignedInstance> allAssignedInstances) {
        try (Writer writer = new FileWriter(reportName)) {
            JsonArray userArr = new JsonArray();
            JsonArray instanceArr = new JsonArray();
            JsonArray datasetArr = new JsonArray();

            for (User user : users) {
                userArr.add(UserMetrics(user, currentDataSets, allAssignedInstances));
            }
            for (Instance instance : instances) {
                instanceArr.add(InstanceMetrics(instance, allAssignedInstances));
            }
            for (DataSet currentDataset : currentDataSets) {
                datasetArr.add(DatasetMetrics(currentDataset, allAssignedInstances));
            }
            JsonArray allMetrics = new JsonArray();

            allMetrics.add(userArr);
            allMetrics.add(instanceArr);
            allMetrics.add(datasetArr);

            gson.toJson(allMetrics, writer);

        } catch (Exception e) {
            System.out.println("ReportWriter.Write e: " + e);
        }
    }
}
