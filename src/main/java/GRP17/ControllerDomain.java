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
        List<DataSet> userDatasets = getDatasetsFromIDS(user.getDatasetIds(), allDatasets);
        Map<DataSet, Double> consistencies = new HashMap<>();

        for (DataSet dataset : userDatasets) {
            consistencies.put(dataset, dataset.getCompleteness());

        }
        return consistencies;
    }

    public Double getCompletenessPercentage(DataSet dataSet, User user, List<AssignedInstance> allAssignedInstances) {
        List<Instance> datasetInstances = dataSet.getInstances();
        List<Instance> userInstances = getUniqueInstancesForUser(user, allAssignedInstances);

        double count = 0;
        for (Instance instance : userInstances) {
            if (datasetInstances.contains(instance)) {
                count += 1;
            }
        }

        return (count / datasetInstances.size()) * 100;
    }


    public List<DataSet> getDatasetsFromIDS(List<Integer> datasetIDS, List<DataSet> allDatasets) {
        List<DataSet> datasets = new ArrayList<>();

        for (DataSet dataSet : allDatasets) {
            if (datasetIDS.contains(dataSet.getId())) {
                datasets.add(dataSet);
            }

        }
        return datasets;
    }


    public List<Instance> getUniqueInstancesForUser(User user, List<AssignedInstance> allAssignedInstances) {
        ArrayList<Instance> Instances = new ArrayList<>();
        for (AssignedInstance assignedInstance : allAssignedInstances) {
            if (assignedInstance.getUser().getId() == user.getId()) {
                if (!Instances.contains(assignedInstance.getInstance())) {
                    Instances.add(assignedInstance.getInstance());
                }
            }
        }
        return Instances;
    }

    // [C-5]
    public Map<User, Double> getUsersWithCompletenessPercentageForDataset(DataSet dataSet, List<AssignedInstance> allAssignedInstances) {
        Map<User, Double> completenessPercentages = new HashMap<>();
        for (User user : dataSet.getUsers()) {
            completenessPercentages.put(user, getCompletenessPercentage(dataSet, user, allAssignedInstances));
        }
        return completenessPercentages;
    }

    public Map<User, Double> getListOfUsersWithConsistencyPercentage(List<AssignedInstance> allAssignedInstances, DataSet dataSet) {
        // [C-6]
        List<User> users = dataSet.getUsers();
        Map<User, Double> consistencyPercentage = new HashMap<>();

        for (User user : users) {
            consistencyPercentage.put(user, getConsistencyPercentage(allAssignedInstances, user));
        }

        return consistencyPercentage;
    }
}
