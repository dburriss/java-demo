package nl.coolblue.javademo;

import java.util.Objects;

public class LogContextProperty {

    /**
     * The name of the property.
     */
    private final String name;

    /**
     * The value of the property.
     */
    private final Object value;

    /**
     * true indicates that the value should be destructured when rendering the
     * log message.
     */
    private final boolean destructureObjects;

    /**
     * Creates a new instance of the LogContextProperty class.
     */
    public LogContextProperty(String name, Object value, boolean destructureObjects) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be empty.");
        }

        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.value = value;
        this.destructureObjects = destructureObjects;
    }

    public LogContextProperty(String name, Object value) {
        this(name, value, false);
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isDestructureObjects() {
        return destructureObjects;
    }
}

