package com.example.fam.domain.exception

data class InvalidLoginResponse(private var name: String = "Invalid name",
                                private var password: String = "Invalid password")