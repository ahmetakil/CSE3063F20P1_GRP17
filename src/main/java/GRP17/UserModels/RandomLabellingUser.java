package GRP17.UserModels;


import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.*;

public class RandomLabellingUser extends User {

    public RandomLabellingUser(int id, String name, String type) {
        super(id, name, type);
    }

    @Override
    public AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance) {

        // Algorithm to a subset of a given List
        Collections.shuffle(labels);
        Random random = new Random();
        int randomNumber = random.nextInt(maxNumberOfLabelsPerInstance) + 1;
        List<Label> subset = labels.subList(0, randomNumber);

        AssignedInstance assignedInstance = new AssignedInstance(this, instance, subset, new Date());
        return assignedInstance;
    }
}