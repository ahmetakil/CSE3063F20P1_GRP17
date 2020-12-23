package GRP17.UserModels;


import GRP17.Models.Label;
import java.util.*;


public class RandomLabellingUser extends User {

    public RandomLabellingUser(int id, String name, String type, double consistencyCheckProbability) {
        super(id, name, type, consistencyCheckProbability);
    }
    @Override
    public List<Label> pickLabel(List<Label> allLabels, int maxNumberOfLabelsPerInstance){
        Collections.shuffle(allLabels);
        Random random = new Random();
        int randomNumber = random.nextInt((maxNumberOfLabelsPerInstance+ 1)-1) + 1 ;
        return allLabels.subList(0, randomNumber);
    }
}