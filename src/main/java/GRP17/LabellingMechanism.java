package GRP17;

import GRP17.IOController.Cache;
import GRP17.IOController.CacheManager;
import GRP17.IOController.OutputWriter;
import GRP17.Models.*;
import GRP17.UserModels.User;
import GRP17.ReportWriters.ReportWriter;

import java.util.ArrayList;
import java.util.List;

class LabellingMechanism {

    private ReportWriter reportWriter;
    private OutputWriter outputWriter;
    private CacheManager cacheManager;

    private List<DataSet> simulationDataSets;
    private List<AssignedInstance> simulationAssignedInstances;
    private List<User> simulationUsers;
    private List<Instance> simulationInstances;

    private DataSet dataSet;


    LabellingMechanism(ConfigSet configSet, ReportWriter reportWriter, CacheManager cacheManager, OutputWriter outputWriter) {
        this.reportWriter = reportWriter;
        this.outputWriter = outputWriter;
        this.cacheManager = cacheManager;

        Cache cache = cacheManager.readCache();

        this.simulationDataSets = cache.getDatasets();
        this.simulationAssignedInstances = cache.getAssignedInstances();
        this.simulationUsers = cache.getUsers();
        this.simulationInstances = cache.getInstances();


        DataSet configDataset = configSet.getCurrentDataset();
        Integer datasetId = configDataset.getId();

        for (DataSet dataSet : simulationDataSets) {
            if (dataSet.getId().equals(datasetId)) {
                dataSet.updateDataset(configDataset);
                this.dataSet = dataSet;
            }
        }


        if (!simulationDataSets.contains(configDataset)) {
            simulationDataSets.add(configDataset);
        }

        if (this.dataSet == null) {
            this.dataSet = configSet.getCurrentDataset();
        }

    }

    void startLabeling() {

        List<User> allUsersAssignedToCurrent = new ArrayList<>();


        if (LoginController.getInstance().isLoggedIn()) {
            allUsersAssignedToCurrent.add(LoginController.getInstance().getUser());
        } else {
            allUsersAssignedToCurrent = dataSet.getBotUsers();
        }

        List<Label> allLabelsOfCurrentDataset = dataSet.getLabels();
        List<Instance> allInstancesOfCurrentDataset = dataSet.getInstances();

        List<Instance> allUnlabelledInstancesOfCurrent = new ArrayList<>();
        for (Instance instance : allInstancesOfCurrentDataset) {

            if (instance.getFrequency().isEmpty()) {
                allUnlabelledInstancesOfCurrent.add(instance);
            }
        }

        for (Instance instance : allInstancesOfCurrentDataset) {
            Instance cachedInstance = getCachedInstance(instance);
            if (cachedInstance != null) {
                instance.setFrequency(cachedInstance.getFrequency());
            }
        }


        for (User user : allUsersAssignedToCurrent) {
            User cachedUser = getCachedUser(user);
            if (cachedUser != null) {
                user.setUserFields(cachedUser);
            }
        }

        DataSet cachedDataset = getCachedDataset(dataSet);
        if (cachedDataset != null) {
            dataSet.setFields(cachedDataset);
        }


        for (User configUser : allUsersAssignedToCurrent) {
            for (User simulationUser : simulationUsers) {

                if (configUser.getId().equals(simulationUser.getId())) {
                    configUser.setUserFields(simulationUser);
                }
            }
        }

        for (Instance instance : allUnlabelledInstancesOfCurrent) {
            for (User user : allUsersAssignedToCurrent) {


                boolean consistency = (Math.random() < user.getConsistencyCheckProbability());
                AssignedInstance assignedInstance;

                if (consistency && user.hasLabelledInstance(dataSet)) {
                    assignedInstance = user.relabelAlreadyLabelledInstance(allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());

                } else {
                    assignedInstance = user.assignLabel(instance, allLabelsOfCurrentDataset, dataSet.getMaxNumberLabels());
                }

                simulationAssignedInstances.add(assignedInstance);
                user.addDatasetID(dataSet.getId());

                if (!simulationUsers.contains(user)) {
                    simulationUsers.add(user);
                }

                if (!simulationInstances.contains(instance)) {
                    simulationInstances.add(instance);
                }


                dataSet.updateInstance(instance);


                reportWriter.write(simulationDataSets, simulationUsers, simulationInstances, simulationAssignedInstances);

                Cache currentCache = new Cache(
                        simulationDataSets,
                        simulationAssignedInstances,
                        simulationUsers,
                        simulationInstances);

                cacheManager.saveCache(currentCache);
                outputWriter.write(simulationAssignedInstances, dataSet, allUsersAssignedToCurrent);
            }
        }

    }


    private Instance getCachedInstance(Instance instance) {
        for (Instance loopInstance : simulationInstances) {

            if (loopInstance.getInstance().equals(instance.getInstance())) {
                return loopInstance;
            }
        }
        return null;
    }

    private User getCachedUser(User user) {
        for (User loopUser : simulationUsers) {

            if (loopUser.getId().equals(user.getId())) {
                return loopUser;
            }
        }
        return null;
    }

    private DataSet getCachedDataset(DataSet dataset) {
        for (DataSet loopDataset : simulationDataSets) {

            if (loopDataset.getId().equals(dataset.getId())) {
                return loopDataset;
            }
        }
        return null;
    }
}
