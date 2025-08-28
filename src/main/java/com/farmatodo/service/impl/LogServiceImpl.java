package com.farmatodo.service.impl;

import com.farmatodo.entity.LogEvent;
import com.farmatodo.repository.LogEventRepository;
import org.springframework.stereotype.Service;
import com.farmatodo.service.LogService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LogServiceImpl implements LogService {

    private final LogEventRepository logEventRepository;

    public LogServiceImpl(LogEventRepository logEventRepository) {
        this.logEventRepository = logEventRepository;
    }

    @Override
    public void logEvent(String event) {
        LogEvent log = new LogEvent();
        log.setUuid(UUID.randomUUID().toString());
        log.setEvent(event);
        log.setTimestamp(LocalDateTime.now());
        logEventRepository.save(log);
    }
}
