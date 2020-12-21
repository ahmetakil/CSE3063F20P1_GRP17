package GRP17.IOController;

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
import java.io.IOException;
import java.io.Writer;

public class ReportWriter {
    private String fileName;

    public ReportWriter(String fileName){
        this.fileName = fileName;
    }

    public void Write(DataSet dataSet, User user) {

        try (Writer writer = new FileWriter(fileName)) {

            Gson gson = new GsonBuilder().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("dataset id ", dataSet.getId());
            jsonObject.addProperty("dataset name", dataSet.getName());
            System.out.println("dataset id "+ dataSet.getId() +" dataset name"+ dataSet.getName());

            jsonObject.addProperty("\nUser name : ", user.getName());
            jsonObject.addProperty(" User ID : ", user.getId());
            System.out.println("User name: "+user.getName() + " User ID: "+ user.getId());
            //1
            jsonObject.addProperty("\nNumber of datasets assigned: ", user.getNumOfDatasets());
            System.out.println(" Number of datasets assigned: "+ user.getNumOfDatasets());
            //2
            for(int i=0 ;  i < user.listAllDatasets().size() ; i++){
                jsonObject.addProperty("\n" + user.listAllDatasets().get(i) + " completeness percentage: ", user.listAllDatasets().get(i).getCompleteness() );
                System.out.println(user.listAllDatasets().get(i) + " completeness percentage: "+ user.listAllDatasets().get(i).getCompleteness());
            }
            jsonObject.addProperty("\nTotal number of instances labeled :", user.getInstances()); //3
            System.out.println("\nTotal number of instances labeled :"+ user.getInstances());
            jsonObject.addProperty("\nTotal number of unique instances labeled :", user.getUniqueInstances()); //4
            System.out.println("\nTotal number of unique instances labeled :"+ user.getUniqueInstances());
            jsonObject.addProperty("\nConsistency percentage :", user.getConsistencyPercentage());//5
            System.out.println("\nConsistency percentage :"+user.getConsistencyPercentage());
            jsonObject.addProperty("\nAverage time spent in labeling an instance :", user.getAverageTimeSpending()); //6
            System.out.println("\nAverage time spent in labeling an instance :"+ user.getAverageTimeSpending());
            jsonObject.addProperty("\nStd. dev. of  time spent in labeling an instance : ", user.getStandardDeviation()); //7
            System.out.println("\nStd. dev. of  time spent in labeling an instance : "+ user.getStandardDeviation());



            gson.toJson(jsonObject, writer);

        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }
}
