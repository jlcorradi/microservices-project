package br.com.jlcorradi.web;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthFilterGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    public static final String BEARER_PREFIX = "Bearer ";
    private final WebClient.Builder webClientBuilder;

    private final String usersServiceBaseUrl;

    public AuthFilterGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                          @Value("${client-services-urls.auth-service}") String usersServiceBaseUrl) {
        this.webClientBuilder = webClientBuilder;
        this.usersServiceBaseUrl = usersServiceBaseUrl;
    }

    record UserDto(Long userId, String commaSeparatedAuthorities) {
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authHeader = Optional.ofNullable(headers.get(HttpHeaders.AUTHORIZATION))
                    .map(strings -> strings.get(0))
                    .map(s -> s.substring(BEARER_PREFIX.length()))
                    .orElse("");

            ServerHttpResponse response = exchange.getResponse();

            if (!StringUtils.hasText(authHeader)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            return webClientBuilder.build()
                    .get()
                    .uri(String.format("%s/api/v1/auth?accessToken=%s&uri=%s",
                            usersServiceBaseUrl, authHeader, exchange.getRequest().getURI()))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
                    .bodyToMono(UserDto.class)
                    .flatMap(userDto -> {
                        MDC.put("userId", String.valueOf(userDto.userId));
                        exchange.getRequest()
                                .mutate()
                                .header("x-user-id", String.valueOf(userDto.userId()));
                        return chain.filter(exchange);
                    });
        };
    }

}