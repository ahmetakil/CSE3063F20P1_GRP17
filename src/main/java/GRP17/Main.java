package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;
import GRP17.UserModels.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {

        Parser configParser = new ConfigParser("assets/config.json");

        OutputWriter outputWriter = new OutputWriter("assets/output.json");


        ConfigSet configSet = ((ConfigParser) configParser).parse();

        int reportId = configSet.getCurrentDataset().getId();
        String reportName = "assets/report" + reportId + ".json";
        ReportWriter reportWriter = new ReportWriter(reportName);


        List<DataSet> simulationDataSets = new ArrayList<>();
        List<AssignedInstance> simulationAssignedInstances = new ArrayList<>();
        List<User> simulationUsers = new ArrayList<>();
        List<Instance> simulationInstances = new ArrayList<>();

        // TODO: ÖNCEKİ SİMULASYONUN OUTPUTUNU OKU. CURRENTLARA ATTRIBUTELARIYLA BİRLİKTE EKLE.

        DataSet dataSet = configSet.getCurrentDataset();
        dataSet.setId(configSet.getCurrentDatasetId());

        List<User> allUsersAssignedToCurrent = configSet.getUsers();

        List<Instance> allInstancesOfCurrentDataset = dataSet.getInstances();
        List<Label> allLabelsOfCurrentDataset = dataSet.getLabels();

        dataSet.setInstances(allInstancesOfCurrentDataset);
        dataSet.setUsers(allUsersAssignedToCurrent);
        dataSet.setLabels(allLabelsOfCurrentDataset);

        if (!simulationDataSets.contains(dataSet)) {
            simulationDataSets.add(dataSet);
        }


        for (Instance instance : allInstancesOfCurrentDataset) {
            for (User user : allUsersAssignedToCurrent) {
                boolean consistency = (Math.random() < user.getConsistencyCheckProbability());
                AssignedInstance assignedInstance;
                if (consistency && user.hasLabelledInstance()) {
                    assignedInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                } else {
                    assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                }

                simulationAssignedInstances.add(assignedInstance);
                if (!simulationUsers.contains(user))
                    simulationUsers.add(user);

                if (!simulationInstances.contains(instance)) {
                    simulationInstances.add(instance);
                }

                user.addDatasetID(dataSet.getId());
                // TODO: CURRENTLARI OUTPUTA ATTRUBUTELARIYLA BIRLIKTE YAZDIR.

                reportWriter.Write(simulationDataSets, simulationUsers, simulationInstances, simulationAssignedInstances);

            }
        }

        outputWriter.write(simulationAssignedInstances, dataSet, allUsersAssignedToCurrent);

    }
}