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
        appsflyer.setDebugLog(true)

        val conversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionDataMap: Map<String, Any>) {
                for (attrName in conversionDataMap.keys)
                    log("Conversion attribute: " + attrName
                            + " = " + conversionDataMap[attrName])
                val status: String = conversionDataMap["af_status"]?.toString() ?: ""
                if (status == "Non-organic") {
                    if (conversionDataMap["is_first_launch"]?.toString() == "true") {
                        log("Conversion: First Launch")
                    } else {
                        log("Conversion: Not First Launch")
                    }
                } else {
                    log("Conversion: This is an organic install.")
                }
                conversionData = conversionDataMap
            }

            override fun onConversionDataFail(errorMessage: String) {
                log("error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    log("onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }

            override fun onAttributionFailure(errorMessage: String) {
                log("error onAttributionFailure : $errorMessage")
            }
        }

        // init
        appsflyer.init(AF_DEV_KEY, conversionListener, this)

        // start
        appsflyer.start(this)
    }

    private fun log(s: String) {
        Log.d(LOG_TAG, s)
    }
}