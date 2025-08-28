package com.farmatodo.service.impl;

import com.farmatodo.entity.LogEvent;
import com.farmatodo.repository.LogEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class LogServiceImplTest {

    @InjectMocks
    private LogServiceImpl logService;

    @Mock
    private LogEventRepository logEventRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogEvent() {
        String eventMessage = "Test event";
        logService.logEvent(eventMessage);
        ArgumentCaptor<LogEvent> captor = ArgumentCaptor.forClass(LogEvent.class);
        verify(logEventRepository, times(1)).save(captor.capture());
        LogEvent savedLog = captor.getValue();
        assert savedLog.getEvent().equals(eventMessage);
        assert savedLog.getUuid() != null;
        assert savedLog.getTimestamp() != null;
    }
}
