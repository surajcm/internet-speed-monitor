package com.github.learning.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledTasks {
    private static final Logger log = LogManager.getLogger(ScheduledTasks.class);
    private final LocalDateTime serverStartTime = LocalDateTime.now();
    private final AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        var now = LocalDateTime.now();
        var elapsedTime = Duration.between(serverStartTime, now).getSeconds();
        log.info("# {} : The date is {}, and time is {} [at {} seconds]",
                counter.incrementAndGet(), now.toLocalDate(), now.toLocalTime(), elapsedTime);
    }
}
