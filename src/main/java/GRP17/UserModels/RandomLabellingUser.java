package GRP17.UserModels;


import GRP17.Models.AssignedInstance;
import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.*;

import static java.lang.System.*;

public class RandomLabellingUser extends User {

    public RandomLabellingUser(int id, String name, String type) {
        super(id, name, type);
    }

    @Override
    public AssignedInstance assignLabel(Instance instance, List<Label> labels, int maxNumberOfLabelsPerInstance) {


        long timerStart = currentTimeMillis();

        if(hasInstance(instance)){
            if(!shouldReLabelAlreadyLabelledInstance()){

                return null;
            }
        }

        // Algorithm to a subset of a given List
        Collections.shuffle(labels);
        Random random = new Random();
        int randomNumber = random.nextInt(maxNumberOfLabelsPerInstance) + 1;
        List<Label> subset = labels.subList(0, randomNumber);

        AssignedInstance assignedInstance = new AssignedInstance(this, instance, subset, new Date());

        instance.updateFrequencyLabelList(labels);
        instance.addUser(this);
        this.addFrequencyLabelList(subset);

        this.getLabellingRequests().add(assignedInstance);

        long timerEnd = currentTimeMillis();

        double timeSpending = (timerEnd - timerStart) / 1000.0;
        this.addTimeSpending(timeSpending);




        return assignedInstance;
    }

    boolean hasInstance(Instance i){
        //this.getLabellingRequests().contains(i)
        return true;
    }

    boolean shouldReLabelAlreadyLabelledInstance(){
        return true;
    }
}