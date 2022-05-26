package com.zzpzaf.restapidemo.Repositories;

import java.util.List;

import com.zzpzaf.restapidemo.dataObjects.User;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepo {

    //private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${USERS_TABLE}")
    private String USERS_TABLE; // = "USERS";
    //private String USERS_TABLE = "users";

    @Value("${ROLES_TABLE}")
    private String ROLES_TABLE; // = "AUTHORITIES";

    public User findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM " + USERS_TABLE + " WHERE ID=?",
                       BeanPropertyRowMapper.newInstance(User.class), id);
            // User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE ID=?",
            //             BeanPropertyRowMapper.newInstance(User.class), id);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public User findByName(String username) {

        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM " + USERS_TABLE + " WHERE USERNAME=?",
                       BeanPropertyRowMapper.newInstance(User.class), username);
            // User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE USERNAME=?",
            //             BeanPropertyRowMapper.newInstance(User.class), username);
            user.grantAuthorities(this.getUserRoles(user.getUSERNAME()));
            return user;

        } catch (IncorrectResultSizeDataAccessException e) {

            return null;
        }
    }

    private List<String> getUserRoles(String userName) {

        String querySQL = "SELECT ROLE FROM " + ROLES_TABLE + " WHERE USERNAME = '" + userName + "'";
        //String querySQL = "SELECT ROLE FROM authorities WHERE USERNAME = '" + userName + "'";
        //dataSource.
        List<String> userRoles = jdbcTemplate.queryForList(querySQL, String.class);
        // logger.info(userRoles);
        // logger.info("========== UsersRepo getUserRoles() ============");
        return userRoles;
    }

}
