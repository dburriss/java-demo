package nl.coolblue.javademo;

import com.timgroup.statsd.StatsDClient;

import java.time.Instant;
import java.util.Date;

public class StatsDMetrics extends Metrics {
    
    private final StatsDClient client;

    public StatsDMetrics(StatsDClient client) {
        this.client = client;
    }

    @Override
    public void incrementCounter(String name, String... tags) {
        client.incrementCounter(format(name), tags);
    }

    @Override
    public void incrementCounter(String name, int amount, String... tags) {
        client.count(format(name), amount, tags);
    }

    @Override
    public void gauge(String name, double value, String... tags) {
        client.gauge(format(name), value, tags);
    }

    @Override
    public void histogram(String name, double value, String... tags) {
        client.distribution(format(name), value, tags);
    }

    @Override
    public void timer(String name, long elapsedTime, String... tags) {
        client.distribution(format(name), elapsedTime, tags);
    }

    @Override
    public void event(String title, String text, String... tags) {
        client.recordEvent(com.timgroup.statsd.Event.builder().withTitle(title).withText(text).withDate(Date.from(Instant.now())).build(), tags);
    }
}
