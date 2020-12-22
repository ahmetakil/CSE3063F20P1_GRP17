package GRP17.IOController;

import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class ReportWriter {
    private String fileName;

    public ReportWriter(String fileName) {
        this.fileName = fileName;
    }


    public void UserMetrics(User user, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("user id: ", user.getId());
            jsonObject.addProperty("user name: ", user.getName());
            jsonObject.addProperty("Number of datasets: ", user.getNumberOfDatasets()); //1

            // TODO 2
            jsonObject.addProperty("\nTotal number of instances labeled :", user.getInstances()); //3
            jsonObject.addProperty("\nTotal number of unique instances labeled :", user.getUniqueInstances().size()); //4
            jsonObject.addProperty("\nConsistency percentage :", user.getConsistencyPercentage());//5
            jsonObject.addProperty("\nAverage time spent in labeling an instance :", user.getAverageTimeSpending()); //6
            jsonObject.addProperty("\nStd. dev. of  time spent in labeling an instance : ", user.getStandardDeviation()); //7

        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }

    public void InstanceMetrics(Instance instance, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
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


        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }

    public void DatasetMetrics(DataSet dataSet, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("dataset id: ", dataSet.getId());
            jsonObject.addProperty("Completeness percentage: ", dataSet.getCompleteness()); //1
            //2
            jsonObject.addProperty("Class distribution based on final instance labels: ",
                    dataSet.printLabelToReport(dataSet.getClassDistributionsBasedOnFinalInstanceLabels()));

            //3
            Map<Label, Integer> uniqueInstancesForLabels = dataSet.getUniqueInstancesForLabels();
            for (Map.Entry<Label, Integer> entry : uniqueInstancesForLabels.entrySet()) {
                jsonObject.addProperty("label name and percentage: ", entry.getKey().getName() + ", " + entry.getValue() + "%");
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



        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }

    public void Write(DataSet dataSet, User user) {

        try (Writer writer = new FileWriter(fileName)) {

            Gson gson = new GsonBuilder().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("dataset id ", dataSet.getId());
            jsonObject.addProperty("dataset name", dataSet.getName());
            System.out.println("dataset id " + dataSet.getId() + " dataset name" + dataSet.getName());

            jsonObject.addProperty("\nUser name : ", user.getName());
            jsonObject.addProperty(" User ID : ", user.getId());
            System.out.println("User name: " + user.getName() + " User ID: " + user.getId());
            //1
            jsonObject.addProperty("\nNumber of datasets assigned: ", user.getNumberOfDatasets());
            System.out.println(" Number of datasets assigned: " + user.getNumberOfDatasets());
            //2
            for (int i = 0; i < user.listAllDatasets().size(); i++) {
                jsonObject.addProperty("\n" + user.listAllDatasets().get(i) + " completeness percentage: ", user.listAllDatasets().get(i).getCompleteness());
                System.out.println(user.listAllDatasets().get(i) + " completeness percentage: " + user.listAllDatasets().get(i).getCompleteness());
            }
            jsonObject.addProperty("\nTotal number of instances labeled :", user.getInstances()); //3
            System.out.println("\nTotal number of instances labeled :" + user.getInstances());
            jsonObject.addProperty("\nTotal number of unique instances labeled :", user.getUniqueInstances().size()); //4
            System.out.println("\nTotal number of unique instances labeled :" + user.getUniqueInstances());
            jsonObject.addProperty("\nConsistency percentage :", user.getConsistencyPercentage());//5
            System.out.println("\nConsistency percentage :" + user.getConsistencyPercentage());
            jsonObject.addProperty("\nAverage time spent in labeling an instance :", user.getAverageTimeSpending()); //6
            System.out.println("\nAverage time spent in labeling an instance :" + user.getAverageTimeSpending());
            jsonObject.addProperty("\nStd. dev. of  time spent in labeling an instance : ", user.getStandardDeviation()); //7
            System.out.println("\nStd. dev. of  time spent in labeling an instance : " + user.getStandardDeviation());


            gson.toJson(jsonObject, writer);

        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }
}
