package com.example.fam.service

import com.example.fam.domain.Account
import com.example.fam.domain.Transaction
import java.time.LocalDate
import java.util.*

interface TransactionService {
    fun createTransaction(date: LocalDate, amount: Int, desc: String, tr: String, account: Int, userName: String): Transaction

    fun getAllTransactions(userName: String): List<Transaction>

    fun searchTransaction(term: String): List<Transaction>

    fun updateTransaction(id: Int, date: LocalDate, amount: Int, desc: String, tr: String, account: Account, userName: String): Transaction?

    fun deleteTransaction(id: Int, userName: String)

    fun findById(id: Int, userName: String): Optional<Transaction>
}