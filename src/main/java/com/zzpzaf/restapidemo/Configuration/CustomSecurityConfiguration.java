package com.zzpzaf.restapidemo.Configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {


       @Override
       protected void configure(HttpSecurity http) throws Exception {
               http.authorizeRequests().antMatchers("/api/items").hasRole("ADMIN")
               .and().httpBasic();
       }

       // @Override
       // protected void configure(AuthenticationManagerBuilder auth) throws Exception {                
       //         auth.inMemoryAuthentication()
       //         .withUser("user").password("{noop}mypassword").roles("USER");
       // }

        @Bean
        public JdbcDaoImpl userDetailsService(DataSource dataSource) {
                JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
                jdbcDaoImpl.setDataSource(dataSource);
                return jdbcDaoImpl;
        } 
        
        @Bean
        public PasswordEncoder encoder() {
            return new BCryptPasswordEncoder();
        }

}

