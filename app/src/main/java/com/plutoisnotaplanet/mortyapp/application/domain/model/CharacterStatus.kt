package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.ui.graphics.Color
import com.plutoisnotaplanet.mortyapp.application.ApiConstants
import com.plutoisnotaplanet.mortyapp.ui.theme.characterAlive
import com.plutoisnotaplanet.mortyapp.ui.theme.characterDead
import com.plutoisnotaplanet.mortyapp.ui.theme.characterUnknown

enum class CharacterStatus(
    override val paramName: String,
    override val apiValue: String,
    val color: Color
) : CharacterStat {
    Alive(paramName = "Alive", apiValue = ApiConstants.Alive, color = characterAlive),
    Dead(paramName = "Dead", apiValue = ApiConstants.Dead, color = characterDead),
    Unknown(paramName = "Unknown status", apiValue = ApiConstants.Unknown, color = characterUnknown)
}
