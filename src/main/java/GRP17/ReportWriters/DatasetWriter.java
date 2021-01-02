package GRP17.ReportWriters;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Label;
import GRP17.UserModels.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class DatasetWriter implements Writable {
    DataSet dataSet;
    List<AssignedInstance> assignedInstances;

    public DatasetWriter(List<AssignedInstance> assignedInstances){
        this.assignedInstances = assignedInstances;

    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public JsonObject getMetrics() {
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
        Map<User, Double> usersWithCompletenessPercentage = metricController.getUsersWithCompletenessPercentageForDataset(dataSet, assignedInstances);

        jsonArray = new JsonArray();
        for (Map.Entry<User, Double> entry : usersWithCompletenessPercentage.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + entry.getValue() + "%");
        }
        jsonObject.add("List of users assigned and their completeness percentage: ", jsonArray);
        //6
        jsonArray = new JsonArray();
        Map<User, Double> userConsistencyPercentage = metricController.getListOfUsersWithConsistencyPercentage(assignedInstances, dataSet);
        for (Map.Entry<User, Double> entry : userConsistencyPercentage.entrySet()) {
            jsonArray.add(entry.getKey().getName() + ", " + entry.getValue() + "%");
        }
        jsonObject.add(" List of users assigned and their consistency percentage:  ", jsonArray);

        return jsonObject;
    }
}
