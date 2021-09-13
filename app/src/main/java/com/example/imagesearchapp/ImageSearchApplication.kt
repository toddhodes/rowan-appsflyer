package com.example.imagesearchapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import com.appsflyer.AppsFlyerLib
import com.appsflyer.AppsFlyerConversionListener
import java.util.*


@HiltAndroidApp
class ImageSearchApplication : Application() {

    private val AF_DEV_KEY = "xTZBvkNLiBuWdH86YzeTsC"
    val LOG_TAG = "AppsFlyerOneLinkSimApp"
    val DL_ATTRS = "dl_attrs"
    var conversionData: Map<String, Any>? = null

    override fun onCreate() {
        super.onCreate()

        val pkg = this.applicationContext.packageName
        Log.i("init", "pkg: $pkg")

        initAppsFlyer()
    }

    private fun initAppsFlyer() {
        val appsflyer = AppsFlyerLib.getInstance()
        // debug
        appsflyer.setDebugLog(true);

        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionDataMap: Map<String, Any>) {
                for (attrName in conversionDataMap.keys)
                    Log.d(
                        LOG_TAG,
                        "Conversion attribute: " + attrName + " = " + conversionDataMap[attrName]
                    )
                val status: String =
                    Objects.requireNonNull(conversionDataMap["af_status"]).toString()
                if (status == "Non-organic") {
                    if (Objects.requireNonNull(conversionDataMap["is_first_launch"]).toString() == "true") {
                        Log.d(LOG_TAG, "Conversion: First Launch")
                    } else {
                        Log.d(LOG_TAG, "Conversion: Not First Launch")
                    }
                } else {
                    Log.d(LOG_TAG, "Conversion: This is an organic install.")
                }
                conversionData = conversionDataMap
            }

            override fun onConversionDataFail(errorMessage: String) {
                Log.d(LOG_TAG, "error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call.")
            }

            override fun onAttributionFailure(errorMessage: String) {
                Log.d(LOG_TAG, "error onAttributionFailure : $errorMessage")
            }
        }

        // init
        appsflyer.init(AF_DEV_KEY, conversionListener, this)

        // start
        appsflyer.start(this)
    }
}