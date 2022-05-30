package com.zzpzaf.restapidemo.Configuration;

import javax.servlet.http.HttpServletResponse;

import com.zzpzaf.restapidemo.Services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private CustomUserDetailsService userDetailsService;
 
       @Override
       protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/api/items").hasAnyAuthority("ADMIN", "USER")
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().httpBasic()
            .realmName(env.getProperty("app.realm"))
            .authenticationEntryPoint(
                    (request, response, authException) -> {
                        response.setHeader("WWW-Authenticate", "Basic realm=" + env.getProperty("app.realm") + "");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Authentication !!!");
                    }
            );
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

}

