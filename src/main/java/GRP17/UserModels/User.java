package GRP17.UserModels;

import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public abstract class User {

    @SerializedName("user id")private int id;
    @SerializedName("user name")private String name;
    @SerializedName("user type")private String type;

    /*
    Holds all of the assignments a user makes, might be useful in the future.
     */
    private List <AssignedInstance> labellingRequests;

    public User(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() { return name; }

    public List<AssignedInstance> getLabellingRequests() {
        return labellingRequests;
    }

    public abstract AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance);
}