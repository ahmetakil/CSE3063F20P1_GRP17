package GRP17.ReportWriters;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.UserModels.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

public class ReportWriter {

    private String reportName;
    private Gson gson;

    public ReportWriter(String rName) {

        gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        reportName = rName;
    }

    public void write(List<DataSet> dataSets, List<User> users, List<Instance> instances, List<AssignedInstance> assignedInstances){
        try (Writer writer = new FileWriter(reportName)) {
            JsonArray userArr = new JsonArray();
            JsonArray instanceArr = new JsonArray();
            JsonArray datasetArr = new JsonArray();

            UserWriter userWriter = new UserWriter(dataSets, assignedInstances);
            InstanceWriter instanceWriter = new InstanceWriter(assignedInstances);
            DatasetWriter datasetWriter = new DatasetWriter(assignedInstances);

            for (User user : users) {
                userWriter.setUser(user);
                userArr.add(userWriter.getMetrics());
            }
            for (Instance instance : instances) {
                instanceWriter.setInstance(instance);
                instanceArr.add(instanceWriter.getMetrics());
            }
            for (DataSet dataSet : dataSets) {
                datasetWriter.setDataSet(dataSet);
                datasetArr.add(datasetWriter.getMetrics());
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
