package com.abai.mystore.security;

import com.abai.mystore.entity.User;
import com.abai.mystore.security.jwt.JwtUser;
import com.abai.mystore.security.jwt.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import com.abai.mystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /* Этот метод на основании найденного по username-у user-а
    *  генерит нам Jwt user-а который является имплементаией
    * UserDetail
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("In loadUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }
}
