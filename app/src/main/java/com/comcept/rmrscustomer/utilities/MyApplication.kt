package com.comcept.rmrscustomer.utilities

import android.app.Application
import com.comcept.rmrscustomer.BuildConfig
import com.testfairy.TestFairy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MyApplication:Application() {

    private val TAG = "TAG"
    override fun onCreate() {
        super.onCreate()
        TestFairy.begin(this, "SDK-4HJOUdP2")

        //Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, String.format(TAG, tag), message, t)
                }
            })
        }
    }
}