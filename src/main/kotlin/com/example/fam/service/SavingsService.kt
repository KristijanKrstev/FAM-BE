package com.example.fam.service

import com.example.fam.domain.Savings
import com.example.fam.domain.User
import java.time.LocalDate
import java.util.*

interface SavingsService {
    fun save(user: User, date: LocalDate, hope_savings: Int): Savings

    fun findById(id: Int, userName: String): List<Savings>

    fun getAllSavings(userName: String): List<Savings>

    fun delete(id: Int)

    fun checkInitialBalance(id: Int, userName: String)
}