package com.example.fam.payload

import com.example.fam.domain.User


data class JWTLoginSuccessResponse(val success: Boolean,
                                   val token: String,
                                   val user: User) {

    override fun toString(): String {
        return "JWTLoginSuccessResponse(success=$success, token='$token', user='$user')"
    }
}