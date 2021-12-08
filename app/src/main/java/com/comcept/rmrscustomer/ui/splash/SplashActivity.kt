package com.comcept.rmrscustomer.ui.splash

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.databinding.ActivitySplashBinding
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.login.LoginActivity
import com.comcept.rmrscustomer.utilities.AppGlobal

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 4000
    private lateinit var mBinding: ActivitySplashBinding
    private lateinit var topAnim:Animation
    private lateinit var bottomAnim:Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    //    setContentView(R.layout.activity_splash)

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_splash)


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        topAnim=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        mBinding.imageView.animation=topAnim
        mBinding.textView.animation=bottomAnim
        mBinding.textView2.animation=bottomAnim
        mBinding.textView3.animation=bottomAnim

        Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (AppGlobal.readString(this,AppGlobal.tokenId,"0")!="0")
                    {
                        AppGlobal.startNewActivity(this,CustomerHomeActivity::class.java)
                    }
                    else
                    {
                        AppGlobal.startNewActivity(this,LoginActivity::class.java)

                    }
                    //AppGlobal.startNewActivity(this, LoginActivity::class.java)
                    finish()
                }, SPLASH_TIME_OUT)
    }


}