package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * @author Peter Schneider-Manzell
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {


    @Autowired
    TokenStore tokenStore;

    @Autowired
    JwtAccessTokenConverter tokenConverter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // @formatter:off
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .requestMatchers()
                .antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.PATCH, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        System.out.println("Configuring ResourceServerSecurityConfigurer ");
        resources.resourceId("dummy").tokenStore(tokenStore);
    }



    public static class Stuff {

        @Autowired
        JwtAccessTokenConverter jwtAccessTokenConverter;


        @Bean
        @Qualifier("tokenStore")
        public TokenStore tokenStore() {

            System.out.println("Created JwtTokenStore");
            return new JwtTokenStore(jwtAccessTokenConverter);
        }

        @Bean
        protected JwtAccessTokenConverter jwtTokenEnhancer() {
            JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
            Resource resource = new ClassPathResource("public.cert");
            String publicKey = null;
            try {
                publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            converter.setVerifierKey(publicKey);
            return converter;
        }
    }

}
