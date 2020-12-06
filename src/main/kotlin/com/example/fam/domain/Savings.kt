package com.example.fam.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "savings")
data class Savings(

        @Id @GeneratedValue
        var id: Int,
        @javax.persistence.OneToOne(cascade = [javax.persistence.CascadeType.PERSIST])
        @JsonIgnore
        val user: User,
        val saving: Int = 0,
        val date: LocalDate,
        val previos_state: Int,
        val hope_savings: Int
)