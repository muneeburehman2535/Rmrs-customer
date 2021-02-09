package com.teletaleem.rmrs_customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.teletaleem.rmrs_customer.R

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
                {
//                    if (!AppUtils.getToken(this).equals("")&&!AppUtils.getToken(this).equals("null")&&!AppUtils.getToken(this).equals("0"))
//                    {}
//                    else
//                    {
//                        AppUtils.startNewActivity(this,LoginActivity::class.java)
//                        finish()
//                    }
                }, SPLASH_TIME_OUT)
    }
}