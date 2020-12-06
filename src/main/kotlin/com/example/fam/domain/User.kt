package com.example.fam.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val Id: Int,
        @Column(unique = true)
        val name: String,
        val dateOfBirth: LocalDate,
        @Column(unique = true, nullable = false)
        val email: String,
        @Column(nullable = false)
        private val password: String,
        @Transient
        @Column(nullable = false)
        val confirmPassword: String,
        @JsonIgnore
        @JsonBackReference
        @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
        val accounts: List<Account>
) : UserDetails {
    override fun getUsername(): String = name
    override fun getPassword(): String = password

    @JsonIgnore
    override fun isEnabled(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun getAuthorities(): Set<out GrantedAuthority>? = null
}