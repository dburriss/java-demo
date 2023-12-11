package nl.coolblue.javademo;

import java.util.ArrayList;
import java.util.List;

public abstract class TagBuilder {
    protected String result = "success";
    protected List<String> tags = new ArrayList<>();
    public abstract TagBuilder withTag(String tag, String value);
    public abstract TagBuilder withTag(String tag, int value);
    public abstract TagBuilder withTag(String tag, long value);
    public abstract TagBuilder withEnv(String env);
    public abstract TagBuilder withService(String service);
    public abstract TagBuilder withVersion(String version);
    public abstract TagBuilder asFailure();
    public String[] build() {
        String[] ts = new String[tags.size() + 1];
        ts[0] = "result:" + result;
        System.arraycopy(tags.toArray(new String[0]), 0, ts, 1, tags.size());
        return ts;
    }

    public static class Tags extends TagBuilder {

        public static TagBuilder success() {
            return new Tags(true);
        }

        public static TagBuilder failure() {
            return new Tags(false);
        }

        private Tags(boolean success) {
            if(success)
                this.result = "success";
            else 
                this.result = "failure";
        }

        /**
         * Formats the input string to a valid tag value.
         * 
         * @param input The input string to format.
         * @return The formatted string.
         */
        public static String format(final String input) {
            
            // Remove leading numbers and special characters
            String cleanedInput = input.replaceAll("^[^a-zA-Z0-9]*", "");

            // Remove trailing special characters
            cleanedInput = cleanedInput.replaceAll("[^a-zA-Z0-9]*$", "");

            // Remove special characters (except numbers, dash, underscore, period, and space)
            cleanedInput = cleanedInput.replaceAll("[^a-zA-Z0-9-_. ]", "");

            // Convert camel case to kebab case
            cleanedInput = cleanedInput.replaceAll("([a-z])([A-Z])", "$1-$2");

            // Lowercase the input
            cleanedInput = cleanedInput.toLowerCase();
            
            // Convert spaces, underscores, and periods to kebab case
            cleanedInput = cleanedInput.replaceAll("[ _\\.]", "-");

            return cleanedInput;
        }
        
        @Override
        public TagBuilder withTag(String tag, String value) {
            tags.add(format(tag) + ":" + format(value));
            return this;
        }

        @Override
        public TagBuilder withTag(String tag, int value) {
            return withTag(tag, String.valueOf(value));
        }

        @Override
        public TagBuilder withTag(String tag, long value) {
            return withTag(tag, String.valueOf(value));
        }

        @Override
        public TagBuilder withEnv(String env) {
            return withTag("env", env);
        }

        @Override
        public TagBuilder withService(String service) {
            return withTag("service", service);
        }

        @Override
        public TagBuilder withVersion(String version) {
            return withTag("version", version);
        }


        @Override
        public TagBuilder asFailure() {
            result = "failure";
            return this;
        }

    }
}
