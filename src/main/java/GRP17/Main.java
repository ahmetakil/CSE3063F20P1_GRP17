package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;
import GRP17.UserModels.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Parser configParser = new ConfigParser("assets/config.json");

        OutputWriter outputWriter = new OutputWriter("assets/output.json");


        ConfigSet configSet = ((ConfigParser) configParser).parse();
        DataSet dataSet = configSet.getCurrentDataset();

        List<User> allUsers = configSet.getUsers();

        List<Instance> allInstances = dataSet.getInstances();
        List<Label> allLabels = dataSet.getLabels();


        List<AssignedInstance> allAssignedInstance = new ArrayList<AssignedInstance>();

        List<Instance> getInstancesToBeLabelled = allInstances;

        for (Instance instance : getInstancesToBeLabelled) {

            for (User user : allUsers) {
                String reportName = "assets/report- .json";
                int reportNameCounter = 1;
                reportName.replace(' ', (char)(reportNameCounter + '0'));

                AssignedInstance assignedInstance = user.assignLabel(instance, allLabels, dataSet.getMaxNumberLabels());

                if(assignedInstance == null){
                    // The labelling mechanism decided not to label
                    continue;
                }

                allAssignedInstance.add(assignedInstance);
                assignedInstance.getInstance().determineFinalLabel();



                //updateMetrics();

                //writeToReportFile();
                ReportWriter reportWriter = new ReportWriter(reportName);
                reportWriter.Write(dataSet,user);
                reportNameCounter++;
            }

        }


        outputWriter.write(allAssignedInstance,dataSet,allUsers);

    }
}