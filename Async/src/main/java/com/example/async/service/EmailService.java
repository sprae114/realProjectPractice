package com.example.async.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Async("defaultTaskExecutor") // defaultTaskExecutor 를 사용하도록 설정
    public void sendMail() {
        System.out.println("[sendMail] :: "
                + Thread.currentThread().getName());
    }

    @Async("messagingTaskExecutor") // messagingTaskExecutor 를 사용하도록 설정
    public void sendMailWithCustomThreadPool() {
        System.out.println("[sendMailWithCustomThreadPool] :: "
                + Thread.currentThread().getName());
    }
}