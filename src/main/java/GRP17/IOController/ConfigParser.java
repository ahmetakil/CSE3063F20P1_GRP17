package GRP17.IOController;

import GRP17.UserModels.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConfigParser extends Parser {


    public ConfigParser(String fileName){
        super(fileName);
    }


    @Override
    public List<User> parse() {

        try (Reader reader = new FileReader(fileName)) {

            GsonBuilder gsonBuilder = new GsonBuilder();

            /* The CustomUserParser allows us to dynamically construct subtypes of user class
            with respect to the type value in the input file */
            gsonBuilder.registerTypeAdapter(User.class, new CustomUserParser());
            Gson gson = gsonBuilder.create();

            // The TypeToken that allows us to parse the input to a list of User instances.
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();

            List<User> users = gson.fromJson(reader, listType);
            return users;

        } catch (IOException e) {
            System.out.println("Something went wrong with ConfigParser.parse please check your config file.");
            System.exit(0);
            return new ArrayList<>();
        }

}
}
