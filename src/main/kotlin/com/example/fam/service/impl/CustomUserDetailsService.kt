package com.example.fam.service.impl

import com.example.fam.domain.User
import com.example.fam.repository.JpaUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomUserDetailsService(private val userRepository: JpaUserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email).map {
            it
        }.orElseThrow {
            UsernameNotFoundException("User not found")
        }
    }

    @Transactional
    fun loadUserById(id: Int): Optional<User> = userRepository.findById(id)

}