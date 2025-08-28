package com.farmatodo.repository;

import com.farmatodo.entity.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEventRepository extends JpaRepository<LogEvent, Long> {
}
