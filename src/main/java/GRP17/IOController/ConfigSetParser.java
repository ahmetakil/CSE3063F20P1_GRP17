package GRP17.IOController;

import GRP17.Logger;
import GRP17.Models.ConfigSet;
import GRP17.Models.DataSet;
import GRP17.UserModels.HumanUser;
import GRP17.UserModels.LazyUser;
import GRP17.UserModels.RandomLabellingUser;
import GRP17.UserModels.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigSetParser implements JsonDeserializer<ConfigSet> {

    private List<User> users; // Storing users as an attribute because multiple functions will access it.
    private List<DataSet> datasets;

    @Override
    public ConfigSet deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {

        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray usersJsonArray = jsonObject.getAsJsonArray("users");
            JsonArray datasetsJsonArray = jsonObject.getAsJsonArray("datasets");

            Integer currentDatasetId = jsonObject.get("currentDatasetId").getAsInt();

            this.users = parseUsers(usersJsonArray);
            this.datasets = parseDatasets(datasetsJsonArray);

            DataSet currentDataset = null;
            for(DataSet dataset : datasets){
                if(dataset.getId().equals(currentDatasetId)){
                    currentDataset = dataset;
                }
            }

            return new ConfigSet(currentDataset);
        } catch (Exception e) {
            System.out.println("ConfigSetParser.deserialize: "+ e);
            return null;
        }
    }

    private List<User> parseUsers(JsonArray userJsonArray) {
        try {
            List<User> users = new ArrayList<User>();
            Iterator userIterator = userJsonArray.iterator();

            while (userIterator.hasNext()) {

                JsonObject userObject = (JsonObject) userIterator.next();
                int id = userObject.get("userId").getAsInt();

                String name = userObject.get("userName").getAsString();
                String type = userObject.get("type").getAsString();

                double consistencyCheckProbability = userObject.get("consistencyCheckProbability").getAsDouble();

                User user;
                switch (type) {
                    case "RandomBot":
                        user = new RandomLabellingUser(id, name, type, consistencyCheckProbability);
                        break;
                    case "Lazybot":
                        user = new LazyUser(id,name,type,consistencyCheckProbability);
                        break;
                    case "HumanUser":
                        user = new HumanUser(id,name,type,consistencyCheckProbability,userObject.get("password").getAsString());
                        break;
                    default:
                        user = null;
                        break;

                }
                if (user == null) {
                    continue;
                }
                users.add(user);
                Logger.getInstance().logUserCreation(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Something went wrong in config file please check your users");
            System.exit(0);
            return null;
        }
    }
    private List<DataSet> parseDatasets(JsonArray datasetJsonArray) {

        List<DataSet> datasets = new ArrayList<DataSet>();
        Iterator datasetIterator = datasetJsonArray.iterator();

        while (datasetIterator.hasNext()) {

            JsonObject datasetObject = (JsonObject) datasetIterator.next();

            String path = datasetObject.get("path").getAsString();
            List<Integer> userIds = getUserIds(datasetObject.get("users").getAsJsonArray());

            InputParser inputParser = new InputParser(path);

            List<User> datasetUsers = getUsersFromIds(userIds);


            DataSet dataSet = inputParser.parse();
            dataSet.addUsers(datasetUsers);

            datasets.add(dataSet);
        }
        return datasets;
    }


    private List<Integer> getUserIds(JsonArray userIdsJsonArray) {
        try {
            List<Integer> userIds = new ArrayList<>();

            Iterator userIdsIterator = userIdsJsonArray.iterator();

            while (userIdsIterator.hasNext()) {

                JsonPrimitive value = (JsonPrimitive) userIdsIterator.next();
                userIds.add(value.getAsInt());
            }
            return userIds;

        } catch (Exception e) {
            System.out.println("Something went wrong in config file please check your user ids");
            System.exit(0);
            return null;
        }
    }
    private List<User> getUsersFromIds(List<Integer> userIds) {
        List<User> selectedUsers = new ArrayList<>();

        for (Integer userId : userIds) {

            selectedUsers.add(getUserFromId(userId));
        }
        return selectedUsers;
    }
    private User getUserFromId(Integer userId) {
        for (User user : this.users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
}
