package demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Peter Schneider-Manzell
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String home() {
        return "Hello world!";
    }
}
