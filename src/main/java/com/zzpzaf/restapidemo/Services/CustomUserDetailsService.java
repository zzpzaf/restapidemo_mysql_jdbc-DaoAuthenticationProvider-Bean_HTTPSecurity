package com.zzpzaf.restapidemo.Services;

import com.zzpzaf.restapidemo.Repositories.UsersRepo;
import com.zzpzaf.restapidemo.dataObjects.User;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    //private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UsersRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByName(s);
        //logger.info(user);
        //logger.info("================================");

        org.springframework.security.core.userdetails.User springUser = null;

        if (user != null) {
            // logger.info(user);
            // logger.info("---------------------------------");
            springUser = new org.springframework.security.core.userdetails.User(
                    user.getUSERNAME(),
                    user.getPASSWORD(),
                    user.getAuthorities());
                    //logger.info(springUser);        
            return springUser;
        } else {
            //logger.error("============EXCEPTION NULL USER==================");
            throw new UsernameNotFoundException(String.format("Username not found"));

        }
    }


}
