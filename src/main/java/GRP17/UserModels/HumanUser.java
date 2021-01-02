package GRP17.UserModels;

import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.*;

public class HumanUser extends User {
    private String password;

    public HumanUser(Integer id, String name, String type, double consistencyCheckProbability, String password) {
        super(id, name, type, consistencyCheckProbability);
        this.password = password;
    }

    public List<Label> pickLabel(List<Label> allLabels, int maxNumberOfLabelsPerInstance, Instance instance){


        try{
            System.out.println("\nInstance: " + instance.getInstance());
            System.out.println("Which labels do you want to label?");
            Collections.sort(allLabels);
            int i = 1;
            for (Label label:allLabels){
                System.out.println(i++ + ": " + label.getName());
            }
            List<Label> picks = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");
            Integer[] ids = new Integer[args.length];
            if (ids.length > maxNumberOfLabelsPerInstance){
                System.out.println("You have entered more inputs than max number of labels per instance" + maxNumberOfLabelsPerInstance);
                throw new Exception();
            }

            for(int j = 0;j<args.length;j++){
                ids[j] = Integer.parseInt(args[j]);
            }
            for (int id:ids){
                for (Label label:allLabels){
                    if (label.getId() == id){
                        picks.add(label);
                    }
                }
            }
            if (picks.size() > 0) {
                return picks;
            }
            else{
                System.out.println("There is an error");
                throw new Exception();
            }

        }catch (Exception e){
            System.out.println("Please try again");
            return pickLabel(allLabels,maxNumberOfLabelsPerInstance,instance);
        }


    }

    public boolean checkAccount(String username, String password) {
        return this.getName().equals(username) && this.password.equals(password);
    }
}
