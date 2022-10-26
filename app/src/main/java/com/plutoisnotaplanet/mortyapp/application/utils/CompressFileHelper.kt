package com.plutoisnotaplanet.mortyapp.application.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.roundToInt

class CompressFileHelper @Inject constructor(
    private val context: Context
) {

    fun getDecodedImage(image: String): Drawable {
        val decodedString: ByteArray = Base64.decode(image, Base64.DEFAULT)
        val decodedByte: Bitmap =
            android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return BitmapDrawable(context.resources, decodedByte)
    }

    fun getEncodedImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}