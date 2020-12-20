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

    public void Write(DataSet dataSet, User user, AssignedInstance assignedInstances) {

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
            //2
            jsonObject.addProperty("  Total number of instances labeled :", user.getInstances()); //3
            System.out.print("  Total number of instances labeled :"+ user.getInstances());
            jsonObject.addProperty("  Total number of unique instances labeled :", user.getUniqueInstances()); //4
            System.out.print("  Total number of unique instances labeled :"+ user.getUniqueInstances());
            jsonObject.addProperty("  Consistency percentage :", user.getConsistencyPercentage());//5
            System.out.print("  Consistency percentage :"+user.getConsistencyPercentage());
            jsonObject.addProperty("  Average time spent in labeling an instance :", user.getAverageTimeSpending()); //6
            System.out.print("  Average time spent in labeling an instance :"+ user.getAverageTimeSpending());
            jsonObject.addProperty(" Std. dev. of  time spent in labeling an instance : ", user.getStandardDeviation()); //7
            System.out.print(" Std. dev. of  time spent in labeling an instance : "+ user.getStandardDeviation());



            gson.toJson(jsonObject, writer);

        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }
    }
}
