package com.CSE3063F20P1.GRP17;

import com.CSE3063F20P1.GRP17.IOController.*;
import com.CSE3063F20P1.GRP17.UserModels.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Parser inputParser = new InputParser("input.json");
        Parser configParser = new ConfigParser("config.json");

        List<User> allUsers = ((ConfigParser) configParser).getUsers();
        DataSet dataSet = ((InputParser) inputParser).getDataset();

        List<Instance> allInstances = dataSet.getInstances();
        List<Label> allLabels = dataSet.getLabels();


        Logger logger = new Logger();
        OutputWriter outputWriter = new OutputWriter();

        List<AssignedInstance> allAssignedInstance = new ArrayList<AssignedInstance>();

        List<Instance> getInstancesToBeLabelled = allInstances;

        for (Instance instance : getInstancesToBeLabelled) {

            for (com.CSE3063F20P1.GRP17.UserModels.User user : allUsers) {

                AssignedInstance assignedInstance = user.assignLabel(instance, allLabels);
                logger.log(assignedInstance);
                allAssignedInstance.add(assignedInstance);

            }

        }

        outputWriter.write(allAssignedInstance,dataSet);

    }
}
