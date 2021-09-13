package com.example.imagesearchapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import com.appsflyer.AppsFlyerLib

@HiltAndroidApp
class ImageSearchApplication : Application() {

    private val AF_DEV_KEY = "xTZBvkNLiBuWdH86YzeTsC"

    override fun onCreate() {
        super.onCreate()

        val pkg = this.applicationContext.packageName
        Log.i("init", "pkg: $pkg")

        // init
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, null, this);

        // start
        AppsFlyerLib.getInstance().start(this)
        AppsFlyerLib.getInstance().setDebugLog(true);
    }
}