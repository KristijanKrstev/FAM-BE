package com.example.fam.repository

import com.example.fam.domain.Account
import com.example.fam.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaAccountRepository : JpaRepository<Account, Int> {

    @Query("select a from Account a where lower(a.name) like %:term%")
    fun findAll(term : String): List<Account>


    fun findAllByUser(user: User): List<Account>

}