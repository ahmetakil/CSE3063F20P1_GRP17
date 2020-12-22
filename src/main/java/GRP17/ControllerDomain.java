package GRP17;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.Models.Label;
import GRP17.UserModels.User;

import java.util.*;

public class ControllerDomain {

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
    public Map<DataSet, Double> listUsersDatasetWithCompletenessPercentage(List<DataSet> datasets, User user) {
        Map<DataSet, Double> consistencies = new HashMap<>();

        for (DataSet dataSet : datasets) {
            int counter = 0;
            for (Instance instance : dataSet.getInstances()) {
                if (instance.getLabelledUsers().contains(user)) {
                    counter++;
                }
            }
            consistencies.put(dataSet, (double) counter / (double) (dataSet.getInstances().size()));
        }


        return consistencies;
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

        return allAssignedInstances;
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
