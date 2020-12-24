package GRP17;

import GRP17.IOController.*;
import GRP17.Models.*;

public class Main {


    public static void main(String[] args) {

        Parser configParser = new ConfigParser("assets/config.json");
        OutputWriter outputWriter = new OutputWriter("assets/output.json");
        ReportWriter reportWriter = new ReportWriter( "assets/report.json");

        CacheManager cacheManager = new CacheManager("assets/cache.ser");



        ConfigSet configSet = ((ConfigParser) configParser).parse();


        LabellingMechanism labellingMechanism = new LabellingMechanism(configSet, reportWriter, cacheManager, outputWriter);

        labellingMechanism.startLabeling();


    }
}