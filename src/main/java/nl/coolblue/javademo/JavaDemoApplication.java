package nl.coolblue.javademo;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import nl.coolblue.javademo.TagBuilder.Tags;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class JavaDemoApplication {
    static String domain = "demo";
    static String app = "java-demo";
    static Logger logger = LoggerFactory.getLogger(JavaDemoApplication.class);
    public static void main(String[] args) {
        var ns = String.format("%s.%s", domain, app);
        StatsDClient statsDClient = new NonBlockingStatsDClientBuilder()
                .prefix(ns)
                .hostname("localhost")
                .port(8125)
                .build();

        // metrics client
        Metrics metrics = new StatsDMetrics(statsDClient);
        var telemetry = new MonitoringEvents(logger, metrics);
        // define tags array for metric
        var tagsBuilder = Tags.success().withEnv("prod").withService("JavaDemo").withVersion("1.0.0");
        var tags = tagsBuilder.build();
        // send metric
        telemetry.getMetrics().event("Java Demo app started", "Running in prod", tags);
        telemetry.getMetrics().histogram("app_started", 1L, tags);
        telemetry.getLogger().info("Java Demo app starting!!!");
        
        SpringApplication.run(JavaDemoApplication.class, args);
        
    }

}
