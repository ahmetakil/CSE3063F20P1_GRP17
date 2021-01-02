package GRP17.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Label implements Serializable, Comparable<Label> {

    @SerializedName("label id")
    private Integer id;
    @SerializedName("label text")
    private String name;

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

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Label)) {
            return false;
        }

        Label label = (Label) o;
        return label.name.equals(this.name) && label.id.equals(this.id);

    }

    @Override
    public int compareTo(Label o) {
        return this.id - o.id;
    }
}
