package com.kudaibergenov.spring.service;

public interface EmailService {

    void sendTaskCreatedNotification(String to, String subject, String body);
}