package com.example.fam.web

import com.example.fam.service.impl.EmailService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/contact")
class ContactController(private val emailService: EmailService) {

    @PostMapping
    fun sendMail(@RequestBody request: SendMail): ResponseEntity<SendMail> {
        emailService.sendMail("kristi.jan97@live.com", "FAM", request)
        return ResponseEntity.ok(request)
    }
}