package com.zzpzaf.restapidemo.Configuration;

import java.io.IOException;
//import java.io.PrintWriter;

//import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

   

    @Autowired
    private Environment env;

    private String realm; 


    // === This is when extending the  BasicAuthenticationEntryPoint class ===
    // === instead of implementing the AuthenticationEntryPoint interface === 
    // @Override
    // public void afterPropertiesSet()  {
    //     realm =  env.getProperty("app.realm"); 
    //     setRealmName(realm);
    //     super.afterPropertiesSet();
    // }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

        realm =  env.getProperty("app.realm");        
                    
        response.setHeader("WWW-Authenticate please!", "Basic Realm=" + realm  + "");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NooOO Authentication !!!");
    }
    
}
