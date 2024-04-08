package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.security.AuthUserDetail;
import com.example.authservice.service.remote.RemoteUserService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService{
    private RemoteUserService remoteUserService;

    @Autowired
    public void setUserService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    // IMPORTANT, since we have only firstname and lastname which is not UNIQUE. We need to search by email here.
    // But spring security only provide load by username.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = Optional.ofNullable(remoteUserService.getUserByEmail(email).getBody());

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder() // spring security's userDetail
                .userId(user.getUserId())
                .type(user.getType())
                .active(user.getActive())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getEmail()) // username is the email, so we use email here
                .password(user.getPassword())
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        userAuthorities.add(new SimpleGrantedAuthority(user.getType()));
        userAuthorities.add(new SimpleGrantedAuthority(user.getActive() ? "ACTIVE" : "BANNED"));
        return userAuthorities;
    }

    public User addUser(User user) {
        try {
//            String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
//            user.setPassword(encodedPassword);
            ResponseEntity<User> response = remoteUserService.addUser(user);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                // created user successfully
                return response.getBody();
            }else{
                return null;
            }
        } catch (FeignException e) {
            return null;
        }
    }
}
