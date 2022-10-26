package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import android.net.Uri
import com.plutoisnotaplanet.mortyapp.application.data.database.DbFindHelper
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.UserProfileEntity
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import com.plutoisnotaplanet.mortyapp.application.domain.model.LocalPhotoModel
import com.plutoisnotaplanet.mortyapp.application.domain.model.PhotoType
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.utils.BitmapFactory
import com.plutoisnotaplanet.mortyapp.application.utils.CompressFileHelper
import com.plutoisnotaplanet.mortyapp.application.utils.FileManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val fileManager: FileManager,
    private val bitmapFactory: BitmapFactory,
    private val compressFileHelper: CompressFileHelper,
    private val dbFindHelper: DbFindHelper,
    private val preferences: MortyPreferences
) : ProfileRepository {

    private val dataBase: MortyDataBase
        get() = dbFindHelper.getDatabase()

    override suspend fun getSelfProfile(): Flow<UserProfile> {
        return if (dataBase.userProfileDao.hasProfile(preferences.email)) {
            dataBase.userProfileDao.getProfileByEmailFlow(preferences.email).map { it.toModel() }
        } else {
            dataBase.userProfileDao.insert(
                UserProfileEntity(
                    email = preferences.email
                ))
            dataBase.userProfileDao.getProfileByEmailFlow(preferences.email).map { it.toModel() }
        }
    }

    override suspend fun deleteAvatar() {
        val currentProfile = dataBase.userProfileDao.getProfileByEmail(preferences.email)
        currentProfile.copy(photoData = LocalPhotoModel()).let { dataBase.userProfileDao.save(it) }
    }

    override suspend fun getInputUriForPhoto(): Uri {
        return fileManager.getImageTempUri()!!
    }

    override suspend fun saveAvatarByUri(uri: Uri) {
        val bitmap = bitmapFactory.createBitmapFromUri(uri)

        val compressedInBase64 = compressFileHelper.getEncodedImage(bitmap)
        val photoPath =
            fileManager.saveBitmapInCacheAndGetFilePath(bitmap, PhotoType.Avatar.name)

        if (dataBase.userProfileDao.hasProfile(preferences.email)) {
            dataBase.userProfileDao.getProfileByEmail(preferences.email).copy(
                photoData = LocalPhotoModel(
                    photoPath = photoPath,
                    photoUri = uri.toString(),
                    photoInBase64 = compressedInBase64,
                    photoType = PhotoType.Avatar
                )
            ).let { dataBase.userProfileDao.save(it) }
        } else {
            dataBase.userProfileDao.save(
                UserProfileEntity(
                    email = preferences.email,
                    photoData = LocalPhotoModel(
                        photoPath = photoPath,
                        photoUri = uri.toString(),
                        photoInBase64 = compressedInBase64,
                        photoType = PhotoType.Avatar
                    )
                )
            )
        }
    }
}