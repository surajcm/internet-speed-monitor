package com.github.learning.schedule;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledTasks {
    private static final Logger log = LogManager.getLogger(ScheduledTasks.class);
    private final LocalDateTime serverStartTime = LocalDateTime.now();
    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicInteger reportTime;
    private final Counter triggerCount;

    public ScheduledTasks(MeterRegistry meterRegistry) {
        reportTime = meterRegistry.gauge("internet_report_time_gauge", new AtomicInteger(0));
        triggerCount = meterRegistry.counter("internet_packet_counter");
    }

    @Scheduled(fixedRate = 5000)
    public void schedulingTask() {
        reportTime.set(reportCurrentTime());
        triggerCount.increment();
    }

    public int reportCurrentTime() {
        var now = LocalDateTime.now();
        var elapsedTime = Duration.between(serverStartTime, now).getSeconds();
        log.info("# {} : The date is {}, and time is {} [at {} seconds]",
                counter.incrementAndGet(), now.toLocalDate(), now.toLocalTime(), elapsedTime);
        return getRandomNumberInRange(0, 100);
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        trySpeedTest();
        return new Random().nextInt((max - min) + 1) + min;
    }

    private void trySpeedTest() {
        SpeedTestSocket speedTestSocket = new SpeedTestSocket();

// add a listener to wait for speedtest completion and progress
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(SpeedTestReport report) {
                // called when download/upload is complete
                log.info("[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
                log.info("[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                // called when a download/upload error occur
            }

            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                log.info("[PROGRESS] progress : " + percent + "%");
                log.info("[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
                log.info("[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
            }
        });
        speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/1M.iso");
    }
}
