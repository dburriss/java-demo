package nl.coolblue.javademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaDemoApplication {

    public static void main(String[] args) {
        
        var observability = new JavaDemoObservability();
        observability.applicationStarted();
        SpringApplication.run(JavaDemoApplication.class, args);
    }

}
