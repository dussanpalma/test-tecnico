package com.farmatodo.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiKeyFilterTest {

    private ApiKeyFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws IOException {
        filter = new ApiKeyFilter();
        // Inyectar API key codificada
        String apiKey = "my-secret-key";
        String encoded = Base64.getEncoder().encodeToString(apiKey.getBytes());
        ReflectionTestUtils.setField(filter, "encodedApiKey", encoded);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }

    @Test
    void testPublicPathsAreNotFiltered() throws ServletException, IOException {
        String[] publicPaths = {"/v3/api-docs", "/swagger-ui", "/h2-console", "/ping"};
        for (String path : publicPaths) {
            when(request.getRequestURI()).thenReturn(path);
            filter.doFilterInternal(request, response, filterChain);
            verify(filterChain).doFilter(request, response);
            reset(filterChain);
        }
    }

    @Test
    void testMissingApiKey() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/private");
        when(request.getHeader("X-API-KEY")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertEquals("API Key no proporcionada", responseWriter.toString());
        verifyNoInteractions(filterChain);
    }

    @Test
    void testInvalidApiKey() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/private");
        when(request.getHeader("X-API-KEY")).thenReturn("wrong-key");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertEquals("API Key inválida", responseWriter.toString());
        verifyNoInteractions(filterChain);
    }

    @Test
    void testValidApiKey() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/private");
        when(request.getHeader("X-API-KEY")).thenReturn("my-secret-key");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertEquals("", responseWriter.toString());
    }
}
