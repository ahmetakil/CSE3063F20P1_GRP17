package GRP17.Models;

import GRP17.UserModels.User;
import java.util.List;

public class ConfigSet {

    private DataSet currentDataset;

    public ConfigSet(DataSet currentDataset){
        this.currentDataset = currentDataset;
    }
    /*
    Returns the users that is assigned to the current dataset
    Not all of the users listed in config file
     */
    public List<User> getUsers(){
        DataSet current = getCurrentDataset();
        return current.getUsers();
    }


    public DataSet getCurrentDataset(){

       return this.currentDataset;
    }
}