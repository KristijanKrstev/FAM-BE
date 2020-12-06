package com.example.fam.web

import com.example.fam.domain.Account
import com.example.fam.service.AccountService
import com.example.fam.service.MapValidationErrorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.MimeTypeUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*


@CrossOrigin
@RestController
@RequestMapping(path = ["/api/accounts"], produces = [MimeTypeUtils.APPLICATION_JSON_VALUE])
class AccountControler(val accountService: AccountService, val mapValidationErrorService: MapValidationErrorService) {

    @GetMapping
    fun getAllAccounts(principal: Principal): List<Account> {
        return accountService.getAllAccounts(principal.name)
    }

    @GetMapping("/find/{term}")
    @Transactional
    fun searchAccounts(@PathVariable term: String, principal: Principal): List<Account> {
       return accountService.searchAccount(term,principal.name)
    }

    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: Int, principal: Principal): Optional<Account>? {
        return accountService.findbyId(accountId, principal.name)
    }

    @PostMapping
    fun createAccount(@RequestBody account: AddAccount, result: BindingResult, principal: Principal): ResponseEntity<*> {
        val account1 = accountService.createAccount(account, principal.name)
        return ResponseEntity(account1, HttpStatus.CREATED)
    }

    @PostMapping("/{aId}")
    fun updateAccount(@PathVariable aId: Int,
                      @RequestBody account: AddAccount,
                      principal: Principal): Account? {
        val account1 = accountService.findbyId(aId, principal.name)
        return accountService.updateAccount(aId, account, principal.name)
    }

    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: Int, principal: Principal) {
        accountService.deleteAccount(id, principal.name)
    }

}