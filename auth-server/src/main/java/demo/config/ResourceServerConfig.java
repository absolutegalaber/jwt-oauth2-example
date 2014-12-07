package demo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Peter Schneider-Manzell
 */
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                // Just for laughs, apply OAuth protection to only 2 resources
                .requestMatchers().antMatchers("/","/admin/beans").and()
                .authorizeRequests()
                .anyRequest().access("#oauth2.hasScope('read')");
        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("sparklr");
    }
}
