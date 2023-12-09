package nl.coolblue.javademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

@SpringBootApplication
public class JavaDemoApplication {
    static String domain = "demo";
    static String app = "java-demo";
    public static void main(String[] args) {
        var ns = String.format("%s.%s", domain, app);
        StatsDClient client = new NonBlockingStatsDClientBuilder()
                .prefix(ns)
                .hostname("localhost")
                .port(8125)
                .build();

        // define tags array for metric
        String[] tags = {"env:prod", "service:java-demo", "version:1.0.0"};
        // send metric
        var ev = com.timgroup.statsd.Event.builder().withTitle("Java Demo App Started").withText("Prod started").build();
        client.recordEvent(ev, tags);
        client.histogram("app_started", 1L, tags);
        
        SpringApplication.run(JavaDemoApplication.class, args);
    }

}
