package GRP17;

import GRP17.IOController.Cache;
import GRP17.IOController.CacheManager;
import GRP17.IOController.OutputWriter;
import GRP17.IOController.ReportWriter;
import GRP17.Models.*;
import GRP17.UserModels.User;

import java.util.List;

class LabellingMechanism {

    private ConfigSet configSet;
    private ReportWriter reportWriter;
    private OutputWriter outputWriter;
    private CacheManager cacheManager;

    private List<DataSet> simulationDataSets;
    private List<AssignedInstance> simulationAssignedInstances;
    private List<User> simulationUsers;
    private List<Instance> simulationInstances;


    LabellingMechanism(ConfigSet configSet, ReportWriter reportWriter, CacheManager cacheManager, OutputWriter outputWriter) {
        this.configSet = configSet;
        this.reportWriter = reportWriter;
        this.outputWriter = outputWriter;
        this.cacheManager = cacheManager;

        Cache cache = cacheManager.readCache();

        this.simulationDataSets = cache.getDatasets();
        this.simulationAssignedInstances = cache.getAssignedInstances();
        this.simulationUsers = cache.getUsers();
        this.simulationInstances = cache.getInstances();

        DataSet dataSet = configSet.getCurrentDataset();
        dataSet.setId(configSet.getCurrentDatasetId());

        List<User> allUsersAssignedToCurrent = configSet.getUsers();

        List<Instance> allInstancesOfCurrentDataset = dataSet.getInstances();
        List<Label> allLabelsOfCurrentDataset = dataSet.getLabels();

        dataSet.setInstances(allInstancesOfCurrentDataset);
        dataSet.setUsers(allUsersAssignedToCurrent);
        dataSet.setLabels(allLabelsOfCurrentDataset);


        if (!contains(dataSet)) {
            simulationDataSets.add(dataSet);
        }
    }

    void startLabeling() {

        DataSet dataSet = configSet.getCurrentDataset();
        List<User> allUsersAssignedToCurrent = configSet.getUsers();
        List<Label> allLabelsOfCurrentDataset = dataSet.getLabels();
        List<Instance> allInstancesOfCurrentDataset = dataSet.getInstances();

        for (Instance instance : allInstancesOfCurrentDataset) {
            for (User user : allUsersAssignedToCurrent) {

                User cachedUser = getCachedUser(user);
                if (cachedUser != null) {
                    cachedUser.setConsistencyCheckProbability(user.getConsistencyCheckProbability());
                    user = cachedUser;
                }

                boolean consistency = (Math.random() < user.getConsistencyCheckProbability());
                AssignedInstance assignedInstance;

                if (consistency && user.hasLabelledInstance()) {
                    assignedInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                } else {
                    assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                }

                simulationAssignedInstances.add(assignedInstance);
                user.addDatasetID(dataSet.getId());

                if (!contains(user)) {
                    simulationUsers.add(user);
                }

                if (!contains(instance)) {
                    simulationInstances.add(instance);
                }


                reportWriter.Write(simulationDataSets, simulationUsers, simulationInstances, simulationAssignedInstances);

                Cache currentCache = new Cache(
                        simulationDataSets,
                        simulationAssignedInstances,
                        simulationUsers,
                        simulationInstances);

                cacheManager.saveCache(currentCache);
            }
        }
        outputWriter.write(simulationAssignedInstances, dataSet, allUsersAssignedToCurrent);
    }


    private boolean contains(DataSet dataSet) {
        for (DataSet loopDataset : simulationDataSets) {

            if (loopDataset.getId().equals(dataSet.getId())) {
                return true;
            }
        }
        return false;
    }


    private boolean contains(Instance instance) {
        for (Instance loopInstance : simulationInstances) {

            if (loopInstance.getId().equals(instance.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean contains(User user) {
        for (User loopUser : simulationUsers) {

            if (loopUser.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

    private User getCachedUser(User user) {
        for (User loopUser : simulationUsers) {

            if (loopUser.getId().equals(user.getId())) {
                return loopUser;
            }
        }
        return null;
    }
}
