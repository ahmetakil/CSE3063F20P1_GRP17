package GRP17.ReportWriters;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.UserModels.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class UserWriter implements Writable {

    private User user;
    private List<DataSet> dataSets;
    private List<AssignedInstance> assignedInstances;

     UserWriter(List<DataSet> dataSets, List<AssignedInstance> assignedInstances){
         this.dataSets = dataSets;
         this.assignedInstances = assignedInstances;
     }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public JsonObject getMetrics() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user id: ", user.getId());
        jsonObject.addProperty("user name: ", user.getName());
        System.out.println("\nUSER METRICS");
        System.out.println("user id : " + user.getId() + " user name: " + user.getName());
        //1
        jsonObject.addProperty("Number of datasets: ", user.getNumberOfDatasets());
        System.out.println("Number of datasets: " + user.getNumberOfDatasets());
        //2
        JsonArray jsonArray = new JsonArray();
        for (Map.Entry<DataSet, Double> dataSetMap : metricController.listUsersDatasetWithCompletenessPercentage(dataSets, user).entrySet()) {
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
        jsonObject.addProperty("Consistency percentage :", metricController.getConsistencyPercentage(assignedInstances, user));
        System.out.println("Consistency percentage :" + metricController.getConsistencyPercentage(assignedInstances, user));
        //6
        jsonObject.addProperty("Average time spent in labeling an instance :", (int)(user.getAverageTimeSpending()*100000)/100000.0);
        System.out.println("Average time spent in labeling an instance :" + (int)(user.getAverageTimeSpending()*100000)/100000.0);
        //7
        jsonObject.addProperty("Std. dev. of  time spent in labeling an instance : ", (int)(user.getStandardDeviation()*100000)/100000.0);
        System.out.println("Std. dev. of  time spent in labeling an instance : " + (int)(user.getStandardDeviation()*100000)/100000.0);

        return jsonObject;
    }
}
