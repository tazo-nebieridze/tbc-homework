package com.example.homeworkstbc.domain.useCase

import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    private val passwordPattern = Pattern.compile(".{5,}")

    operator fun invoke(password: String): Boolean {
        return passwordPattern.matcher(password).matches()
    }
}