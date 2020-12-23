package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;
import GRP17.UserModels.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {


    public static int createRandomForConsistencyCheckProb() {
        Random r = new Random();
        int low = 0;
        int high = 100;
        return r.nextInt(high - low) + low;
    }

    public static void main(String[] args) throws IOException {

        Parser configParser = new ConfigParser("assets/config.json");

        OutputWriter outputWriter = new OutputWriter("assets/output.json");


        ConfigSet configSet = ((ConfigParser) configParser).parse();

        int reportId = configSet.getCurrentDataset().getId();
        String reportName = "assets/report" + reportId + ".json";
        ReportWriter reportWriter = new ReportWriter(reportName);


        List<DataSet> currentDataSets = new ArrayList<>();
        List<AssignedInstance> currentAssignedInstances = new ArrayList<>();
        List<User> currentUsers = new ArrayList<>();
        List<Instance> currentInstances = new ArrayList<>();

        DataSet dataSet = configSet.getCurrentDataset();
        dataSet.setId(configSet.getCurrentDatasetId());

        List<User> allUsersOfCurrentDataset = configSet.getUsers();
        List<Instance> allInstancesOfCurrentDataset = dataSet.getInstances();
        List<Label> allLabelsOfCurrentDataset = dataSet.getLabels();

        dataSet.setInstances(allInstancesOfCurrentDataset);
        dataSet.setUsers(allUsersOfCurrentDataset);
        dataSet.setLabels(allLabelsOfCurrentDataset);

        if (!currentDataSets.contains(dataSet))
            currentDataSets.add(dataSet);

        //TODO need to fill with parsing output.json

        for (Instance instance : allInstancesOfCurrentDataset) {

            for (User user : allUsersOfCurrentDataset) {

                boolean consistency = (createRandomForConsistencyCheckProb() < user.getConsistencyCheckProbability() * 100);

                AssignedInstance assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                if (!currentUsers.contains(user))
                    currentUsers.add(user);


                if (!currentInstances.contains(instance)) {
                    currentInstances.add(instance);
                }

                if (consistency) {

                    AssignedInstance consistencyLabelledInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                    currentAssignedInstances.add(consistencyLabelledInstance);
                }

                currentAssignedInstances.add(assignedInstance);

                // TODO UPDATE USER PARAMETERS: ALREADY UPDATED AFTER CALLING assignlabel()
                user.addDatasetID(dataSet.getId());


                // TODO UPDATE INSTANCE PARAMETERS:  ALREADY UPDATED AFTER CALLING assignlabel()


                // TODO WRITE METRICS TO REPORT:
                reportWriter.Write(dataSet, currentUsers, currentInstances, currentDataSets, currentAssignedInstances);
            }

        }


        outputWriter.write(currentAssignedInstances, dataSet, allUsersOfCurrentDataset);


    }
}