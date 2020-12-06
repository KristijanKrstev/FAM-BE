package com.example.fam.domain.exception

class InvalidAccount: RuntimeException{
    constructor(message: String?) : super(message)
}