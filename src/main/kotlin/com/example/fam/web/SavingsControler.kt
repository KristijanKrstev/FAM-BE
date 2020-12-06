package com.example.fam.web

import com.example.fam.domain.Savings
import com.example.fam.domain.User
import com.example.fam.domain.exception.InvalidSavings
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.SavingsService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDate


@CrossOrigin
@RestController
@RequestMapping(path = ["/api/savings"], produces = [MimeTypeUtils.APPLICATION_JSON_VALUE])
class SavingsControler(val savingsService: SavingsService,val userRepository: JpaUserRepository) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSavings(@RequestBody savings: AddSaving,
                      principal: Principal): Savings {
        val user: User = userRepository.findAll(principal.name).get(0)
        return savingsService.save(user, savings.date, savings.hope_savings)
    }

    @GetMapping
    fun getAllSavings(principal: Principal): List<Savings> {
        return savingsService.getAllSavings(principal.name)
    }

    @GetMapping("/{sId}")
    fun getSavings(@PathVariable sId: Int, principal: Principal): Savings {
        return savingsService.findById(sId, principal.name).get(0)
    }

    @DeleteMapping("/{sId}")
    fun delete(@PathVariable sId: Int, principal: Principal) {
        val saving = savingsService.findById(sId, principal.name).get(0)
        savingsService.delete(saving.id)
    }

    @GetMapping("/initialBalance/{sId}")
    fun getInitialBalance(@PathVariable sId: Int, principal: Principal) {
        savingsService.checkInitialBalance(sId, principal.name)
    }

}