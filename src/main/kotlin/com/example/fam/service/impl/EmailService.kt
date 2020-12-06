package com.example.fam.service.impl

import com.example.fam.web.SendMail
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService {
    private var javaMailSender: JavaMailSender

    constructor(javaMailSender: JavaMailSender) {
        this.javaMailSender = javaMailSender
    }

    fun sendMail(toEmail: String, subject: String, request: SendMail) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(toEmail)
        mailMessage.setSubject(subject)
        mailMessage.setText(request.comment + " " + request.fullName + " " + request.email)
        mailMessage.setFrom(request.email)
        return javaMailSender.send(mailMessage)
    }
}