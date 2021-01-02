package GRP17.UserModels;

import GRP17.Models.Label;

import java.util.List;

public class HumanUser extends User {
    String password;
    public HumanUser(Integer id, String name, String type, double consistencyCheckProbability, String password) {
        super(id, name, type, consistencyCheckProbability);
        this.password = password;
    }

    @Override
    public List<Label> pickLabel(List<Label> labels, int maxNumberOfLabelsPerInstance) {
        return null;
    }
}
