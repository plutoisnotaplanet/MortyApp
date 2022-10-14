package com.plutoisnotaplanet.mortyapp.application.data.rest

import com.plutoisnotaplanet.mortyapp.application.data.rest.response.BaseResponseDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.CharacterDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.EpisodeDto
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.LocationDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {

    @GET("character/")
    suspend fun fetchCharacters(@Query("page") page: Int): BaseResponseDto<CharacterDto>

    @GET("character/")
    suspend fun fetchFilteredCharacters(
        @QueryMap map: Map<String, String>
    ): BaseResponseDto<CharacterDto>


    @GET("character/{character_id}")
    suspend fun fetchCharacter(@Path("character_id") id: Long): CharacterDto

    @GET("location/")
    suspend fun fetchLocations(@Query("page") page: Long): BaseResponseDto<LocationDto>

    @GET("location/{location_id}")
    suspend fun fetchLocation(@Path("location_id") id: Long): LocationDto

    @GET("episode/")
    suspend fun fetchEpisodes(@Query("page") page: Long): BaseResponseDto<EpisodeDto>

    @GET("episode/{episode_id}")
    suspend fun fetchEpisode(@Path("episode_id") id: Long): EpisodeDto

}