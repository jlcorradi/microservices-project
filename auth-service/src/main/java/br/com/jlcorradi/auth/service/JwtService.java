package br.com.jlcorradi.auth.service;

import br.com.jlcorradi.auth.dto.EcommerceUserDto;
import br.com.jlcorradi.auth.model.ActiveToken;
import br.com.jlcorradi.auth.model.EcommerceUser;
import br.com.jlcorradi.auth.repository.ActiveTokenRepository;
import br.com.jlcorradi.auth.repository.EcommerceUserRepository;
import br.com.jlcorradi.commons.exception.UnauthorizedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final ActiveTokenRepository repository;
    private final int tokenDurationInMilliseconds;
    private final String jwtTokenSecret;
    private final EcommerceUserRepository userRepository;
    private final ModelMapper mapper;

    public JwtService(
            ActiveTokenRepository repository,
            @Value("${authService.tokenDurationInMillis}") int tokenDurationInMilliseconds,
            @Value("${authService.jwtTokenSecret}") String jwtTokenSecret,
            EcommerceUserRepository userRepository,
            ModelMapper mapper) {
        this.repository = repository;
        this.tokenDurationInMilliseconds = tokenDurationInMilliseconds;
        this.jwtTokenSecret = jwtTokenSecret;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public String retrieveOrCreateTokenForUser(EcommerceUser user) {
        return repository.findActiveTokenByUser(user)
                .stream()
                .findFirst()
                .map(ActiveToken::getToken)
                .orElseGet(() -> createNewToken(user));
    }

    private String createNewToken(EcommerceUser user) {
        repository.markOtherTokensAsInactive(user);

        Date expiration = new Date(System.currentTimeMillis() + tokenDurationInMilliseconds);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtTokenSecret.getBytes(StandardCharsets.UTF_8)))
                .issuedAt(new Date())
                .expiration(expiration)
                .subject(user.getUsername())
                .claim("authorities", user.getCommaSeparatedAuthorities());
        String jwt = jwtBuilder.compact();
        ActiveToken activeToken = ActiveToken.of(UUID.randomUUID(), user, jwt, expiration, true);
        activeToken = repository.save(activeToken);

        return activeToken.getToken();
    }

    public EcommerceUserDto validateToken(String accessToken) {
        JwtParser parser = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtTokenSecret.getBytes(StandardCharsets.UTF_8)))
                .build();

        Claims payload = null;
        try {
            payload = parser.parseSignedClaims(accessToken).getPayload();
        } catch (Exception e) {
            throw new UnauthorizedTokenException(e);
        }
        return userRepository.findByUsername(payload.getSubject())
                .map(user -> repository.findActiveTokenByUserAndToken(user, accessToken))
                .flatMap(activeToken -> activeToken)
                .map(activeToken -> mapper.map(activeToken.getUser(), EcommerceUserDto.class))
                .orElseThrow(UnauthorizedTokenException::new);
    }
}
