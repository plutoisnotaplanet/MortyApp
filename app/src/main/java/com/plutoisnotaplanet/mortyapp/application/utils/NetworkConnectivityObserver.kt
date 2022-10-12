package com.plutoisnotaplanet.mortyapp.application.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.runtime.currentRecomposeScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver
@Inject constructor(
    context: Context
) : ConnectivityObserver {

    override var currentStatus: ConnectivityObserver.Status = ConnectivityObserver.Status.Available

    override val hasConnection: Boolean
        get() = connectivityManager.isDefaultNetworkActive

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    currentStatus = ConnectivityObserver.Status.Available
                    launch { send(currentStatus) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    currentStatus = ConnectivityObserver.Status.Unavailable
                    launch { send(currentStatus) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    currentStatus = ConnectivityObserver.Status.Losing
                    launch { send(currentStatus) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    currentStatus = ConnectivityObserver.Status.Lost
                    launch { send(currentStatus) }
                }
            }


            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }


}