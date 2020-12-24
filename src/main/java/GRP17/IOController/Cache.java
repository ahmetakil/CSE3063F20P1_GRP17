package GRP17.IOController;

import GRP17.Models.AssignedInstance;
import GRP17.Models.DataSet;
import GRP17.Models.Instance;
import GRP17.UserModels.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cache implements Serializable {

    private List<DataSet> cachedDatasets;
    private List<AssignedInstance> cachedAssignedInstances;
    private List<User> cachedUsers;
    private List<Instance> cachedInstances;

    public Cache(List<DataSet> cachedDatasets, List<AssignedInstance> cachedAssignedInstances, List<User> cachedUsers,
                 List<Instance> cachedInstances) {

        this.cachedAssignedInstances = cachedAssignedInstances;
        this.cachedDatasets = cachedDatasets;
        this.cachedUsers = cachedUsers;
        this.cachedInstances = cachedInstances;

    }

    public Cache() {

        cachedDatasets = new ArrayList<>();
        cachedAssignedInstances = new ArrayList<>();
        cachedUsers = new ArrayList<>();
        cachedInstances = new ArrayList<>();
    }

    public List<DataSet> getDatasets() {
        return this.cachedDatasets;
    }


    public List<AssignedInstance> getAssignedInstances() {
        return this.cachedAssignedInstances;
    }

    public List<User> getUsers() {
        return this.cachedUsers;
    }

    public List<Instance> getInstances() {
        return this.cachedInstances;
    }

}
