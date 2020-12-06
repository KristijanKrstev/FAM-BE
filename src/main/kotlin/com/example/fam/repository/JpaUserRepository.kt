package com.example.fam.repository

import com.example.fam.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Repository
interface JpaUserRepository : JpaRepository<User, Int> {

    @Query("select u from User u where u.email like :email")
    fun findByEmail(email: String): Optional<User>

    @Query("select u from User u where u.name like :term or u.email like :term")
    fun findAll(term: String): List<User>

    @Query("select u from User u where u.email like :username")
    fun findByUsername(username: String): User

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name = ?2, u.email =?3, u.dateOfBirth =?4 where u.id = ?1")
    fun updateUser(id: Int,
                   name: String,
                   email: String,
                   dateOfBirth: LocalDate)

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.password = ?2 where u.id = ?1")
    fun updatePassword(id: Int,
                       password: String)
}
