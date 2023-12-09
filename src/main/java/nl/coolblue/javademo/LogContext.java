package nl.coolblue.javademo;

import org.slf4j.MDC;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

public class LogContext {

    /**
     * Pushes multiple properties onto the context. Disposing of the result will
     * remove them again.
     */
    public AutoCloseable pushProperties(LogContextProperty... properties) {
        return new PropertyEnricher(properties);
    }

    /**
     * Pushes a single property onto the context. Disposing of the result will
     * remove it again.
     */
    public AutoCloseable pushProperty(LogContextProperty property) {
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null");
        }

        return pushProperties(property);
    }
    
    /**
     * Pushes a single property onto the context. Disposing of the result will
     * remove it again.
     */
    public AutoCloseable pushValue(String key, String value) {
        return pushProperty(new LogContextProperty(key, value));
    }
    
    /**
     * Clears all properties from the context.
     */
    public void clear() {
        MDC.clear();
    }
    
    private static class PropertyEnricher implements AutoCloseable {
        
        public PropertyEnricher(LogContextProperty... properties) {
            Arrays.stream(properties).forEach(p -> MDC.put(p.getName(), p.getValue().toString()));
        }

        @Override
        public void close() throws Exception {
            MDC.clear();
        }
    }
}

