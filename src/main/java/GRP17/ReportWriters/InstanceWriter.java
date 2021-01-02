package GRP17.ReportWriters;

import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public class InstanceWriter implements Writable {
    private Instance instance;
    private List<AssignedInstance> assignedInstances;

    InstanceWriter(List<AssignedInstance> assignedInstances){
        this.assignedInstances = assignedInstances;

    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }


    public JsonObject getMetrics() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("instance id: ", instance.getId());
        jsonObject.addProperty("instance: ", instance.getInstance());
        jsonObject.addProperty("Total number of label assignments: ", instance.noOfLabelAssignments()); //1
        jsonObject.addProperty("Number of unique label assignments: ", instance.noOfUniqueLabelAssignments());//2
        jsonObject.addProperty("Number of unique users: ", metricController.noOfUniqueUsersForInstance(assignedInstances, instance)); //3
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



}
