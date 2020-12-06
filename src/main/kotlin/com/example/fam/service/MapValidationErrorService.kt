package com.example.fam.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult


@Service
class MapValidationErrorService {
    fun mapValidationService(result: BindingResult): ResponseEntity<*>? {
        if (result.hasErrors()) {
            val errorMap: MutableMap<String, String?> = HashMap()
            for (error in result.fieldErrors) {
                errorMap[error.field] = error.defaultMessage
            }
            return ResponseEntity<Map<String, String?>>(errorMap, HttpStatus.BAD_REQUEST)
        }
        return null
    }
}