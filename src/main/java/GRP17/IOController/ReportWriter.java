package GRP17.IOController;

import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ReportWriter {
    String reportName;
    Writer writer;
    Gson gson;

    public ReportWriter(String fileName) throws IOException {
        writer = new FileWriter(fileName,false);
        gson = new GsonBuilder().create();
    }


    public JsonObject UserMetrics(User user) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("user id: ", user.getId());
        jsonObject.addProperty("user name: ", user.getName());
        System.out.println("USER METRICS");
        System.out.println("user id : " + user.getId() + " user name: " + user.getName());
        //1
        jsonObject.addProperty("Number of datasets: ", user.getNumberOfDatasets());
        System.out.println("Number of datasets: " + user.getNumberOfDatasets());
        //TODO A-2 is now in experimental use need to check
        //2
        jsonObject.addProperty("List of datasets and their completeness percentage: ", user.listUsersDatasetWithCompletenessPercentage().toString());
        System.out.println("List of datasets and their completeness percentage: " + user.listUsersDatasetWithCompletenessPercentage().toString());
        //3
        jsonObject.addProperty("Total number of instances labeled :", user.getInstances().size());
        System.out.println("Total number of instances labeled :" + user.getInstances().size());
        //4
        jsonObject.addProperty("Total number of unique instances labeled :", user.getUniqueInstances().size());
        System.out.println("Total number of unique instances labeled :" + user.getUniqueInstances().size());
        //5
        jsonObject.addProperty("Consistency percentage :", user.getConsistencyPercentage());
        System.out.println("Consistency percentage :" + user.getConsistencyPercentage());
        //6
        jsonObject.addProperty("Average time spent in labeling an instance :", user.getAverageTimeSpending());
        System.out.println("Average time spent in labeling an instance :" + user.getAverageTimeSpending());
        //7
        jsonObject.addProperty("Std. dev. of  time spent in labeling an instance : ", user.getStandardDeviation());
        System.out.println("Std. dev. of  time spent in labeling an instance : " + user.getStandardDeviation());
        return jsonObject;
    }

    public JsonObject InstanceMetrics(Instance instance) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("instance id: ", instance.getId());
        jsonObject.addProperty("instance: ", instance.getInstance());
        jsonObject.addProperty("Total number of label assignments: ", instance.noOfLabelAssignments()); //1
        jsonObject.addProperty("Number of unique label assignments: ", instance.noOfUniqueLabelAssignments());//2
        jsonObject.addProperty("Number of unique users: ", instance.noOfUniqueUsers()); //3

        jsonObject.addProperty("Most frequent class label and percentage: ",
                instance.mostFrequentLabel().getKey().getName() + ", " + instance.mostFrequentLabel().getValue() + "%");
        Map<Label, Double> labelPercentage = instance.labelPercentage(); //5
        for (Map.Entry<Label, Double> entry : labelPercentage.entrySet()) {
            jsonObject.addProperty("label name and percentage: ", entry.getKey().getName() + ", " + entry.getValue() + "%");
        }

        jsonObject.addProperty("Entropy: ", instance.entropy()); //6

        return jsonObject;
    }

    public JsonObject DatasetMetrics(DataSet dataSet) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("dataset id: ", dataSet.getId());
        jsonObject.addProperty("Completeness percentage: ", dataSet.getCompleteness()); //1
        //2
        jsonObject.addProperty("Class distribution based on final instance labels: ",
                dataSet.printLabelToReport(dataSet.getClassDistributionsBasedOnFinalInstanceLabels()));
        System.out.println("Class distribution based on final instance labels: " +
                dataSet.printLabelToReport(dataSet.getClassDistributionsBasedOnFinalInstanceLabels()));

        //3
        Map<Label, Integer> uniqueInstancesForLabels = dataSet.getUniqueInstancesForLabels();
        for (Map.Entry<Label, Integer> entry : uniqueInstancesForLabels.entrySet()) {
            jsonObject.addProperty("Label name and percentage: ", entry.getKey().getName() + ", " + entry.getValue() + "%");
        }

        //4
        jsonObject.addProperty("Number of users assigned to this dataset: ", dataSet.noOfUsersAssignedToThisDataset());

        //5
        jsonObject.addProperty("List of users assigned and their completeness percentage: ",
                dataSet.printUserToReport(dataSet.getUniqueInstancesForEachUser()));

        //6
        //TODO: CHANGE DOUBLE VALUES WITH PERCENTAGE
        Map<User, Double> userConsistencyPercentage = dataSet.getListOfUsersWithConsistencyPercentage();
        for (Map.Entry<User, Double> entry : userConsistencyPercentage.entrySet()) {
            jsonObject.addProperty("- List of users assigned and their consistency percentage: ", entry.getKey().getName() + ", " + entry.getValue() + "%");
        }


        return jsonObject;
    }

    public void Write(DataSet dataSet, List<User> users, List<Instance> instances) {


        JsonArray userArr = new JsonArray();
        JsonArray instanceArr = new JsonArray();


        for (User user : users) {
            userArr.add(UserMetrics(user));
        }

        for (Instance instance : instances) {
            if (instance.mostFrequentLabel() == null) {
                continue;
            }
            instanceArr.add(InstanceMetrics(instance));
        }


        JsonObject datasetObj = DatasetMetrics(dataSet);
        JsonArray allMetrics = new JsonArray();

        allMetrics.add(userArr);
        allMetrics.add(instanceArr);
        allMetrics.add(datasetObj);
        gson.toJson(allMetrics, writer);
    }
}
