package com.plutoisnotaplanet.mortyapp.application.data.interactors

import android.net.Uri
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.ProfileRepository
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.domain.model.runResulting
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditProfileInteractor @Inject constructor(
    private val profileRepository: ProfileRepository
): EditProfileUseCase {

    override suspend fun selfProfile(): Flow<UserProfile> {
        return profileRepository.getSelfProfile()
    }

    override suspend fun getInputUriForPhoto(): Response<Uri> {
        return runResulting {
            profileRepository.getInputUriForPhoto()
        }
    }

    override suspend fun savePhotoByUri(uri: Uri): Response<Unit> {
        return runResulting {
            profileRepository.saveAvatarByUri(uri)
        }
    }

    override suspend fun deleteAvatar(): Response<Unit> {
        return runResulting {
            profileRepository.deleteAvatar()
        }
    }
}