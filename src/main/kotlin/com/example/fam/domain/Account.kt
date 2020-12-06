package com.example.fam.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "accounts")
data class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        val name: String,
        val type: String,
        val currency: Currency,
        var initialBalance: Int,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
        @JsonIgnore
        val user: User
)