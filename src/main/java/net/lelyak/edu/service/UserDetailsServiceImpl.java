package net.lelyak.edu.service;


import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("theaterDTS")
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
                                                                                          List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
            user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {
        return userRoles.stream()
            .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
            .collect(Collectors.toList());
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole));
        return authorities;
    }


}