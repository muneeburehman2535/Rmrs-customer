package com.teletaleem.rmrs_customer.utilities

import android.app.Application
import com.testfairy.TestFairy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        TestFairy.begin(this, "SDK-4HJOUdP2");
    }
}