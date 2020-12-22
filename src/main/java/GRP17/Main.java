package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;
import GRP17.UserModels.User;

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

    public static void main(String[] args) {

        Parser configParser = new ConfigParser("assets/config.json");

        OutputWriter outputWriter = new OutputWriter("assets/output.json");


        ConfigSet configSet = ((ConfigParser) configParser).parse();
        DataSet dataSet = configSet.getCurrentDataset();

        List<User> allUsers = configSet.getUsers();

        List<Instance> allInstances = dataSet.getInstances();
        List<Label> allLabels = dataSet.getLabels();

        //TODO need to fill with parsing output.json
        List<AssignedInstance> allAssignedInstance = new ArrayList<>();

        for (Instance instance : allInstances) {

            for (User user : allUsers) {

                boolean consistency = (createRandomForConsistencyCheckProb() < user.getConsistencyCheckProbability() * 100);

                AssignedInstance  assignedInstance = user.assignLabel(instance, allLabels, dataSet.getMaxNumberLabels());

                if (consistency) {

                    AssignedInstance consistencyLabelledInstance = user.relabelAlreadyLabelledInstance(allLabels,dataSet.getMaxNumberLabels());
                    allAssignedInstance.add(consistencyLabelledInstance);
                }

                allAssignedInstance.add(assignedInstance);

                // TODO UPDATE INSTANCE PARAMETERS:
                assignedInstance.getInstance().determineFinalLabel();
                assignedInstance.getInstance().addUser(user);
                assignedInstance.getInstance().updateFrequencyLabelList(assignedInstance.getLabels());
                for (Instance datasetInstance : dataSet.getInstances()){
                    if(datasetInstance.getId() == assignedInstance.getInstance().getId()){
                        datasetInstance.determineFinalLabel();
                        datasetInstance.addUser(user);
                        datasetInstance.updateFrequencyLabelList(assignedInstance.getLabels());
                        break;
                    }
                }
                // TODO UPDATE USER PARAMETERS: ALREADY UPDATED AFTER CALLING assignlabel()



                int reportId = configSet.getCurrentDataset().getId();
                String reportName = "assets/report"+reportId+".json";


                //updateMetrics();



                user.getNumberOfDatasets();



                //writeToReportFile();
                ReportWriter reportWriter = new ReportWriter(reportName);
                reportWriter.Write(dataSet, allUsers, instance);
            }

        }


        outputWriter.write(allAssignedInstance, dataSet, allUsers);


    }
}