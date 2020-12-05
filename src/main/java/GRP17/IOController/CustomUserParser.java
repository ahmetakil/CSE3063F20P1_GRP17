package GRP17.IOController;

import GRP17.UserModels.RandomLabellingUser;
import GRP17.UserModels.User;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

public class CustomUserParser implements JsonDeserializer<User> {

    @Override
    public User deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {

        try {
            JsonObject jobject = json.getAsJsonObject();
            int id = jobject.get("userId").getAsInt();

            String name = jobject.get("userName").getAsString();
            String type = jobject.get("type").getAsString();


            switch (type) {
                case "RandomBot":
                    return new RandomLabellingUser(id, name, type);
                case "MachineLearningBot":
                    return null;
                default:
                    return null;

            }


        } catch (Exception e) {

            System.out.println("Something went wrong with CustomUserParser. Please check config.json");
            System.exit(0);
            return null;
        }

    }

}
