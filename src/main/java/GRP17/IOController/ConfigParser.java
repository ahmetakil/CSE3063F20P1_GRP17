package GRP17.IOController;

import GRP17.Models.ConfigSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ConfigParser extends Parser {


    public ConfigParser(String fileName) {
        super(fileName);
    }



    @Override
    public ConfigSet parse() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        /*
        The registerTyeAdapter allows us to customize the data parsing process
        by providing a Custom JsonDeserializer
         */
        gsonBuilder.registerTypeAdapter(ConfigSet.class, new ConfigSetParser());

        try (Reader reader = new FileReader(fileName)) {

            Gson gson = gsonBuilder.create();
            ConfigSet configSet = gson.fromJson(reader, ConfigSet.class);
            return configSet;

        } catch (IOException e) {

            System.out.println("Something went wrong, please check your config file!!!!");
            return null;
        }

    }
}
