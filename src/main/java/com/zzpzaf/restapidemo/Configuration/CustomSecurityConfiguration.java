package com.zzpzaf.restapidemo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private JdbcTemplate jdbcTemplate;    

    //Authentication and Authorization SQL Strings
    private final String authenticateSQL = "SELECT USERNAME as user_name, PASSWORD as user_pwd, ACCOUNT_ENABLED as user_enabled FROM USERS WHERE USERNAME = ?";
    private final String authorizeSQL = "SELECT USERNAME as user_name, ROLE as user_role FROM USERSROLES WHERE USERNAME = ?";
 
       @Override
       protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/api/items").hasRole("ADMIN")
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().httpBasic();
       }

       @Override
       public void configure(AuthenticationManagerBuilder auth) throws Exception {
           auth.jdbcAuthentication()
           .dataSource(jdbcTemplate.getDataSource())     //database connection
           .usersByUsernameQuery(authenticateSQL)
           .authoritiesByUsernameQuery(authorizeSQL)
           .passwordEncoder(encoder());
       }

        
        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }

}

