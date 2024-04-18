package com.br.projeto.controller;

import com.br.projeto.dto.auth.AuthRequestDTO;
import com.br.projeto.dto.auth.AuthenticatedResponseDTO;
import com.br.projeto.dto.auth.TokenValidateDTO;
import com.br.projeto.dto.auth.UserCredentialsDTO;
import com.br.projeto.service.jwt.TokenServiceImpl;
import com.br.projeto.utils.MapperUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final MapperUtils mapper = new MapperUtils();
    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenServiceImpl tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticatedResponseDTO> authenticate(@Validated @RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var authenticatedResponseDTO = buildAuthenticatedResponseDTO(authentication);

        return ResponseEntity.ok(authenticatedResponseDTO);
    }

    @PostMapping(value = "/validate-token")
    public ResponseEntity<UserCredentialsDTO> validateToken(@RequestBody TokenValidateDTO tokenValidateDTO) {
        var user = tokenService.getUserFromToken(tokenValidateDTO.getToken());
        var response = mapper.map(user, UserCredentialsDTO.class);

        return ResponseEntity.ok(response);
    }

    private AuthenticatedResponseDTO buildAuthenticatedResponseDTO(Authentication authentication) {
        var authenticatedResponseDTO = new AuthenticatedResponseDTO();

        String jwt = tokenService.generateToken(authentication);

        authenticatedResponseDTO.setJwt(jwt);

        return authenticatedResponseDTO;
    }
}
