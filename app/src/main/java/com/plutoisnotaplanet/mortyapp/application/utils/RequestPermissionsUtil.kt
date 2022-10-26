package com.plutoisnotaplanet.mortyapp.application.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.MainThread
import com.markodevcic.peko.PermissionResult
import com.markodevcic.peko.requestPermissionsAsync
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

var cameraPermissions =
    listOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

val storagePermissions = listOf(
    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
)

interface Listener {
    @MainThread
    fun onGranted(result: PermissionResult.Granted) {
    }
    /**
     * Called if the request is cancelled.
     */
    @MainThread
    fun onCancel(result: PermissionResult.Cancelled) {
    }

    /**
     * Called if an error occurs while executing the request.
     */
    @MainThread
    fun onDenied(result: PermissionResult.Denied) {
    }
}

inline fun PermissionResult.listener(
    crossinline onGranted: (PermissionResult.Granted) -> Unit = {},
    crossinline onCancelled: (PermissionResult.Cancelled) -> Unit = {},
    crossinline onDenied: (PermissionResult.Denied) -> Unit = {}
): Listener {
    val listener = object : Listener {
        override fun onCancel(result: PermissionResult.Cancelled) = onCancelled(result)
        override fun onGranted(result: PermissionResult.Granted) = onGranted(result)
        override fun onDenied(result: PermissionResult.Denied) = onDenied(result)
    }
    when (this) {
        is PermissionResult.Denied -> listener.onDenied(this)
        is PermissionResult.Granted -> listener.onGranted(this)
        is PermissionResult.Cancelled -> listener.onCancel(this)
    }
    return listener
}

suspend fun Fragment.checkCameraPermissions() =
    this.requestPermissionsAsync(*cameraPermissions.toTypedArray())

suspend fun Fragment.checkGalleryPermissions() =
    this.requestPermissionsAsync(*storagePermissions.toTypedArray())
