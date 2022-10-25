package com.plutoisnotaplanet.mortyapp.ui.theme.compose.utils

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.ref.WeakReference

class SnackbarController
constructor(
    private val scope: CoroutineScope
){

    private var snackbarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String = ""
    ){
        Timber.e("scsc $message")
        if (message.isNotBlank()) {
            if(snackbarJob == null){
                snackbarJob = scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel
                    )
                    cancelActiveJob()
                }
            }
            else {
                cancelActiveJob()
                snackbarJob = scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel
                    )
                    cancelActiveJob()
                }
            }
        }
    }

    private fun cancelActiveJob(){
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}