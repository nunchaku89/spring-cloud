package com.example.authserver.service;

import com.example.authserver.model.User;
import com.example.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("***********************************************************");
        logger.info("username :: {}", username);
        User user = userRepository.findByUserId(username);
        logger.info("user :: {}", user);

        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            String roles[] = user.getUserRole().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            logger.info("***********************************************************");

            return new CustomUserDetails(user);
        }

        logger.info("***********************************************************");
        return null;
    }
}
