package GRP17;

import GRP17.IOController.ConfigParser;
import GRP17.IOController.InputParser;
import GRP17.IOController.OutputWriter;
import GRP17.IOController.Parser;
import GRP17.Models.*;
import GRP17.UserModels.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        Parser inputParser = new InputParser("assets/input.json");
        Parser configParser = new ConfigParser("assets/config.json");

        OutputWriter outputWriter = new OutputWriter("assets/output.json");

        ConfigSet configSet = ((ConfigParser) configParser).parse();
        DataSet dataSet = ((InputParser) inputParser).parse();

        List<User> allUsers = configSet.getUsers();

        List<Instance> allInstances = dataSet.getInstances();
        List<Label> allLabels = dataSet.getLabels();


        List<AssignedInstance> allAssignedInstance = new ArrayList<AssignedInstance>();

        List<Instance> getInstancesToBeLabelled = allInstances;

        for (Instance instance : getInstancesToBeLabelled) {

            for (User user : allUsers) {

                AssignedInstance assignedInstance = user.assignLabel(instance, allLabels, dataSet.getMaxNumberLabels());
                allAssignedInstance.add(assignedInstance);
            }

        }
        outputWriter.write(allAssignedInstance,dataSet,allUsers);

    }
}