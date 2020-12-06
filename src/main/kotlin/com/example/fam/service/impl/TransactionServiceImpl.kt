package com.example.fam.service.impl

import com.example.fam.domain.Account
import com.example.fam.domain.Transaction
import com.example.fam.repository.JpaTransactionRepository
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.AccountService
import com.example.fam.service.TransactionService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TransactionServiceImpl(private val repository: JpaTransactionRepository,
                             private val accountService: AccountService,
                             private val userRepository: JpaUserRepository) : TransactionService {
    override fun createTransaction(date: LocalDate, amount: Int, desc: String, tr: String, account: Int, userName: String): Transaction {
        //Just checking if that account is on that user
        val account1 = accountService.findbyId(account, userName).get()
        val user = userRepository.findAll(userName).get(0)

        if (tr == "Withdrawal") {
            account1.initialBalance -= amount
        } else if (tr == "Deposit") {
            account1.initialBalance += amount
        }
        val transaction = Transaction(0, date, amount, desc, tr, account1)
        return this.repository.save(transaction)
    }

    override fun getAllTransactions(userName: String): List<Transaction> = this.repository.findByAccount(userName)

    override fun searchTransaction(term: String): List<Transaction> = this.repository.findAll(term);

    override fun updateTransaction(id: Int, date: LocalDate, amount: Int, desc: String, tr: String, account: Account,userName: String): Transaction? {
        val transaction = repository.findById(id).get()
        if(transaction.account.user.name==userName)
        {
            deleteTransaction(id,userName)
            return createTransaction(date,amount,desc,tr,account.id,userName)
        }
        return null;
    }

    override fun deleteTransaction(id: Int, userName: String) {
        val transaction = findById(id, userName).get()
        val account: Account = transaction.account
        if (account.user.name == userName) {
            if (transaction.tr_transaction == "Withdrawal") {
                account.initialBalance += transaction.amount
            } else if (transaction.tr_transaction == "Deposit") {
                account.initialBalance -= transaction.amount
            }
            repository.deleteById(transaction.id)
        }
    }

    override fun findById(id: Int, userName: String): Optional<Transaction> = this.repository.findById(id)

}