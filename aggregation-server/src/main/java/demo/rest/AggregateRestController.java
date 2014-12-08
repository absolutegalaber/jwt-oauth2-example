package demo.rest;

import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */
@RestController
public class AggregateRestController extends AbstractTokenReusageRemoteRestController {

    @Autowired
    private OAuth2ProtectedResourceDetails resource1;

    @Value("${resource1.baseURL:http://localhost:8088}")
    private String resource1BaseURL;

    @RequestMapping(method = RequestMethod.GET, value = "/api/me")
    @ApiOperation(value = "Returns the authentication object of the current user")
    public Map me() {
        return remoteOAuthTokenReusageRestTemplate(resource1).getForEntity(resource1BaseURL+"/api/me", Map.class).getBody();
    }


}
