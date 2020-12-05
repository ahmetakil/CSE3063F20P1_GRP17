package GRP17.IOController;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class OutputWriter {

    private String fileName;

    public OutputWriter(String fileName){
        this.fileName = fileName;
    }

    public void write(List<AssignedInstance> assignedInstances, DataSet dataSet,List<User> users) {


        try (Writer writer = new FileWriter(fileName)) {

            Gson gson = new GsonBuilder().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("dataset id ", dataSet.getId());
            jsonObject.addProperty("dataset name", dataSet.getName());
            jsonObject.addProperty("maximum number of labels per instance", dataSet.getMaxNumberLabels());


            List<Label> labels = dataSet.getLabels();
            JsonArray classLabelsJson = gson.toJsonTree(labels).getAsJsonArray();
            jsonObject.add("class labels",classLabelsJson );


            List<Instance> instances = dataSet.getInstances();
            JsonArray instancesJson = gson.toJsonTree(instances).getAsJsonArray();
            jsonObject.add("instances",instancesJson );

            JsonArray assignedInstancesArray = new JsonArray();

            for(AssignedInstance assignedInstance : assignedInstances){
                assignedInstancesArray.add(assignedInstance.toJson());
            }
            jsonObject.add("class label assignments",  assignedInstancesArray);


            JsonArray usersJson = gson.toJsonTree(users).getAsJsonArray();
            jsonObject.add("users", usersJson);

            gson.toJson(jsonObject, writer);

        } catch (IOException e) {
            System.out.println("Something went wrong with OutputWriter, Please check your input files. ");
            return;
        }

    }


}