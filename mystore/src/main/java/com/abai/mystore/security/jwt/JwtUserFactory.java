package com.abai.mystore.security.jwt;

import com.abai.mystore.entity.Role;
import com.abai.mystore.entity.Status;
import com.abai.mystore.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    /* Проходим полностью по списку userRoles
     * и его преобразуем в список GrandAuthorities
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles){
        return userRoles.stream()
                .map(role ->
                    new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
    }
}
