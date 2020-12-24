package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;

public class Main {


    public static void main(String[] args) {

        Parser configParser = new ConfigParser("assets/config.json");
        OutputWriter outputWriter = new OutputWriter("assets/output.json");


        ConfigSet configSet = ((ConfigParser) configParser).parse();

        String reportName = "assets/report.json";
        ReportWriter reportWriter = new ReportWriter(reportName);


        LabellingMechanism labellingMechanism = new LabellingMechanism(configSet, reportWriter,outputWriter);

        labellingMechanism.startLabeling();


    }
}