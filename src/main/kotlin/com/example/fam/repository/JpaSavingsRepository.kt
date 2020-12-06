package com.example.fam.repository

import com.example.fam.domain.Savings
import com.example.fam.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSavingsRepository : JpaRepository<Savings, Int> {

    fun findAllByUser(user : User) : List<Savings>
}