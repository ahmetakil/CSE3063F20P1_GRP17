package GRP17.Models;

import GRP17.UserModels.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSet {

    @SerializedName("dataset id")
    private int id;
    @SerializedName("dataset name")
    private String name;
    @SerializedName("maximum number of labels per instance")
    private int MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    @SerializedName("class labels")
    private List<Label> labels;
    private List<Instance> instances; // Since the json key name is also the same we can skip the annotation
    private List<User> users; //TODO

    public double getCompleteness(){
    //TODO [C-1] Loop the instances use isEmpty calculate percantage.
        return 0;
    }

    public double getClassDistributionWithRespectToFinalLabels(){
        //TODO [C-2] Loop through the instances use final labels.
        return 0;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberLabels() {
        return MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public List<Instance> getInstances() {
        return instances;
    }


}
