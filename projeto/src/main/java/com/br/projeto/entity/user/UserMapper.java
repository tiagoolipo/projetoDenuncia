package com.br.projeto.entity.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserPrincipal userToPrincipal(User user){
        UserPrincipal userMap = new UserPrincipal();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("" + role.getRoleName())).collect(Collectors.toList());

        userMap.setEmail(user.getEmail());
        userMap.setPassword(user.getPassword());
        userMap.setEnabled(true);
        userMap.setAuthorities(authorities);
        return userMap;
    }

}
