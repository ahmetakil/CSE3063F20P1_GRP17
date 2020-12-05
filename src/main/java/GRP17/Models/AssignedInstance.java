package GRP17.Models;

import GRP17.Logger;
import GRP17.UserModels.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AssignedInstance {

    private Instance instance;
    private List<Label> labels;
    private User user;
    private Date date;

    public AssignedInstance(User user, Instance instance, List<Label> labels, Date date) {
        this.instance = instance;
        this.labels = labels;
        this.date = date;
        this.user = user;
        Logger.getInstance().logLabelAssignment(this);
    }

    public Instance getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public List<Label> getLabels() {
        return this.labels;
    }

    public JsonObject toJson() {
        /*
        Used in OutputWriter to properly format the json output.
         */
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("instance id", instance.getId());


        JsonArray labelIdsJson = new JsonArray();

        for (Label label : this.labels) {
            labelIdsJson.add(label.getId());
        }

        jsonObject.add("class labels id", labelIdsJson);
        jsonObject.addProperty("user id", this.user.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss");
        jsonObject.addProperty("datetime", dateFormat.format(date));
        return jsonObject;
    }

    public String toString() {
        return "AssignedInstance <" + instance.toString() + Arrays.toString(this.labels.toArray()) + ">";
    }
}
