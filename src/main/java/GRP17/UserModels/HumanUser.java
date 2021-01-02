package GRP17.UserModels;

import GRP17.Models.Instance;
import GRP17.Models.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanUser extends User {
    String password;

    public HumanUser(Integer id, String name, String type, double consistencyCheckProbability, String password) {
        super(id, name, type, consistencyCheckProbability);
        this.password = password;
    }

    public List<Label> pickLabel(List<Label> allLabels, int maxNumberOfLabelsPerInstance, Instance instance) {

        System.out.println(instance.getInstance());
        System.out.println("Which label do you want to label?");
        int i = 1;
        for (Label label:allLabels){
            System.out.println(i++ + ": " + label.getName());
        }
        List<Label> picks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] args = line.split(" ");
        Integer[] ids = new Integer[args.length];

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
        return picks;

    }

    public boolean checkAccount(String username, String password) {
        return this.getName().equals(username) && this.password.equals(password);
    }
}
