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


        List<AssignedInstance> allAssignedInstance = new ArrayList<>();

        for (Instance instance : allInstances) {

            for (User user : allUsers) {

                boolean consistency = (createRandomForConsistencyCheckProb() < user.getConsistencyCheckProbability() * 100);

                AssignedInstance assignedInstance;
                if (consistency) {
                    Random r = new Random();
                    int i = r.nextInt(user.getUniqueInstances().size());
                    Instance instanceI = user.getRandomLabelledInstance(i);
                    assignedInstance = user.assignLabel(instanceI, allLabels, dataSet.getMaxNumberLabels());
                    allAssignedInstance.add(assignedInstance);
                } else {

                    assignedInstance = user.assignLabel(instance, allLabels, dataSet.getMaxNumberLabels());
                    if (assignedInstance == null) {
                        // The labelling mechanism decided not to label
                        continue;
                    }

                }

                allAssignedInstance.add(assignedInstance);

                // TODO UPDATE INSTANCE PARAMETERS:
                assignedInstance.getInstance().determineFinalLabel();
                assignedInstance.getInstance().addUser(user);
                assignedInstance.getInstance().updateFrequencyLabelList(assignedInstance.getLabels());

                // TODO UPDATE USER PARAMETERS: ALREADY UPDATED AFTER CALLING assignlabel()




                String reportName = "assets/report.json";
                int reportNameCounter = 1;
                reportName.replace(' ', (char) (reportNameCounter + '0'));


                //updateMetrics();



                user.getNumOfDatasets();



                //writeToReportFile();
                ReportWriter reportWriter = new ReportWriter(reportName);
                reportWriter.Write(dataSet, user);
                reportNameCounter++;
            }

        }


        outputWriter.write(allAssignedInstance, dataSet, allUsers);


    }
}