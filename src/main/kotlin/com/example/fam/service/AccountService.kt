package com.example.fam.service

import com.example.fam.domain.Account
import com.example.fam.domain.Transaction
import com.example.fam.web.AddAccount
import java.util.*

interface AccountService {
    fun createAccount(account: AddAccount, userName: String): Account

    fun getAllAccounts(userName: String): List<Account>

    fun searchAccount(term: String, userName: String): List<Account>

    fun updateAccount(id: Int, account: AddAccount, userName: String): Account?

    fun deleteAccount(id: Int, userName: String)

    fun updateTransactionList(id: Int, transaction: Transaction)

    fun findbyId(id: Int, userName: String): Optional<Account>
}