package GRP17.Models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSet {
    @SerializedName("dataset id") private int id;
    @SerializedName("dataset name") private String name;
    @SerializedName("maximum number of labels per instance") private int MAX_NUMBER_OF_LABELS_PER_INSTANCE;
    @SerializedName("class labels") private List<Label> labels;
    private List<Instance> instances; // Since the json key name is also the same we can skip the annotation

    public DataSet(int id, String name, int MAX_NUMBER_OF_LABELS_PER_INSTANCE) {
        this.id = id;
        this.name = name;
        this.MAX_NUMBER_OF_LABELS_PER_INSTANCE = MAX_NUMBER_OF_LABELS_PER_INSTANCE;
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
