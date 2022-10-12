package com.plutoisnotaplanet.mortyapp.application.data.rest

import com.plutoisnotaplanet.mortyapp.application.data.rest.response.BaseResponseDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.CharacterDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.EpisodeDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.LocationDto
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/character/?page={page_id}")
    fun fetchCharacters(@Path("page_id") id: Long): BaseResponseDto<CharacterDto>

    @GET("/character/{character_id}")
    fun fetchCharacter(@Path("character_id") id: Long): CharacterDto

    @GET("/location/?page={page_id}")
    fun fetchLocations(@Path("page_id") id: Long): BaseResponseDto<LocationDto>

    @GET("/location/{location_id}")
    fun fetchLocation(@Path("location_id") id: Long): LocationDto

    @GET("/episode/?page={page_id}")
    fun fetchEpisodes(@Path("page_id") id: Long): BaseResponseDto<EpisodeDto>

    @GET("/location/{episode_id}")
    fun fetchEpisode(@Path("episode_id") id: Long): EpisodeDto

}