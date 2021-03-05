package com.teletaleem.rmrs_customer.ui.activities

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.confirm_otp.ConfirmOtp
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.databinding.ActivityConfirmOtpBinding
import com.teletaleem.rmrs_customer.ui.view_models.ConfirmOTPViewModel
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import timber.log.Timber


class ConfirmOtpActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var mBinding:ActivityConfirmOtpBinding
    private lateinit var mViewModel:ConfirmOTPViewModel
    private lateinit var registration:Registration
    private lateinit var progressDialog: KProgressHUD
    private lateinit var mOTPCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       mBinding=DataBindingUtil.setContentView(this, R.layout.activity_confirm_otp)
        mViewModel=ViewModelProvider(this).get(ConfirmOTPViewModel::class.java)
        mBinding.confirmOtpViewModel=mViewModel

        registration=intent.getSerializableExtra("registration") as Registration

        Timber.d("Confirm OTP Screen: ${Gson().toJson(registration)}")

        progressDialog=AppGlobal.setProgressDialog(this)


        mBinding.edTxtOtpAco.requestFocusOTP()
        mBinding.edTxtOtpAco.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                mOTPCode=otp
            }
        }


        setClickListeners()

    }
    private fun setClickListeners() {
        mBinding.btnVerifyCodeAco.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_verify_code_aco -> {
                if (mBinding.edTxtOtpAco.isNotEmpty())
                {
                    sendOTPVerificationCall()
                }

            }
        }
    }


    private fun sendOTPVerificationCall(){
        progressDialog.setLabel("Please Wait").show()
        val confirmOtp= ConfirmOtp(
            registration.Email,
            mOTPCode
        )
        Timber.d(Gson().toJson(confirmOtp))
        mViewModel.getOTPVerificationResponse(confirmOtp).observe(this, {
            if (it?.data?.status == true) {
                Timber.d("OTP Verification: ${Gson().toJson(it)}")
                sendRegistrationCall()
            } else {
                progressDialog.dismiss()
                AppGlobal.showDialog(
                    getString(R.string.title_alert),
                    it?.data?.description.toString(),
                    this
                )
            }

        })
    }

    private fun sendRegistrationCall(){

        mViewModel.getSignUpResponse(registration).observe(this, {
            progressDialog.dismiss()
            if (it?.message == "Success") {
                Timber.d("Registration: ${Gson().toJson(it)}")
                AppGlobal.saveToken(this, it.data.Token)
            } else {
                AppGlobal.showDialog(
                    getString(R.string.title_alert),
                    it?.data?.description.toString(),
                    this
                )
            }

        })
    }


}