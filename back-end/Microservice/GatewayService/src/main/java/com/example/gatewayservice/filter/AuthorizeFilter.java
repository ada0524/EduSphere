package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.springframework.util.MultiValueMap;

@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        // Extract the Authorization header
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract token without 'Bearer ' prefix

            // Validate the token. This is a placeholder for your validation logic.
            boolean isValidToken = validateToken(token);

            if (!isValidToken) {
                // If the token is invalid, reject the request
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // If the token is valid, proceed with the request
            return chain.filter(exchange);
        } else if (request.getPath().toString().startsWith("/auth-service/")) {
            // Allow requests to auth-service without a token
            return chain.filter(exchange);
        } else if (request.getPath().toString().startsWith("/email-service/")) {
            // Allow requests to email-service without a token
            return chain.filter(exchange);
        } else {
            // If there's no token and the request is not to auth-service, reject the request
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean validateToken(String token) {
        // Implement token validation logic here
        return token != null && !token.isEmpty();
    }
}
