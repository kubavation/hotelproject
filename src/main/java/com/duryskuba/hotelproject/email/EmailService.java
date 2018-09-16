package com.duryskuba.hotelproject.email;

public interface EmailService {

    void sendMessage(String to, String subject, String message);
    void sendMessageFromTemplate(String to, String subject, String[] args);
    void sendMessageWithAttachment(String to,String subject, String message, String attachment);
    //+ moze plik?
}
