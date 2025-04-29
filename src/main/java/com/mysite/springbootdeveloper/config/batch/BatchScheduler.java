package com.mysite.springbootdeveloper.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job refreshTokenCleanupJob;

    // 매일 새벽 1시 실행
    @Scheduled(cron = "0 0 1 * * *")
    public void runRefreshTokenCleanupJob() throws Exception {
        jobLauncher.run(refreshTokenCleanupJob,
                new org.springframework.batch.core.JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());
    }
}