package com.example.fam.web

import com.example.fam.domain.Work
import com.example.fam.domain.exception.InvalidWork
import com.example.fam.service.MapValidationErrorService
import com.example.fam.service.WorkService
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.*
import java.security.Principal

@CrossOrigin
@RestController
@RequestMapping(path = ["/api/works"], produces = [MimeTypeUtils.APPLICATION_JSON_VALUE])
class WorkControler(val workService: WorkService) {

    @GetMapping
    fun getAllAccounts(): List<Work> {
        return workService.getAllWorks()
    }


    @PostMapping
    fun createWork(@RequestBody work : AddWork,
                   principal: Principal): Work? {
        return workService.createWork(work.name,work.address,work.description, work.number, principal.name)
    }

    @PostMapping("/{wId}")
    fun updateAccount(@PathVariable wId: Int,
                      @RequestBody work : AddWork,
                      principal: Principal): Work? {
        return workService.updateWork(wId, work.name, work.address,work.description, work.number, principal.name)
    }

    @GetMapping("/{workId}")
    fun getWork(@PathVariable workId: Int): Work {
        return workService.findById(workId).orElseThrow { InvalidWork("") }
    }

    @GetMapping("/find/{term}")
    fun getWorkByDescription(@PathVariable term: String): List<Work> {
        return workService.findByDescription(term)
    }

    @DeleteMapping("/{id}")
    fun deleteWork(@PathVariable id: Int, principal: Principal) {
        workService.deleteWork(id, principal.name)
    }
}