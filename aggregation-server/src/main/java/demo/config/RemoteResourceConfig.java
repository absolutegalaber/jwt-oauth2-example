package demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;


/**
 * @author Peter Schneider-Manzell
 */
@Configuration
public class RemoteResourceConfig {


    @Bean
    @Qualifier("resource1")
    public OAuth2ProtectedResourceDetails resource1(){
        OAuth2ProtectedResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        return resourceDetails;
    }



}
