package com.example.imagesearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.appsflyer.AppsFlyerLib

@HiltAndroidApp
class ImageSearchApplication : Application() {

    private val AF_DEV_KEY = "xTZBvkNLiBuWdH86YzeTsC"

    override fun onCreate() {
        super.onCreate()

        // init
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, null, this);

        // start
        AppsFlyerLib.getInstance().start(this)
        AppsFlyerLib.getInstance().setDebugLog(true);
    }
}