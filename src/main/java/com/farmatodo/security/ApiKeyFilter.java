package com.farmatodo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private String getEncodedApiKey() {
        String envKey = System.getenv("API_KEY_BASE64");
        if (envKey == null) {
            throw new IllegalStateException("API Key no configurada en el entorno");
        }
        return envKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")
                || path.startsWith("/h2-console") || path.equals("/ping")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestKey = request.getHeader("X-API-KEY");
        if (requestKey == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("API Key no proporcionada");
            return;
        }

        String decodedKey = new String(Base64.getDecoder()
                .decode(getEncodedApiKey().trim()), StandardCharsets.UTF_8).trim();

        if (!requestKey.trim().equals(decodedKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("API Key inválida");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

