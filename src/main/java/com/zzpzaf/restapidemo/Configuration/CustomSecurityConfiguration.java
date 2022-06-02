package com.zzpzaf.restapidemo.Configuration;

import com.zzpzaf.restapidemo.Services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@Configuration
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomBasicAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private Environment env;

    private String[] authorities;
    private String[] matchers;
    
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        getEnvironmentVariables();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requestHandler = http.authorizeRequests();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authUrls = requestHandler.antMatchers (matchers);
        requestHandler = authUrls.hasAnyAuthority(authorities);
   
        SessionManagementConfigurer<HttpSecurity> smc = http.sessionManagement(); //.     .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        SessionCreationPolicy sCrPol = SessionCreationPolicy.STATELESS;
        smc.sessionCreationPolicy(sCrPol);
        
    }


    @Bean 
    public BasicAuthenticationFilter basicAuthenticationFilter(){
        BasicAuthenticationFilter baf = new BasicAuthenticationFilter(providerManager(), myAuthenticationEntryPoint);
        return baf;
    }

    @Bean
    public ProviderManager providerManager(){
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
        
    }

        
    @Bean
    public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
    }

    private void getEnvironmentVariables() {
        authorities = env.getProperty("USER_ROLES", String[].class);
        matchers = env.getProperty("MATCHERS", String[].class);
    }

}

