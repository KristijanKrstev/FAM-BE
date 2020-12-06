package com.example.fam.service

import com.example.fam.domain.Account
import com.example.fam.domain.User
import java.time.LocalDate
import java.util.*

interface UserService {
    fun createUser(user: User): Optional<User>

    fun getAllUsers(userName: String): List<User>

    fun searchUser(term: String): List<User>

    fun updateUser(id: Int, name: String, date: LocalDate, email: String, userName: String): User

    fun deleteUser(id: Int, userName: String)

    fun updateAccountsList(userId: Int, account: Account)

    fun updatePassword(uId: Int, password: String, confirmPassword: String, userName: String): User

    fun findById(id: Int, userName: String): Optional<User>
}