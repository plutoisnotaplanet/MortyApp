package com.plutoisnotaplanet.mortyapp.application.domain.model

enum class PasswordValidationState {
    Valid,
    Invalid,
    Empty,
    Short,
    Long
}