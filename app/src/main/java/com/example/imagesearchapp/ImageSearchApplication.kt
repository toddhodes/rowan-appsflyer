package com.example.imagesearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.appsflyer.AppsFlyerLib

@HiltAndroidApp
class ImageSearchApplication : Application() {

    private val AF_DEV_KEY = ""

    override fun onCreate() {
        super.onCreate()

        // init
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, null, this);

        // start
        AppsFlyerLib.getInstance().start(this)
        AppsFlyerLib.getInstance().setDebugLog(true);

        googleInstallReferrer()
    }

    //private lateinit var referrerClient: InstallReferrerClient
    private fun googleInstallReferrer() {
//        referrerClient = InstallReferrerClient.newBuilder(this).build()
//        referrerClient.startConnection(object : InstallReferrerStateListener {
//
//            override fun onInstallReferrerSetupFinished(responseCode: Int) {
//                when (responseCode) {
//                    InstallReferrerResponse.OK -> {
//                        // Connection established.
//                    }
//                    InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
//                        // API not available on the current Play Store app.
//                    }
//                    InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
//                        // Connection couldn't be established.
//                    }
//                }
//            }
//
//            override fun onInstallReferrerServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//            }
//        })
    }
}