package com.ybe.mp10.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    void deleteInvalidOrders() {

    }
}
