package GRP17;

import GRP17.IOController.CacheManager;
import GRP17.IOController.ConfigParser;
import GRP17.IOController.OutputWriter;
import GRP17.IOController.Parser;
import GRP17.Models.ConfigSet;
import GRP17.ReportWriters.ReportWriter;

public class Main {


    public static void main(String[] args) {

        Parser configParser = new ConfigParser("assets/config.json");
        OutputWriter outputWriter = new OutputWriter("assets/output.json");
        ReportWriter reportWriter = new ReportWriter( "assets/report.json");

        CacheManager cacheManager = new CacheManager("assets/cache.ser");

        ConfigSet configSet = ((ConfigParser) configParser).parse();


        LoginController.getInstance(configSet).login();

        LabellingMechanism labellingMechanism = new LabellingMechanism(configSet, reportWriter, cacheManager, outputWriter);

        labellingMechanism.startLabeling();


    }
}