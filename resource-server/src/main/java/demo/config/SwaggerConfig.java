package demo.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Schneider-Manzell
 */
@Configuration
@EnableSwagger
public class SwaggerConfig extends WebMvcConfigurerAdapter implements ServletContextAware {

    private SpringSwaggerConfig springSwaggerConfig;
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        RelativeSwaggerPathProvider relativeSwaggerPathProvider = new RelativeSwaggerPathProvider(servletContext);
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo()).includePatterns("/api/.*").ignoredParameterTypes(Authentication.class).authorizationTypes(authorizationTypes()).pathProvider(relativeSwaggerPathProvider);
    }
    /**
    private List<AuthorizationType> authorizationTypes() {
        List<AuthorizationScope> scopes = Arrays.asList(new AuthorizationScope("read","Read persmission"));
        GrantType impilcit = new ImplicitGrant(new LoginEndpoint("http://localhost:8080/login"),"auth_token");
        List<GrantType> grantTypes = Arrays.asList(impilcit);
        return Arrays.asList(new OAuth(scopes,grantTypes));
    } **/

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Demo API", "API for Demo",
                "Demo API terms of service", "demo@gmail.com",
                "Demo API Licence Type", "Demo API License URL");
        return apiInfo;
    }

    private List<AuthorizationType> authorizationTypes() {
        ArrayList<AuthorizationType> authorizationTypes = new ArrayList<AuthorizationType>();

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("global", "access all"));

        List<GrantType> grantTypes = new ArrayList();

        LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost:8080/login");
        grantTypes.add(new ImplicitGrant(loginEndpoint, "access_token"));

        //TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("/oauth/authorize", "client_id", "client_secret");
        //TokenEndpoint tokenEndpoint = new TokenEndpoint("http://localhost:8080/oauth/token", "code");

        //AuthorizationCodeGrant authorizationCodeGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
        //grantTypes.add(authorizationCodeGrant);

        OAuth oAuth = new OAuthBuilder()
                .scopes(authorizationScopeList)
                .grantTypes(grantTypes)
                .build();

        authorizationTypes.add(oAuth);
        return authorizationTypes;
    }
}
