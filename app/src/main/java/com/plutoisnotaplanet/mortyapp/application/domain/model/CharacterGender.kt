package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.plutoisnotaplanet.mortyapp.application.ApiConstants

enum class CharacterGender(override val viewValue: String, override val apiValue: String = ApiConstants.Gender): CharacterStat {
    Female("Female", apiValue = ApiConstants.Female),
    Male("Male", apiValue = ApiConstants.Male),
    Genderless("Genderless", apiValue = ApiConstants.Genderless),
    Unknown("Unknown gender", apiValue = ApiConstants.Unknown)
}