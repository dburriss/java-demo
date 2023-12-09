package nl.coolblue.javademo;

import java.util.Arrays;
import java.util.Objects;

public abstract class Metrics {

    /**
     * Increments the specified counter metric by 1.
     */
    public abstract void incrementCounter(String name, String... tags);

    /**
     * Increments the specified counter metric by the amount indicated by
     * {@code amount}.
     */
    public abstract void incrementCounter(String name, int amount, String... tags);

    /**
     * Sets the current value for a gauge-type metric.
     */
    public abstract void gauge(String name, double value, String... tags);

    /**
     * Registers a value for a histogram-type metric.
     */
    public abstract void histogram(String name, double value, String... tags);

    /**
     * Registers an elapsed time for a timer-type metric.
     */
    public abstract void timer(String name, long elapsedTime, String... tags);

    /**
     * Registers an event.
     */
    public abstract void event(String title, String text, String... tags);

    /**
     * Starts a timer that will register an elapsed time for a timer-type metric when the
     * result is disposed.
     */
    public TimerDisposable startTimer(String name, String... tags) {
        throwIfArgumentsInvalid(name, tags);

        return new TimerDisposable(this, name, tags);
    }

    private void throwIfArgumentsInvalid(String name, String[] tags) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (tags == null) {
            throw new IllegalArgumentException("Tags cannot be null.");
        }
        if (Arrays.stream(tags).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Tag cannot be null.");
        }
    }

    public static class TimerDisposable implements AutoCloseable {
        private final String name;
        private final String[] tags;
        private final Metrics owner;
        private final long startTime;
        private boolean disposed = false;

        public TimerDisposable(Metrics owner, String name, String[] tags) {
            this.owner = owner;
            this.name = name;
            this.tags = tags;
            this.startTime = System.nanoTime();
        }

        @Override
        public void close() {
            if (!disposed) {
                long elapsedTime = System.nanoTime() - startTime;
                owner.timer(name, elapsedTime, tags);
                this.disposed = true;
            }
        }
    }
}
