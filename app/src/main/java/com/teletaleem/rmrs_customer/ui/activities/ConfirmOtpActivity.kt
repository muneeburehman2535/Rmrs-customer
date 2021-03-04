package com.teletaleem.rmrs_customer.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.confirm_otp.ConfirmOtp
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP
import com.teletaleem.rmrs_customer.databinding.ActivityConfirmOtpBinding
import com.teletaleem.rmrs_customer.shared_view_models.RegistrationSharedViewModel
import com.teletaleem.rmrs_customer.ui.view_models.ConfirmOTPViewModel
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import timber.log.Timber

class ConfirmOtpActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var mBinding:ActivityConfirmOtpBinding
    private lateinit var mViewModel:ConfirmOTPViewModel
    private lateinit var registration:Registration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       mBinding=DataBindingUtil.setContentView(this,R.layout.activity_confirm_otp)
        mViewModel=ViewModelProvider(this).get(ConfirmOTPViewModel::class.java)
        mBinding.confirmOtpViewModel=mViewModel

        registration=intent.getSerializableExtra("registration") as Registration

        Timber.d("Confirm OTP Screen: ${Gson().toJson(registration)}")

        setClickListeners()

    }

    private fun setClickListeners() {
        mBinding.button.setOnClickListener(this)
    }


    private fun sendOTPVerificationCall(){
        val confirmOtp= ConfirmOtp(registration.Email, mBinding.editTextNumber.text.trim().toString())
        Timber.d(Gson().toJson(confirmOtp))
        mViewModel.getOTPVerificationResponse(confirmOtp).observe(this, {
            if (it?.data?.status == true)
            {
                Timber.d("OTP Verification: ${Gson().toJson(it)}")
            }
            else
            {
                AppGlobal.showDialog(getString(R.string.title_alert), it?.data?.description.toString(),this)
            }

        })
    }

    private fun sendRegistrationCall(){

        mViewModel.getSignUpResponse(registration).observe(this, {
            if (it?.message =="Success")
            {
                Timber.d("Registration: ${Gson().toJson(it)}")
                AppGlobal.saveToken(this,it.data.Token)
            }
            else
            {
                AppGlobal.showDialog(getString(R.string.title_alert), it?.data?.description.toString(),this)
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.button->
            {
                sendOTPVerificationCall()
            }
        }
    }
}