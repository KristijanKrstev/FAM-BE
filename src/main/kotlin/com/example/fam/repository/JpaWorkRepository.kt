package com.example.fam.repository

import com.example.fam.domain.Work
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaWorkRepository : JpaRepository<Work, Int> {

    @Query("select w from Work w where lower(w.description) like %:term%")
    fun findAllByDescription(term: String): List<Work>
}