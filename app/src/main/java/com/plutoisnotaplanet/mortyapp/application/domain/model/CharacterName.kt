package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.plutoisnotaplanet.mortyapp.application.ApiConstants

data class CharacterName(
    override val apiValue: String,
    override val viewValue: String = ApiConstants.Name
): CharacterStat {
}