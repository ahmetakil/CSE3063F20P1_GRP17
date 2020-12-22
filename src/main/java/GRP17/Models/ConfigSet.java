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

    public List<User> getUsers(){
        return users;
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

    }
}