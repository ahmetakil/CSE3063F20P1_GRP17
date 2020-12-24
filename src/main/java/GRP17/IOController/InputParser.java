package GRP17.IOController;

import GRP17.Models.DataSet;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class InputParser extends Parser {
    public InputParser(String fileName) {
        super(fileName);
    }

    public DataSet parse() {
        Gson gson = new Gson();
        String fullPath = "assets/"+fileName;

        try (Reader reader = new FileReader(fullPath)) {

            DataSet dataset = gson.fromJson(reader, DataSet.class);
            return dataset;

        } catch (IOException e) {
            System.out.println("Something went wrong, please check your config file!!!!");
            return null;
        }
    }
}

