package com.plutoisnotaplanet.mortyapp.application.utils

import android.content.Context
import android.graphics.*
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class BitmapFactory
@Inject constructor(private val context: Context) {
    fun createFromLayout(@LayoutRes resId: Int): Bitmap {
        val view = LayoutInflater.from(context).inflate(resId, null)

        return createFromView(view)
    }

    fun createFromDrawable(@DrawableRes drawableId: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(context, drawableId)!!

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun createFromView(view: View): Bitmap {
        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(measureSpec, measureSpec)

        val measuredWidth = view.measuredWidth
        val measuredHeight = view.measuredHeight

        view.layout(0, 0, measuredWidth, measuredHeight)
        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.TRANSPARENT)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


    fun createFromUri(uri: Uri, options: BitmapFactory.Options): Bitmap? {
        val inStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inStream)
    }

    private fun getExifInterfaceFromPath(photoPath: String): Float {
        val ei = ExifInterface(photoPath)
        val orientation = ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            ExifInterface.ORIENTATION_NORMAL -> 0f
            else -> 0f
        }
    }

    private fun rotateImage(bitmap: Bitmap, rotate: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotate)
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true
        )
    }

    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 45, stream)
        return stream.toByteArray()
    }

    fun createDrawableFromUri(uri: Uri): Drawable {
        val inStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inStream)
        return BitmapDrawable(context.resources, bitmap)
    }

    fun createBitmapFromUri(uri: Uri): Bitmap {
        val inStream = context.contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inStream)
    }
}