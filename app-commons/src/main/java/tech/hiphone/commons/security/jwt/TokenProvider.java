package tech.hiphone.commons.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import tech.hiphone.commons.management.SecurityMetersService;
import tech.hiphone.framework.config.SystemProperties;
import tech.hiphone.framework.config.SystemProperties.Security.Authentication.Jwt;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private static final String AUTHORITIES_DELIMITER = ",";

    private static final String INVALID_JWT_TOKEN = "Invalid JWT token.";

    private Key key;

    private final JwtParser jwtParser;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final SecurityMetersService securityMetersService;

    public TokenProvider(SystemProperties systemProperties, SecurityMetersService securityMetersService) {
        byte[] keyBytes;
        Jwt jwt = systemProperties.getSecurity().getAuthentication().getJwt();
        String secret = jwt.getBase64Secret();
        if (StringUtils.isEmpty(secret)) {
            secret = jwt.getSecret();
            log.warn(
                    "Warning: the JWT key used is not Base64-encoded. We recommend using the base64-secret key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Base64.getDecoder().decode(secret);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jwt.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * jwt.getTokenValidityInSecondsForRememberMe();

        this.securityMetersService = securityMetersService;
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITIES_DELIMITER));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String createToken(Authentication authentication, long expirationMill) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITIES_DELIMITER));

        long now = (new Date()).getTime();
        Date validity = new Date(now + expirationMill);

        Map<String, Object> claimsMap = new HashMap<>();
        Object details = authentication.getDetails();
        if (details instanceof Map) {
            claimsMap.putAll((Map) details);
        }
        claimsMap.put(AUTHORITIES_KEY, authorities);

        JwtBuilder jwtBuilder = Jwts.builder().setSubject(authentication.getName());
        claimsMap.forEach((key, value) -> {
            jwtBuilder.claim(key, value);
        });
        return jwtBuilder.signWith(key, SignatureAlgorithm.HS512).setExpiration(validity).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(AUTHORITIES_DELIMITER))
                .filter(auth -> !auth.trim().isEmpty()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        claims.remove(AUTHORITIES_KEY);
        Map<String, Object> details = new HashMap<>();
        claims.forEach((key, value) -> {
            details.put(key, value);
        });

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, token,
                authorities);
        authentication.setDetails(details);
        return authentication;
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            this.securityMetersService.trackTokenExpired();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (UnsupportedJwtException e) {
            this.securityMetersService.trackTokenUnsupported();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (MalformedJwtException e) {
            this.securityMetersService.trackTokenMalformed();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (SignatureException e) {
            this.securityMetersService.trackTokenInvalidSignature();

            log.trace(INVALID_JWT_TOKEN, e);
        } catch (IllegalArgumentException e) {
            // TODO: should we let it bubble (no catch), to avoid defensive programming and
            // follow the fail-fast principle?
            log.error("Token validation error {}", e.getMessage());
        }
        return false;
    }

}
