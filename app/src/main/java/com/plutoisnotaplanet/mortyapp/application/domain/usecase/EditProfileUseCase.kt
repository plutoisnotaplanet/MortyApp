package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import android.net.Uri
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface EditProfileUseCase {

    suspend fun clearDataBase(): Response<Boolean>

    suspend fun selfProfile(): Flow<UserProfile>

    suspend fun getInputUriForPhoto(): Response<Uri>

    suspend fun savePhotoByUri(uri: Uri): Response<Unit>

    suspend fun deleteAvatar(): Response<Unit>
}