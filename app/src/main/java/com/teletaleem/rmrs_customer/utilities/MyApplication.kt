package com.teletaleem.rmrs_customer.utilities

import android.app.Application
import com.testfairy.TestFairy

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        TestFairy.begin(this, "SDK-4HJOUdP2");
    }
}