package GRP17.Models;

import GRP17.UserModels.User;

import java.util.ArrayList;
import java.util.List;

public class ConfigSet {

    private List<User> users;
    private List<DataSet> datasets;
    private Integer currentDatasetId;

    public ConfigSet(List<User> users,List<DataSet> dataSets, Integer currentDatasetId){
        this.users = users;
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

    public DataSet getDataset(Integer id){

        for(DataSet dataSet: this.datasets){
            if(dataSet.getId() == id){
                return dataSet;
            }
        }
        return null;
    }
}