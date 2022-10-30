package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.plutoisnotaplanet.mortyapp.ui.theme.MortyAppTheme
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var cameraUri: WeakReference<Uri>? = null

    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess && cameraUri?.get() != null) {
                viewModel.savePhotoByUri(cameraUri?.get()!!)
            }
        }

    private val photoChooserLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.savePhotoByUri(uri)
            }
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
        observeMainAction()
    }

    private fun observeMainAction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.singleAction.collectLatest { action ->
                    when (action) {
                        is MainAction.OpenCamera -> {
                            cameraUri = WeakReference(action.uri)
                            cameraResultLauncher.launch(action.uri)
                        }
                        is MainAction.OpenGalleryChooser -> {
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
    }
}
