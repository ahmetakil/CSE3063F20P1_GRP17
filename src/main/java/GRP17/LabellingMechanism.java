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


    LabellingMechanism(ConfigSet configSet, ReportWriter reportWriter, CacheManager cacheManager,  OutputWriter outputWriter) {
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

        if (!simulationDataSets.contains(dataSet)) {
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

                boolean consistency = (Math.random() < user.getConsistencyCheckProbability());
                AssignedInstance assignedInstance;

                if (consistency && user.hasLabelledInstance()) {
                    assignedInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                } else {
                    assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                }

                simulationAssignedInstances.add(assignedInstance);

                if (!simulationUsers.contains(user)) {
                    simulationUsers.add(user);
                }


                if (!simulationInstances.contains(instance)) {
                    simulationInstances.add(instance);
                }

                user.addDatasetID(dataSet.getId());

                reportWriter.Write(simulationDataSets, simulationUsers, simulationInstances, simulationAssignedInstances);

                Cache currentCache = new Cache(
                        simulationDataSets,
                        simulationAssignedInstances,
                        simulationUsers,
                        simulationInstances
                );

                cacheManager.saveCache(currentCache);
            }
        }

        outputWriter.write(simulationAssignedInstances, dataSet, allUsersAssignedToCurrent);

    }
}
