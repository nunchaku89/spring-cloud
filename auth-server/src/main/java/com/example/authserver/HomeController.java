package com.example.authserver;

import com.example.authserver.model.User;
import com.example.authserver.service.CustomUserDetailsService;
import com.example.authserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public String home() throws Exception {
        return "Welcome to JWT Tutorial";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserId(),
                            user.getUserPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUserId());

//        final String token = jwtUtil.generateToken(userDetails);
        return userDetails.getUsername();
    }
}
