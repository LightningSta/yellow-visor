package com.yellow.dash.service;

import com.yellow.dash.Logic.ProjectsAnalys;
import com.yellow.dash.model.Person;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.ArrayList;

@Service
public class MailService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private ProjectsAnalys projectsAnalys;
    @Autowired
    public MailService(JavaMailSender emailSender, ProjectsAnalys projectsAnalys) {
        this.emailSender = emailSender;
        this.projectsAnalys = projectsAnalys;
    }

    public void sendSimpleEmail(String toAddress, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("trololo89878825467@gmail.com");
        mimeMessageHelper.setTo(toAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        emailSender.send(mimeMessage);
    }
    public void sendAll(String nameproject) throws MessagingException {
        String text="В проекте "+nameproject+" было добавлено новое задание";
        ArrayList<Person> persons = projectsAnalys.getPersonFromProject(nameproject);
        for (int i = 0; i < persons.size(); i++) {
            try {
                sendSimpleEmail(persons.get(i).getEmail(),nameproject,text);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
