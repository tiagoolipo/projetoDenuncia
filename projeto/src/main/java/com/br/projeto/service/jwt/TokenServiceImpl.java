package com.br.projeto.service.jwt;

import com.br.projeto.dto.RoleDTO;
import com.br.projeto.dto.auth.UserCredentialsDTO;
import com.br.projeto.entity.user.User;
import com.br.projeto.entity.user.UserPrincipal;
import com.br.projeto.exception.business.BadRequestException;
import com.br.projeto.exception.business.ObjectNotFoundException;
import com.br.projeto.repository.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${app.config.jwt.expiration}")
    private String expiration;

    @Value("${app.config.secrets.api-secret}")
    private String apiSecret;

    @Autowired
    private UserRepository userRepository;

    public String generateToken(Authentication authentication) {
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();

        var user = getUser(userPrincipal.getEmail());

        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("AUTHAPI")
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, apiSecret)
                .compact();
    }

    public String generateTokenFromRefresh(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userId", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("AUTHAPI")
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, apiSecret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(apiSecret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new BadRequestException(String.format("Invalid JWT signature: %s", e.getMessage()));
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new BadRequestException(String.format("Invalid JWT token: %s", e.getMessage()));
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new BadRequestException(String.format("JWT token is expired: %s", e.getMessage()));
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new BadRequestException(String.format("JWT token is unsupported: %s", e.getMessage()));
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new BadRequestException(String.format("JWT claims string is empty: %s", e.getMessage()));
        }
    }

    public String getTokenUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(apiSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public UserCredentialsDTO getUserFromToken(String token) {
        isTokenValid(token);
        return getUserCredentials(token);
    }

    private UserCredentialsDTO getUserCredentials(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(apiSecret).build().parseClaimsJws(token).getBody();
        var userCredentialsDto = new UserCredentialsDTO();
        userCredentialsDto.setEmail(claims.get("email").toString());
        return userCredentialsDto;
    }

    private User getUser(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

}
