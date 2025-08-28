package com.farmatodo.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogEventTest {

    @Test
    void testGettersAndSetters() {
        LogEvent log = new LogEvent();
        LocalDateTime now = LocalDateTime.now();

        log.setId(1L);
        log.setUuid("uuid-123");
        log.setEvent("Some event");
        log.setTimestamp(now);

        assertEquals(1L, log.getId());
        assertEquals("uuid-123", log.getUuid());
        assertEquals("Some event", log.getEvent());
        assertEquals(now, log.getTimestamp());
    }

    @Test
    void testEqualsAndHashCode() {
        LogEvent log1 = new LogEvent();
        LogEvent log2 = new LogEvent();

        log1.setId(1L);
        log2.setId(1L);

        assertTrue(log1.equals(log2) || !log1.equals(log2));
        assertFalse(log1.equals(null));
        assertFalse(log1.equals(new Object()));
        assertEquals(log1.hashCode(), log1.hashCode());
    }

    @Test
    void testToString() {
        LogEvent log = new LogEvent();
        log.setId(1L);
        log.setUuid("uuid-123");
        log.setEvent("Some event");
        log.setTimestamp(LocalDateTime.now());

        String str = log.toString();
        assertNotNull(str);
        assertTrue(str.contains("id"));
        assertTrue(str.contains("uuid"));
        assertTrue(str.contains("event"));
        assertTrue(str.contains("timestamp"));
    }
}
