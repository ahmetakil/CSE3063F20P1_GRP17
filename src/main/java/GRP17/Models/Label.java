package GRP17.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Label {

    @SerializedName("label id") private int id;
    @SerializedName("label text")private String name;

    public Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String toString()
    {
        return this.name;
    }
}
