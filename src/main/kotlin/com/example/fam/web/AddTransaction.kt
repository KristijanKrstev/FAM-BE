package com.example.fam.web

import java.time.LocalDate

class AddTransaction(
        val id : Int,
        val date: LocalDate,
        val amount: Int,
        val description: String,
        val tr_transaction: String,
        val account: Int
)