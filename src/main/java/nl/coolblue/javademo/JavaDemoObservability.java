package nl.coolblue.javademo;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaDemoObservability {
    static String domain = "demo";
    static String app = "JavaDemo";
    private final MonitoringEvents monitoringEvents;
    private final String env;
    private final String version;

    public JavaDemoObservability() {
        var ns = Metrics.format(String.format("%s.%s", domain, app));
        StatsDClient statsDClient = new NonBlockingStatsDClientBuilder()
            .prefix(ns)
            .hostname("localhost")
            .port(8125)
            .build();
        
        // get env from settings
        env = "prod";
        
        // get version from settings
        version = "1.0.0";

        // metrics client
        Metrics metrics = new StatsDMetrics(statsDClient);
        
        // logger
        Logger logger = LoggerFactory.getLogger(JavaDemoApplication.class);
        
        // monitoring events
        monitoringEvents = new MonitoringEvents(logger, metrics);
    }
    
    private void addDefaultTags(TagBuilder tagBuilder) {
        tagBuilder.withEnv(env).withService(app).withVersion(version);
    }
    
    public void applicationStarted() {
        // define tags array for metric
        var tagsBuilder = TagBuilder.Tags.success();
        addDefaultTags(tagsBuilder);
        var tags = tagsBuilder.build();
        
        // send metrics
        monitoringEvents.getMetrics().event("Java Demo app started", "Running in prod", tags);
        monitoringEvents.getMetrics().histogram("appStarted", 1L, tags);
        
        // send log
        monitoringEvents.getLogger().info("Java Demo app starting!!!");
    }
}
