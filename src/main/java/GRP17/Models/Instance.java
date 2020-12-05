package GRP17.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instance {
    @SerializedName("id")private int id;
    @Expose
    @SerializedName ("instance")private String instance;

    public Instance(int id, String content) {
        this.id = id;
        this.instance = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

     public String toString(){
        return this.instance;
     }


}
