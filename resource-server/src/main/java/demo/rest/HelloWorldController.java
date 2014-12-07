package demo.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Peter Schneider-Manzell
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String home() {
        return "Hello world !";
    }

    @RequestMapping("/me")
    public Map<String,Object> me(Authentication authentication) {
        Map<String,Object> result = new HashMap<>();
        result.put("authentication",authentication);
        return result;
    }

    @RequestMapping("/supersecure")
    @Secured("#oauth2.hasScope('write')")
    public String superSecureMessage(){
        return "42";
    }
}
