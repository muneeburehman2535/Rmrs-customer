package com.comcept.rmrscustomer.utilities

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppExecutor {

    private var sInstance: ExecutorService? = null

    fun getExecutorService(): ExecutorService? {
        if (sInstance == null) {
            sInstance = Executors.newSingleThreadExecutor()
            // sInstance = Executors.newFixedThreadPool(3);
        }
        return sInstance
    }
}