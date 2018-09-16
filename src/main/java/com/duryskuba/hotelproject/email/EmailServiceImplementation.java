package com.duryskuba.hotelproject.email;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImplementation implements EmailService {

    private JavaMailSender mailSender;
    private SimpleMailMessage template;

    public EmailServiceImplementation(JavaMailSender javaMailSender, SimpleMailMessage template) {
        this.mailSender = javaMailSender;
        this.template = template;
    }

    @Override
    public void sendMessage(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public void sendMessageFromTemplate(String to, String subject, String[] messageArgs) {
        sendMessage(to,subject,String.format(template.getText(),messageArgs));
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String message, String attachment) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            //mimeMessageHelper.addAttachment("Registration" ,new FileSystemResource(new File(attachment)));
            mimeMessageHelper.addAttachment("Registration" ,new ClassPathResource(attachment));

        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }

        mailSender.send(mimeMessage);

    }

}
