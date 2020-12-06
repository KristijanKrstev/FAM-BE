package com.example.fam.service.impl

import com.example.fam.domain.User
import com.example.fam.domain.Work
import com.example.fam.domain.exception.InvalidUser
import com.example.fam.repository.JpaUserRepository
import com.example.fam.repository.JpaWorkRepository
import com.example.fam.service.WorkService
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service
class WorkServiceImpl(private val workRepository: JpaWorkRepository,
                      private val userRepository: JpaUserRepository) : WorkService {


    override fun createWork(Name: String, Address: String, Description: String, Number: String, userName: String): Work? {
        val user = userRepository.findAll(userName)[0]
        if(isPhoneValid(Number))
        {
        val work = Work(0, Name, Address, Description, Number, user)
        return workRepository.save(work)
        }
        return null
    }

    override fun getAllWorks(): List<Work> = workRepository.findAll();


    override fun updateWork(id: Int, name: String, address: String, description: String, number: String, userName: String): Work? {

        //This throw exp if you are not admin on that work

        //This throw exp if you are not admin on that work
        val user: User = userRepository.findAll(userName).get(0)
        val work: Work = workRepository.findById(id).get()
        if (work.user != user) throw InvalidUser("Invalid user")
        if(isPhoneValid(number)){
            val work1 = with(work) { Work(0, name, address, description, number, user) }
            this.workRepository.deleteById(id)
            return workRepository.save(work1)
        }
        return null
    }

    override fun findByDescription(term: String): List<Work> = this.workRepository.findAllByDescription(term)

    override fun deleteWork(id: Int, userName: String) {
        val work: Work = this.findById(id).get()
        if (work.user.name != userName) throw InvalidUser("Invalid user")
        this.workRepository.deleteById(id)
    }

    override fun findById(id: Int): Optional<Work> = this.workRepository.findById(id);

    private fun isPhoneValid(phoneNumber: String): Boolean {
        val regex = "^(\\+389){0,1}[2,3,4,5,7,8]{1}[0-9]{7}\$"
        val pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }
}