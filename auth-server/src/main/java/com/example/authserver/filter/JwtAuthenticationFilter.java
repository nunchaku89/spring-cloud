package com.example.authserver.filter;

import com.example.authserver.dto.LoginRequestDto;
import com.example.authserver.properties.JwtProperties;
import com.example.authserver.service.CustomUserDetails;
import com.example.authserver.util.JwtUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        super.setAuthenticationManager(authenticationManager);
    }
    private JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("***********************************************************");
        logger.info("Entered JwtAuthenticationFilter");

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;

        try {
            loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("JwtAuthenicationFilter :: {}", loginRequestDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getUserPassword());
        logger.debug("JwtAuthenticationFilter : Token issued");

        Authentication authentication = getAuthenticationManager().authenticate(authenticationToken);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.debug("Authentication :: {}", customUserDetails.getUser().getUserName());
        logger.info("***********************************************************");

        return authentication;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("***********************************************************");

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        logger.debug("USER ID :: {}", customUserDetails.getUser().getUserId());
        logger.debug("USER NAME :: {}", customUserDetails.getUser().getUserName());

        final String jwtToken = jwtUtil.generateToken(customUserDetails);

        logger.info("***********************************************************");

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
