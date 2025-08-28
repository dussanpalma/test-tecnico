package com.farmatodo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class PingControllerTest {

    private final PingController controller = new PingController();

    @Test
    void testPing() {
        ResponseEntity<String> response = controller.ping();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("pong", response.getBody());
    }
}
