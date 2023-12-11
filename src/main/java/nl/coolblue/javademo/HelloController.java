package nl.coolblue.javademo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final JavaDemoObservability observability;

    public HelloController() {
        this.observability = new JavaDemoObservability();
    }
    @GetMapping("/")
    public String index() {
        String result = "";
        // example of a custom metric timer
        try(var timer = observability.timeHelloQuery()) {
            Thread.sleep(100);
            result = "Hello World!";
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
