package com.example.fam.web

import java.time.LocalDate

data class UpdateUser(
        val id: Int,
        val password: String,
        val confirmPassword: String
)