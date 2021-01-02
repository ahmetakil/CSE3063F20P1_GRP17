package GRP17.UserModels;


import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.*;


public class LazyUser extends User {

    public LazyUser(Integer id, String name, String type, double consistencyCheckProbability) {
        super(id, name, type, consistencyCheckProbability);
    }

    public List<Label> pickLabel(List<Label> allLabels, int maxNumberOfLabelsPerInstance, Instance instance) {
        List<Label> picks = new ArrayList<>();
        Label shortest = allLabels.get(0);
        for (Label label : allLabels) {
            if (label.getName().length() < shortest.getName().length()) {
                shortest = label;
            }
        }
        picks.add(shortest);
        return picks;
    }
}