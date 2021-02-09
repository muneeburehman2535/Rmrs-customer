package com.teletaleem.rmrs_customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.utilities.AppGlobal

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (!AppGlobal.getToken(this).equals("")&&!AppGlobal.getToken(this).equals("null")&&!AppGlobal.getToken(this).equals("0"))
                    {}
                    else
                    {
                        AppGlobal.startNewActivity(this,LoginActivity::class.java)
                        finish()
                    }
                }, SPLASH_TIME_OUT)
    }
}