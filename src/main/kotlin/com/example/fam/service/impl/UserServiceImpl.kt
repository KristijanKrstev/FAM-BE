package com.example.fam.service.impl

import com.example.fam.domain.Account
import com.example.fam.domain.User
import com.example.fam.domain.exception.InvalidUser
import com.example.fam.repository.JpaAccountRepository
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.UserService
import com.example.fam.web.UpdateUser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserServiceImpl(private val userRepository: JpaUserRepository,
                      private val accountRepository: JpaAccountRepository,
                      private var bCryptPasswordEncoder: BCryptPasswordEncoder) : UserService {


    override fun createUser(user: User): Optional<User> {
        return try {
            val newUser = with(user) {
                User(Id, name, dateOfBirth, email, bCryptPasswordEncoder.encode(password), "", accounts)
            }
            Optional.ofNullable(userRepository.save(newUser))

        } catch (exception: Exception) {
            throw InvalidUser("User exist");
        }
    }

    override fun getAllUsers(userName: String): List<User> = this.userRepository.findAll(userName)

    override fun searchUser(term: String): List<User> = this.userRepository.findAll(term)

    override fun updateUser(id: Int, name: String, date: LocalDate, email: String, userName: String): User {
        val user = userRepository.findById(id).get()
        if (user.name == userName) {
            userRepository.updateUser(id, name, email, date)
        }
        return user
    }

    override fun deleteUser(id: Int, userName: String) {
        userRepository.deleteById(findById(id, userName).get().Id)
    }

    override fun updateAccountsList(userId: Int, account: Account) {
        val user = userRepository.findById(userId).get()

        for (acc: Account in user.accounts) {
            if (acc == account)
                accountRepository.delete(acc)
        }
        userRepository.save(user)
    }

    override fun updatePassword(uId: Int, password: String, confirmPassword: String, userName: String): User {
        val user = userRepository.findById(uId).get()
        if(user.name==userName && password==confirmPassword){
            userRepository.updatePassword(uId, bCryptPasswordEncoder.encode(password))
        }
        return user
    }

    override fun findById(id: Int, userName: String): Optional<User> {
        val user: User = this.userRepository.findById(id).get()
        if (user.name != userName) throw InvalidUser("Invalid user")
        return this.userRepository.findById(id)
    }
}