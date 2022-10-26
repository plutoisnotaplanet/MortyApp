package com.plutoisnotaplanet.mortyapp.application.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.plutoisnotaplanet.mortyapp.application.Constants
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileManager @Inject constructor(
    private val context: Context
) {

    var filePath: String? = null
    var inputTempUri: Uri? = null
    var outputTempUri: Uri? = null

    fun getImageTempUri(): Uri? {
        if (inputTempUri != null) return inputTempUri
        val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
        val tempImagesDir = File(
            context.filesDir,
            "Images/"
        )

        tempImagesDir.mkdir()

        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            tempImagesDir
        )
        filePath = file.absolutePath
        inputTempUri = FileProvider.getUriForFile(context, Constants.FILE_PROVIDER_AUTHORITY, file)
        return inputTempUri
    }

    fun saveBitmapInCacheAndGetFilePath(bitmap: Bitmap, name: String): String {
        return try {
            val path = context.externalCacheDir?.absolutePath
            val timeStamp = System.currentTimeMillis().toString()
            var fOut: OutputStream
            val file = File(
                path,
                "${name}_$timeStamp.jpg"
            )
            fOut = FileOutputStream(file, false)
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                85,
                fOut
            )
            fOut.flush()
            fOut.close()
            saveByMediaStore(file)
        } catch (e: Exception) {
            Log.e("FileHelper", "saveFile: $e.localizedMessage")
            throw e
        }
    }

    private fun saveByMediaStore(file: File): String {
        return try {
            val values = ContentValues()
            val myMime = MimeTypeMap.getSingleton()
            val mimeType =
                myMime.getMimeTypeFromExtension(file.extension)
            values.put(MediaStore.Downloads.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Downloads.DATE_TAKEN, System.currentTimeMillis())
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            }
            file.absolutePath
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }
}