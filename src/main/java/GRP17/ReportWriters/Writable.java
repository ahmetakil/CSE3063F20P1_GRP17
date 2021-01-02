package GRP17.ReportWriters;

import GRP17.MetricController;
import com.google.gson.JsonObject;

public interface Writable {
MetricController metricController = new MetricController();
JsonObject getMetrics();
}
