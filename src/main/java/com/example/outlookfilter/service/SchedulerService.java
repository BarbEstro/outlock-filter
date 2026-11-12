package com.example.outlookfilter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Service
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final TaskScheduler taskScheduler;
    private final OutlookService outlookService;
    private ScheduledFuture<?> currentTask;

    public SchedulerService(TaskScheduler taskScheduler, OutlookService outlookService) {
        this.taskScheduler = taskScheduler;
        this.outlookService = outlookService;
    }

    public void startOrUpdateSchedule(int minutes) {
        if(minutes <= 0){
            log.warn("I minuti che hai impostato è minore di zero");
            return;
        }
        stopSchedule();
        currentTask = taskScheduler.scheduleWithFixedDelay(outlookService::downloadAttachment, Duration.ofMinutes(minutes));
        log.info("Timer avviato: controllo ogni {} minuti", minutes);
    }

    public void stopSchedule() {
        if(currentTask == null){
            return;
        }

        currentTask.cancel(false);
        currentTask = null;
        log.info("Timer stoppato");
    }


    public boolean isRunning() {
        return currentTask != null && !currentTask.isCancelled() && !currentTask.isDone();
    }
}