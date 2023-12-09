package nl.coolblue.javademo;

import org.slf4j.Logger;

import java.util.Objects;

public class MonitoringEvents {
    /**
     * The configured logger instance.
     */
    private final Logger logger;

    /**
     * The configured metrics implementation.
     */
    private final Metrics metrics;

    /**
     * A LogContext instance that will allow you to set ambient properties.
     */
    private final LogContext logContext;

    /**
     * Creates a new instance of MonitoringEvents.
     */
    public MonitoringEvents(Logger logger, Metrics metrics) {
        this.logger = Objects.requireNonNull(logger, "Logger cannot be null");
        this.metrics = Objects.requireNonNull(metrics, "Metrics cannot be null");
        this.logContext = new LogContext();
    }

    public Logger getLogger() {
        return logger;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public LogContext getLogContext() {
        return logContext;
    }
}

