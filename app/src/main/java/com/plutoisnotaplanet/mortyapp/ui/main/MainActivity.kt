package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.theme.MortyAppTheme
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.ref.WeakReference


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var cameraUri: WeakReference<Uri>? = null

    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess && cameraUri?.get() != null) {
                viewModel.savePhotoByUri(cameraUri!!.get()!!)
            } else viewModel.showSnack(R.string.tv_error_on_open_camera)
        }

    private val photoChooserLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.savePhotoByUri(uri)
            } else viewModel.showSnack(R.string.tv_error_on_open_gallery)
        }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MortyAppTheme {
                CompositionLocalProvider(LocalCoilImageLoader provides viewModel.imageLoader) {
                    MainScreen(viewModel)
                }
            }
        }
        observeSingleLiveEvent()
    }

    private fun observeSingleLiveEvent() {
        viewModel.mainActionEvent.observe(this) { action ->
            when (action) {
                is MainSingleEvent.OpenCamera -> {
                    cameraUri = WeakReference(action.uri)
                    cameraResultLauncher.launch(action.uri)
                }
                is MainSingleEvent.OpenGalleryChooser -> {
                    photoChooserLauncher.launch(
                        PickVisualMediaRequest.Builder()
                            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            .build()
                    )
                }
            }
        }
    }
}
