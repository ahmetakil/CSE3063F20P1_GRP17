package GRP17.Models;

import com.google.gson.annotations.SerializedName;

public class Label {

    @SerializedName("label id") private Integer id;
    @SerializedName("label text")private String name;

    public Label(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String toString() {
        return this.name;
    }
}
