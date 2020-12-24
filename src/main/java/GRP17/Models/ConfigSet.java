package GRP17.Models;

import GRP17.UserModels.User;
import java.util.List;

public class ConfigSet {

    private List<DataSet> datasets;
    private Integer currentDatasetId;

    public ConfigSet(List<DataSet> dataSets, Integer currentDatasetId){
        this.datasets = dataSets;
        this.currentDatasetId = currentDatasetId;
    }
    /*
    Returns the users that is assigned to the current dataset
    Not all of the users listed in config file
     */
    public List<User> getUsers(){
        DataSet current = getCurrentDataset();
        return current.getUsers();
    }

    public Integer getCurrentDatasetId() {
        return currentDatasetId;
    }

    public DataSet getCurrentDataset(){

        for(DataSet dataSet : datasets){
            if(dataSet.getId() == currentDatasetId){
                return dataSet;
            }
        }
        return null;
    }
}