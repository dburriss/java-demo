package nl.coolblue.javademo;

import java.util.ArrayList;
import java.util.List;

public abstract class TagBuilder {
    protected String success = "success";
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
        ts[0] = "result:" + success.toLowerCase();
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
                this.success = "success";
            else 
                this.success = "failure";
        }

        public static String clean(String input) {
            // Lowercase the input
            String lowercaseInput = input.toLowerCase();

            // Remove leading numbers and special characters
            String trimmedInput = lowercaseInput.replaceAll("^[^a-zA-Z0-9]*", "");

            // Remove trailing special characters
            trimmedInput = trimmedInput.replaceAll("[^a-zA-Z0-9]*$", "");

            // Remove special characters (except numbers, dash, underscore, period, and space)
            String cleanedInput = trimmedInput.replaceAll("[^a-zA-Z0-9-_. ]", "");

            // Convert camel case to kebab case
            String kebabCaseInput = cleanedInput.replaceAll("([a-z])([A-Z])", "$1-$2");

            // Convert spaces, underscores, and periods to kebab case
            kebabCaseInput = kebabCaseInput.replaceAll("[ _\\.]", "-");

            return kebabCaseInput;
        }
        
        @Override
        public TagBuilder withTag(String tag, String value) {
            tags.add(clean(tag) + ":" + clean(value));
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
            success = "failure";
            return this;
        }

    }
}
