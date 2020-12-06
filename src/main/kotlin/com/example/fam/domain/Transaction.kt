package com.example.fam.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "transactions")
data class Transaction(
        @Id @GeneratedValue
        var id: Int,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        val date: LocalDate,
        val amount: Int,
        val description: String,
        val tr_transaction: String,
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
        @JsonIgnore
        val account: Account
)