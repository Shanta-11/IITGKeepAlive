package com.shanta.keepalive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import kotlin.concurrent.thread


class NetworkChangeReciever: BroadcastReceiver() {
    var bound = false
    override fun onReceive(context: Context?, intent: Intent?) {
        bound = false
        Log.d("Brodcast reciever", "Broadcast Recieved")
        val wifiState = intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1)

        Log.d("Brodcast reciever", "wifi state:" + wifiState.toString())
        if (intent != null) {
            intent.action?.let { Log.d("Brodcast reciever", it) }
        }

        if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent!!.action && WifiManager.WIFI_STATE_ENABLED == wifiState) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            val networkCallback = @RequiresApi(Build.VERSION_CODES.S)
            object : ConnectivityManager.NetworkCallback(
                FLAG_INCLUDE_LOCATION_INFO) {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    val wifiInfo = networkCapabilities.transportInfo as WifiInfo
                    val ssid = wifiInfo.ssid
                    Log.d("Brodcast reciever", "ssid: $ssid")
                    if(ssid == "Shanta's Dell"){
                        connectivityManager?.bindProcessToNetwork(network)
                        bound = true
                    }
                }


            }
            connectivityManager?.registerNetworkCallback(request, networkCallback)

            thread {
                while (true){
                    if(bound){
                        connectivityManager?.unregisterNetworkCallback(networkCallback)
                        Log.d("Brodcast reciever", "Unregistered ")
                    }
                }
            }



        }
    }
}