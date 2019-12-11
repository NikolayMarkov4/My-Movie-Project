package org.softuni.mymoviemaster.service;


public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
