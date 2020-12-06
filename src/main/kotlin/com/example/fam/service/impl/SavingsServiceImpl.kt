package com.example.fam.service.impl

import com.example.fam.domain.Savings
import com.example.fam.domain.User
import com.example.fam.domain.exception.InvalidSavings
import com.example.fam.repository.JpaSavingsRepository
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.SavingsService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class SavingsServiceImpl(private val savingsRepository: JpaSavingsRepository, private val userRepository: JpaUserRepository) : SavingsService{
    override fun save(user: User, date: LocalDate, hope_savings: Int): Savings {
        if (getAllSavings(user.name).isNotEmpty()) throw InvalidSavings("Saving already exist")
        var suma = 0
        for (account in user.accounts) {
            suma += account.initialBalance
        }
        val savings = Savings(0,user,0, date, suma,hope_savings)
        return this.savingsRepository.save(savings)
    }

    override fun findById(id: Int, userName: String): List<Savings> {
        val user = userRepository.findAll(userName).get(0)
        return savingsRepository.findAllByUser(user)
    }

    override fun getAllSavings(userName: String): List<Savings> {
        val user = userRepository.findAll(userName)[0]
        return savingsRepository.findAllByUser(user)
    }

    override fun delete(id: Int) = savingsRepository.deleteById(id)

    override fun checkInitialBalance(id: Int, userName: String) {

        val savings = findById(id, userName).get(0)
        val user = userRepository.findAll(userName)[0]
        var suma = 0
        for (account in user.accounts) {
            suma += account.initialBalance
        }
        suma -= savings.previos_state
        val savings1 = with(savings) { Savings(0,user,suma,date,previos_state,hope_savings)}
        savingsRepository.deleteById(id)
        savingsRepository.save(savings1)
    }
}