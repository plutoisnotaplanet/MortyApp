package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import android.net.Uri
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getInputUriForPhoto(): Uri

    suspend fun saveAvatarByUri(uri: Uri)

    suspend fun getSelfProfile(): Flow<UserProfile>

    suspend fun deleteAvatar()
}