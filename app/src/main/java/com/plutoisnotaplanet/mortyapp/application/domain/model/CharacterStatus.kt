package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.ui.graphics.Color
import com.plutoisnotaplanet.mortyapp.ui.theme.characterAlive
import com.plutoisnotaplanet.mortyapp.ui.theme.characterDead
import com.plutoisnotaplanet.mortyapp.ui.theme.characterUnknown

enum class CharacterStatus(val status: String, val color: Color) {
    Alive("Alive", characterAlive),
    Dead("Dead", characterDead),
    Unknown("unknown", characterUnknown)
}
