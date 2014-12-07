package demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Peter Schneider-Manzell
 */
@RestController
public class AdminController {



    @RequestMapping("/admin/supersecure")
    public String superSecureMessage(){
        return "42";
    }
}
