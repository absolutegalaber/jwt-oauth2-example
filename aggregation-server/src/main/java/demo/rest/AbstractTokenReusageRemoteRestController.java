package demo.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.client.RestTemplate;

/**
 * @author Peter Schneider-Manzell
 */
public abstract class AbstractTokenReusageRemoteRestController {

    protected RestTemplate remoteOAuthTokenReusageRestTemplate(OAuth2ProtectedResourceDetails resourceDetails){
        OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)auth.getDetails();
        OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(details.getTokenValue());
        OAuth2ClientContext clientContext = new DefaultOAuth2ClientContext(accessToken);
        RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails,clientContext);
        return restTemplate;
    }


}
