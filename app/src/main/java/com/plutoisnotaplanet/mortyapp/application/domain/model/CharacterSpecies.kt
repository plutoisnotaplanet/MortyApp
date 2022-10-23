package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.plutoisnotaplanet.mortyapp.application.ApiConstants

enum class CharacterSpecies(override val viewValue: String, override val apiValue: String): CharacterStat {
    Human("Human", apiValue = ApiConstants.Human),
    Alien("Alien", apiValue = ApiConstants.Alien),
    Humanoid("Humanoid", apiValue = ApiConstants.Humanoid),
    Cronenberg("Cronenberg", apiValue = ApiConstants.Cronenberg),
    Disease("Disease", apiValue = ApiConstants.Disease),
    Robot("Robot", apiValue = ApiConstants.Robot),
    Poopybutthole("Poopybutthole", apiValue = ApiConstants.Poopybutthole),
    Animal("Animal", apiValue = ApiConstants.Animal),
    MythologicalCreature("Mythological Creature", apiValue = ApiConstants.MythologicalCreature),
    Vampire("Vampire", apiValue = ApiConstants.Vampire),
    Unknown("Unknown", apiValue = ApiConstants.Unknown)
}