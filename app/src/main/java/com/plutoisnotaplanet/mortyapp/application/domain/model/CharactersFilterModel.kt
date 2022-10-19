package com.plutoisnotaplanet.mortyapp.application.domain.model

data class CharactersFilterModel(
    val status: CharacterStatus? = null,
    val gender: CharacterGender? = null,
    val species: CharacterSpecies? = null,
    val name: String? = null
) {

    val isFiltersActive: Boolean
        get() = status != null || gender != null || species != null || name != null
}