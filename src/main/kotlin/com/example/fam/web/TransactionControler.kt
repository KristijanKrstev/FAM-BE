package com.example.fam.web

import com.example.fam.domain.Transaction
import com.example.fam.domain.User
import com.example.fam.domain.exception.InvalidTransaction
import com.example.fam.repository.JpaAccountRepository
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal

@CrossOrigin
@RestController
@RequestMapping(path = ["/api/transactions"], produces = [MimeTypeUtils.APPLICATION_JSON_VALUE])
class TransactionControler(val transactionService: TransactionService, val accountRepository: JpaAccountRepository, val userRepository: JpaUserRepository) {

    @GetMapping
    fun getAllTransactions(principal: Principal): List<Transaction> {
        return transactionService.getAllTransactions(principal.name)
    }

    @GetMapping("/find/{term}")
    fun searchTransactions(@PathVariable term: String): List<Transaction> {
        return transactionService.searchTransaction(term)
    }

    @GetMapping("/{transactionId}")
    fun getTransaction(@PathVariable transactionId: Int, principal: Principal): Transaction {
        return transactionService.findById(transactionId, principal.name).orElseThrow { InvalidTransaction("") }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@RequestBody transaction: AddTransaction,
                          principal: Principal
    ): Transaction {
        val user: User = userRepository.findAll(principal.name).get(0)
        return transactionService.createTransaction(transaction.date, transaction.amount, transaction.description, transaction.tr_transaction, transaction.account, principal.name)
    }

    @PostMapping("/{tId}")
    fun updateTransaction(@PathVariable tId: Int,
                          @RequestBody transaction: AddTransaction,
                          principal: Principal
    ): Transaction? {
        val transaction1: Transaction = getTransaction(tId, principal)
        return transactionService.updateTransaction(tId, transaction.date, transaction.amount, transaction.description, transaction.tr_transaction, transaction1.account, principal.name)
    }

    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: Int, principal: Principal) {
        transactionService.deleteTransaction(id, principal.name)
    }

}