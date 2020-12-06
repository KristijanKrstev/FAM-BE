package com.example.fam.domain.exception

class InvalidTransaction: RuntimeException{
    constructor(message: String?) : super(message)
}