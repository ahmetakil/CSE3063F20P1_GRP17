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

        // TODO: ÖNCEKİ SİMULASYONUN OUTPUTUNU OKU. CURRENTLARA ATTRIBUTELARIYLA BİRLİKTE EKLE.


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

        for (Instance instance : allInstancesOfCurrentDataset) {
            for (User user : allUsersOfCurrentDataset) {

                boolean consistency = (Math.random() < user.getConsistencyCheckProbability());

                AssignedInstance assignedInstance;

                if (consistency && user.hasLabelledInstance()) {
                    assignedInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                } else {
                    assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                }


                currentAssignedInstances.add(assignedInstance);

                if (!currentUsers.contains(user))
                    currentUsers.add(user);

                if (!currentInstances.contains(instance)) {
                    currentInstances.add(instance);
                }


                user.addDatasetID(dataSet.getId());

                // TODO: CURRENTLARI OUTPUTA ATTRUBUTELARIYLA BIRLIKTE YAZDIR.


                reportWriter.Write(currentDataSets, currentUsers, currentInstances, currentAssignedInstances);


            }

        }


        outputWriter.write(currentAssignedInstances, dataSet, allUsersOfCurrentDataset);


    }
}