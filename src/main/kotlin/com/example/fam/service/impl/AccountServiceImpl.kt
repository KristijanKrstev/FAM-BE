package com.example.fam.service.impl

import com.example.fam.domain.Account
import com.example.fam.domain.Transaction
import com.example.fam.domain.User
import com.example.fam.repository.JpaAccountRepository
import com.example.fam.repository.JpaTransactionRepository
import com.example.fam.repository.JpaUserRepository
import com.example.fam.service.AccountService
import com.example.fam.web.AddAccount
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex

@Service
class AccountServiceImpl(private val repository: JpaAccountRepository, private val userRepository: JpaUserRepository,
                        private val transactionRepository: JpaTransactionRepository) : AccountService{

    override fun createAccount(account: AddAccount, userName: String): Account {
        val user: User = userRepository.findAll(userName).get(0)
        val newAcc = with(account) {
            Account(0, name, type, currency, initialBalance, user)
        }
        return repository.save(newAcc)
    }

    override fun getAllAccounts(userName: String): List<Account> {
        val user = userRepository.findAll(userName).get(0)
        return repository.findAllByUser(user)
    }

    override fun searchAccount(term: String,userName: String): List<Account> {
        val accounts = this.repository.findAll(term)
        val accounts1 : MutableList<Account> = mutableListOf()
        for(ac : Account in accounts)
        {
            if(ac.user.name==userName)
                accounts1.add(ac)
        }
        return accounts1
    }

    override fun updateAccount(id: Int, account: AddAccount, userName: String): Account?{
        val user: User = userRepository.findAll(userName).get(0)

        val account1 = repository.findById(id).get()
        if(account1.user==user)
        {
            deleteAccount(id,userName)
            return createAccount(account, userName)
        }
        return null
    }

    override fun deleteAccount(id: Int, userName: String) {
        val user = userRepository.findAll(userName).get(0)
        val account = repository.findById(id).get()
        if(account.user==user)
        {
            val transactions = this.transactionRepository.findByAccount(userName)
            for (tr in transactions){
                this.transactionRepository.deleteById(tr.id)
            }
            this.repository.deleteById(id)
        }
    }

    override fun updateTransactionList(id: Int, transaction: Transaction) {
        val account = repository.findById(id).get()
        for (tr: Transaction in transactionRepository.findAll()){
            if(tr.account == account)
            {
                transactionRepository.delete(tr)
            }
        }
        repository.save(account)
    }

    override fun findbyId(id: Int, userName: String): Optional<Account> {
        val user = userRepository.findAll(userName).get(0)
        val account = repository.findById(id).get()
        if(account.user==user)
        return Optional.of(account)
        return Optional.empty()
    }
}