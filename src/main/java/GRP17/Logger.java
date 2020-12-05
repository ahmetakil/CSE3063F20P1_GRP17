package GRP17;

import GRP17.Models.AssignedInstance;
import GRP17.UserModels.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
    final String logFile = "log.log";

    private Logger() {
        //Because this constructor is called only once (at the first invocation of Logger.getInstance() we can safely clear the previous log file.
        clearLogFile();
    }

    static Logger instance = null; //We use Singleton pattern here.

    //In Singleton Pattern we always access the object via getInstance() and the first invocation creates the object and then the other invocations simply returns the previously created object.
    public static Logger getInstance() {
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }
    private String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void logUserCreation(User user){
        String output=getCurrentTime();
        output +=" [ConfigSetParser] ";
        output += "created user with id: " + user.getId() + ", name: " + user.getName();
        output += " as " + user.getType();
        log(output);
    }
    public void logLabelAssignment(AssignedInstance labelAssignment){
        String output = getCurrentTime();
        output += " [AssignmentInstance] INFO";
        output += " user id:" + labelAssignment.getUser().getId() +", user name: " +labelAssignment.getUser().getName();
        output += " assigned instance id: " + labelAssignment.getInstance().getId() + " "+
        "with class label: " + labelAssignment.getLabels().toString() +
                " instance: \"" + labelAssignment.getInstance().toString() + "\"";
        log(output);
    }
    //All log functions invokes this function in order to display the log.
    public void log(String log){
        System.out.println(log);
        addLogToFile(log);
    }

    public void addLogToFile(String log) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.append(log);
            writer.append('\n');
            writer.close();
        }
        catch (IOException e){
            System.out.println("Something went wrong, please check your log file!");
        }
    }
    void clearLogFile(){
        try {
           File file = new File(logFile);
           file.delete();
        }
        catch (Exception e){
            System.out.println("Something went wrong, please check your log file!");
        }
    }
}
