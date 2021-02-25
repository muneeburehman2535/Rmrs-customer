package com.teletaleem.rmrs_customer.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ActivityLoginBinding
import com.teletaleem.rmrs_customer.ui.view_models.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityLoginBinding
    private lateinit var mViewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bind Layout with data binding
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        mViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding.loginViewModel=mViewModel

//        //Hide Toolbar
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }



    }
}