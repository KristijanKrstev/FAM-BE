package com.example.fam.service

import com.example.fam.domain.User
import com.example.fam.domain.Work
import java.util.*

interface WorkService {
    fun createWork(Name: String, Address: String, Description: String, Number: String, userName: String): Work?

    fun getAllWorks(): List<Work>

    fun updateWork(id: Int, name: String, address: String, Description: String, number: String, userName: String): Work?

    fun findByDescription(term: String): List<Work>

    fun deleteWork(id: Int, userName: String)

    fun findById(id: Int): Optional<Work>
}