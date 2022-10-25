package com.plutoisnotaplanet.mortyapp.application.utils

import android.util.Patterns
import com.plutoisnotaplanet.mortyapp.application.domain.model.EmailValidationState
import com.plutoisnotaplanet.mortyapp.application.domain.model.PasswordValidationState
import java.util.regex.Pattern

object ValidationUtil {

    private val PASSWORD_PATTERN =
        Pattern.compile(
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}\$"
        )

    private const val MIN_PASSWORD_LENGTH = 6
    private const val MAX_PASSWORD_LENGTH = 100

    fun validateEmail(email: String): EmailValidationState =
        when {
            email.isEmpty() -> EmailValidationState.Empty
            !Patterns.EMAIL_ADDRESS.matcher(email).find() -> EmailValidationState.Invalid
            else -> EmailValidationState.Valid
        }

    fun validatePassword(password: String): PasswordValidationState =
        when {
            password.isEmpty() ->
                PasswordValidationState.Empty
            password.length < MIN_PASSWORD_LENGTH ->
                PasswordValidationState.Short
            password.length > MAX_PASSWORD_LENGTH ->
                PasswordValidationState.Long
            PASSWORD_PATTERN.matcher(password).find() ->
                PasswordValidationState.Valid
            else -> PasswordValidationState.Invalid
        }
}