package com.example.fam.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "works")
data class Work(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,
        @Column(unique = true)
        val name: String,
        val address: String,
        val description: String,
        val number: String,
        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
        @JsonIgnore
        val user: User
)