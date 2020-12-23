package GRP17;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;

import java.util.*;

public class ControllerDomain {

    //B-3
    //This method returns size of the list of the user that labelled this particular instance
    public int noOfUniqueUsersForInstance(List<AssignedInstance> allAssignedInstances, Instance instance) {
        //TODO ZEYNEP
        List<User> users = new ArrayList<>();
        for (AssignedInstance assignedInstance : allAssignedInstances) {

            if (assignedInstance.getInstance().getId() == instance.getId()) {
                User user = assignedInstance.getUser();
                if (!users.contains(user)) {
                    users.add(user);
                }
            }
        }
        return users.size();

    }

    //A-5
    public double getConsistencyPercentage(List<AssignedInstance> assignedInstances, User user) {
        Map<Label, Integer> labelCount = new HashMap<>();
        for (AssignedInstance assignedInstance : assignedInstances) {
            if (assignedInstance.getUser().getId() == user.getId()) {
                List<Label> labels = assignedInstance.getLabels();
                for (Label label : labels) {
                    if (labelCount.containsKey(label)) {
                        labelCount.put(label, labelCount.get(label) + 1);
                    } else {
                        labelCount.put(label, 1);
                    }
                }
            }
        }
        Map.Entry<Label, Integer> maxEntry = null;

        double sum = 0;
        for (Map.Entry<Label, Integer> entry : labelCount.entrySet()) {
            sum += entry.getValue();
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null)
            return 0;

        return (maxEntry.getValue() / sum) * 100;
    }


    //A-2
    public Map<DataSet, Double> listUsersDatasetWithCompletenessPercentage(List<DataSet> allDatasets, User user) {

        List<DataSet> userDatasets =  getDatasetsFromIDS(user.getDatasetIds(), allDatasets);
        Map<DataSet, Double> consistencies = new HashMap<>();

        for(DataSet dataset: userDatasets){
            consistencies.put(dataset, dataset.getCompleteness());

    }


        return consistencies;
}


    // RETURN DATASETS FROM DATASET_ID LIST

    public List<DataSet> getDatasetsFromIDS(List<Integer> datasetIDS, List<DataSet> allDatasets) {
        List<DataSet> datasets = new ArrayList<>();

        for (DataSet dataSet : allDatasets) {
            if (datasetIDS.contains(dataSet.getId())) {
                datasets.add(dataSet);
            }

        }
        return datasets;
    }


    public ArrayList<Instance> getUniqueInstances(User user, List<AssignedInstance> allAssignedInstances) {
        List<AssignedInstance> assignedInstances = getAssignInstancesForUser(user, allAssignedInstances);

        ArrayList<Instance> uniqueInstances = new ArrayList<>();


        for (AssignedInstance assignedInstance : assignedInstances) {
            if (!uniqueInstances.contains(assignedInstance.getInstance())) {
                uniqueInstances.add(assignedInstance.getInstance());
            }
        }
        return uniqueInstances;
    }

    public List<AssignedInstance> getAssignInstancesForUser(User user, List<AssignedInstance> allAssignedInstances) {
        List<AssignedInstance> assignedInstances = new ArrayList<>();
        for (AssignedInstance assignedInstance : allAssignedInstances) {
            if (assignedInstance.getUser().getId() == user.getId()) {
                allAssignedInstances.add(assignedInstance);
            }
        }

        return assignedInstances;
    }

    public Map<User, Integer> getUniqueInstancesForEachUser(DataSet dataSet, List<AssignedInstance> allAssignedInstance) {
        //TODO [C-5]
        List<User> users = dataSet.getUsers();
        Map<User, Integer> unique = new HashMap<User, Integer>();
        for (User user : users) {
            unique.put(user, getUniqueInstances(user, allAssignedInstance).size());
        }
        return unique;
    }

    public Map<User, Double> getListOfUsersWithConsistencyPercentage(List<AssignedInstance> allAssignedInstances, DataSet dataSet) {
        //TODO [C-6]

        List<User> users = dataSet.getUsers();
        Map<User, Double> consistencyPercentage = new HashMap<>();

        for (User user : users) {
            consistencyPercentage.put(user, getConsistencyPercentage(allAssignedInstances, user));
        }

        return consistencyPercentage;
    }


}
