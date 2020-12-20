package GRP17.UserModels;


import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.*;

import static java.lang.System.*;

public class RandomLabellingUser extends User {

    public RandomLabellingUser(int id, String name, String type, double consistencyCheckProbability) {
        super(id, name, type, consistencyCheckProbability);
    }
    @Override
    public List<Label> pickLabel(List<Label> labels, int maxNumberOfLabelsPerInstance){
        Collections.shuffle(labels);
        Random random = new Random();
        int randomNumber = random.nextInt(maxNumberOfLabelsPerInstance) + 1;
        return labels.subList(0, randomNumber);
    }
}