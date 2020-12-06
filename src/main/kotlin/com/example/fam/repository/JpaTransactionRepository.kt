package com.example.fam.repository

import com.example.fam.domain.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaTransactionRepository : JpaRepository<Transaction, Int> {
    @Query("select t from Transaction t where lower(t.description) like %:term%")
    fun findAll(term: String): List<Transaction>

    @Query("select t from Transaction t join Account a on t.account.id=a.id join User u on a.user.Id=u.Id where u.name like :term")
    fun findByAccount(term: String): List<Transaction>
}