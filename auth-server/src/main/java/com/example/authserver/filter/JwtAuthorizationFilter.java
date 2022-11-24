package com.example.authserver.filter;

import com.example.authserver.model.User;
import com.example.authserver.properties.JwtProperties;
import com.example.authserver.repository.UserRepository;
import com.example.authserver.service.CustomUserDetails;
import com.example.authserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    private JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("***********************************************************");

        final String authorizationHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if (authorizationHeader == null || !authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
        }
        logger.debug("Authorization Header :: {}", authorizationHeader);

        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        logger.debug("token :: {}", token);

        String userId = jwtUtil.getUserIdFromToken(token);
//        String username = jwtUtil.getUsernameFromToken(token);
        logger.debug("username: {}", userId);

        if (userId != null) {
            User user = userRepository.findByUserId(userId);

            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        logger.info("***********************************************************");

        filterChain.doFilter(request, response);
    }
}
