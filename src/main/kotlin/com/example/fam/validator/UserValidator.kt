package com.example.fam.validator

import com.example.fam.domain.User
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.time.LocalDate

@Component
class UserValidator : Validator {
    override fun validate(target: Any, errors: Errors) {
        val user: User = target as User
        with(user) {
            if (password.length < 8) {
                errors.rejectValue("password", "Length", "Password must be at least 8 characters")
            }
            if (password != confirmPassword) {
                errors.rejectValue("confirmPassword", "Match", "Password must match")
            }
            if (dateOfBirth.isAfter(LocalDate.now())) {
                errors.rejectValue("dateOfBirth", "Invalid Date", "You can't be born in the future")
            }
        }
    }

    override fun supports(clazz: Class<*>): Boolean {
        return User::class == clazz
    }
}