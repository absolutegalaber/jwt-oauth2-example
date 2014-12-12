package demo;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Peter Schneider-Manzell
 */

public class GrantTypePasswordTests extends AbstractTokenTests {

    private static final String GRANT_TYPE = "password";
    private static final String CLIENT_ID = "my-trusted-client";
    private static final String CLIENT_SECRET = "";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "testpass";


    private static final String[] EXPECTED_SCOPES = new String[]{"read","trust","write"};

    @Test
    public void testGetTokenViaUsernamePassword(){
        RestTemplate restTemplate = new TestRestTemplate(CLIENT_ID,CLIENT_SECRET);
        ResponseEntity<Map> result = restTemplate.postForEntity("http://localhost:"+port+"/oauth/token?grant_type="+GRANT_TYPE+"&username="+USERNAME+"&password="+PASSWORD, null, Map.class);
        Assert.assertNotNull(result.getBody().get("access_token"));
        Assert.assertNotNull(result.getBody().get("refresh_token"));
        Assert.assertNotNull(result.getBody().get("expires_in"));
        Assert.assertNotNull(result.getBody().get("jti"));
        Assert.assertEquals("bearer", result.getBody().get("token_type"));
        for (String expectedScope : EXPECTED_SCOPES) {
            Assert.assertTrue("Scope ["+expectedScope+"] not found!",result.getBody().get("scope").toString().contains(expectedScope));
        }
    }

    @Test
    public void testGetTokenViaUsernamePasswordInvalidUsernamePwd(){
        RestTemplate restTemplate = new TestRestTemplate(CLIENT_ID,CLIENT_SECRET);
        ResponseEntity<Map> result = restTemplate.postForEntity("http://localhost:"+port+"/oauth/token?grant_type="+GRANT_TYPE+"&username="+USERNAME+"&password="+PASSWORD+"wrong", null, Map.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getStatusCode().value());
    }


}
