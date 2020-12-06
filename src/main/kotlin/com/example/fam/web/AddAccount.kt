package com.example.fam.web

import com.example.fam.domain.User
import java.util.*

data class AddAccount(
        val id : Int,
        val name: String,
        val type: String,
        val currency: Currency,
        val initialBalance: Int
)