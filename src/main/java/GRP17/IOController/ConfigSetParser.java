package GRP17.IOController;

import GRP17.Models.ConfigSet;
import GRP17.UserModels.RandomLabellingUser;
import GRP17.UserModels.User;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigSetParser implements JsonDeserializer<ConfigSet> {

    @Override
    public ConfigSet deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {

        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("users");
            List<User> users = new ArrayList<User>();

            Iterator userIterator = jsonArray.iterator();

            while (userIterator.hasNext()) {

                JsonObject userObject = (JsonObject) userIterator.next();
                int id = userObject.get("userId").getAsInt();

                String name = userObject.get("userName").getAsString();
                String type = userObject.get("type").getAsString();

                User user;
                switch (type) {
                    case "RandomBot":
                        user = new RandomLabellingUser(id, name, type);
                        break;
                    case "MachineLearningBot":
                        user = null;
                        break;
                    default:
                        user = null;
                        break;

                }

                users.add(user);

            }
            return new ConfigSet(users);


        } catch (Exception e) {

            System.out.println("Something went wrong with CustomUserParser. Please check config.json");
            return null;
        }

    }

}
