package com.teletaleem.rmrs_customer.ui.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mBinding:ActivityForgotPasswordBinding
    private lateinit var mViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
        mViewModel=ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        mBinding.forgotPasswordViewModel=mViewModel

        setClickListeners()
    }

    private fun setClickListeners() {

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {}
    }


    /***********************************************************API Calls**************************************************/

}