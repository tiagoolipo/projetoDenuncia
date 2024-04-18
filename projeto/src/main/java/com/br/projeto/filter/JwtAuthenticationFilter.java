package com.br.projeto.filter;

import com.br.projeto.entity.role.Role;
import com.br.projeto.entity.role.RoleName;
import com.br.projeto.exception.business.BadRequestException;
import com.br.projeto.exception.dto.error.ApiErrorResponseDTO;
import com.br.projeto.exception.dto.error.ErrorDTO;
import com.br.projeto.repository.user.UserRepository;
import com.br.projeto.service.jwt.TokenServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenServiceImpl tokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UserRepository userRepository;

    RequestMatcher customFilterUrl = new AntPathRequestMatcher("/api/v1/reports/**");

    public JwtAuthenticationFilter(TokenServiceImpl tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (customFilterUrl.matches(request)) {
            try {
                var token = getJwtTokenFromHeader(request);

                if (token == null || token.isEmpty()) {
                    handleExceptions(response, request.getRequestURI(), "unauthorized", "Token not informed", 401);
                    return;
                }

                var loggedUser = tokenService.getUserFromToken(token);

                var user = userRepository.findUserByEmail(loggedUser.getEmail()).orElseThrow();

                var roles = user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("" + role.getRoleName())).collect(Collectors.toList());

                var authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(), null, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                if (ex instanceof BadRequestException) {
                    handleExceptions(response, request.getRequestURI(), "bad.request", ex.getMessage(), 400);
                }
            }

        }

        filterChain.doFilter(request, response);
    }

    private void handleExceptions(HttpServletResponse response, String path, String key, String message, Integer status) throws IOException {
        var errorResponse = new ApiErrorResponseDTO();
        var errors = new ErrorDTO(key, message);
        errorResponse.setCode(status.toString());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(path);
        errorResponse.setStatus(status);
        errorResponse.setErrors(Set.of(errors));

        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String getJwtTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("JWT ")) {
            return token.substring(4);
        }
        if (StringUtils.hasText(token)) {
            return token;
        }
        return null;
    }
}
