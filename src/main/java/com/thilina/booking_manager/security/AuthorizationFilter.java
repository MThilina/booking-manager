package com.thilina.booking_manager.security;

import com.thilina.booking_manager.service.security.CustomUserDetailsService;
import com.thilina.booking_manager.util.JwtTokenUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil tokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.bearerPrefix}")
    private String bearerPrefix;

    @Value("${jwt.authHeader}")
    private String authHeader;

    @Value("${basicPrefix}")
    private String basicHeader;

    public AuthorizationFilter(JwtTokenUtil tokenUtil, CustomUserDetailsService userDetailsService) {
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(authHeader);
        String requestURI = request.getRequestURI();

        String username = null;
        String jwtToken = null;


        // Skip the filter for Swagger and API docs
        if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        // basic auth skip
        if (requestTokenHeader != null && requestTokenHeader.startsWith(basicHeader)) {
            jwtToken = requestTokenHeader.substring(basicHeader.length()).trim();
            String basicUser = tokenUtil.getUsernameFromBase64encoding(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(basicUser);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

            logger.info("User Basic credential validated and token creation success");
        } else {
            // token auth
            if (requestTokenHeader != null && requestTokenHeader.startsWith(bearerPrefix)) {
                jwtToken = requestTokenHeader.substring(bearerPrefix.length()).trim();
                try {
                    username = tokenUtil.getUsernameFromToken(jwtToken);
                } catch (Exception e) {
                    logger.warn("JWT token processing failed: " + e.getMessage());
                }
            } else {
                logger.warn("JWT token does not begin with Bearer String");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (tokenUtil.validateToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken tokenAuthentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    tokenAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);

                    logger.info("Barer token creation success and user allocated to the context");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

