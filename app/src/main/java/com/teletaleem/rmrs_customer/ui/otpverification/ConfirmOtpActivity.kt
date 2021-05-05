package com.teletaleem.rmrs_customer.ui.otpverification

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.confirm_otp.ConfirmOtp
import com.teletaleem.rmrs_customer.data_class.registration.Registration
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP
import com.teletaleem.rmrs_customer.databinding.ActivityConfirmOtpBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import timber.log.Timber


class ConfirmOtpActivity : AppCompatActivity() ,View.OnClickListener{

    private lateinit var mBinding:ActivityConfirmOtpBinding
    private lateinit var mViewModel: ConfirmOTPViewModel
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
        mBinding.edTxtOtpAco.isFocusable=true
        reverseTimer(300)


        setClickListeners()

    }
    private fun setClickListeners() {
        mBinding.btnVerifyCodeAco.setOnClickListener(this)
        mBinding.txtResendCodeAco.setOnClickListener(this)
    }


    private fun reverseTimer(Seconds: Int) {
        mBinding.edTxtOtpAco.isEnabled=true
        mBinding.btnVerifyCodeAco.isEnabled=true
        mBinding.txtResendCodeAco.isEnabled=false
        mBinding.btnVerifyCodeAco.background=ContextCompat.getDrawable(this@ConfirmOtpActivity,R.drawable.button_background)
        mBinding.btnVerifyCodeAco.setTextColor(ContextCompat.getColor(this@ConfirmOtpActivity,R.color.white))
        mBinding.txtResendCodeAco.setTextColor(ContextCompat.getColor(this@ConfirmOtpActivity, R.color.grey))
        object : CountDownTimer((Seconds * 1000+1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()

                val hours = seconds / (60 * 60)
                val tempMint = seconds - hours * 60 * 60
                val minutes = tempMint / 60
                seconds = tempMint - minutes * 60
                mBinding.txtTimerAco.text = "Remaining time : " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds)

            }

            override fun onFinish() {
                mBinding.edTxtOtpAco.isEnabled=false
                mBinding.btnVerifyCodeAco.isEnabled=false
                mBinding.txtResendCodeAco.isEnabled=true
                mBinding.btnVerifyCodeAco.background=ContextCompat.getDrawable(this@ConfirmOtpActivity,R.drawable.button_background_grey)
                mBinding.btnVerifyCodeAco.setTextColor(ContextCompat.getColor(this@ConfirmOtpActivity,R.color.black))
                mBinding.txtResendCodeAco.setTextColor(ContextCompat.getColor(this@ConfirmOtpActivity, R.color.colorAccent))
            }
        }.start()
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_verify_code_aco -> {
                if (mOTPCode != "") {
                    if (AppGlobal.isInternetAvailable(this)) {
                        sendOTPVerificationCall()
                    } else {
                        AppGlobal.snackBar(mBinding.layoutParentAcotp, getString(R.string.err_no_internet), AppGlobal.LONG)
                    }
                }

            }
            R.id.txt_resend_code_aco -> {
                sendOTP()
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


                if (AppGlobal.isInternetAvailable(this)) {
                    sendRegistrationCall()
                } else {
                    AppGlobal.snackBar(mBinding.layoutParentAcotp, getString(R.string.err_no_internet), AppGlobal.LONG)
                }
            } else {
                progressDialog.dismiss()
                AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it?.data?.description.toString(),
                        this
                )
                mBinding.edTxtOtpAco.setOTP("")
            }

        })
    }

    private fun sendRegistrationCall(){

        mViewModel.getSignUpResponse(registration).observe(this, {
            progressDialog.dismiss()
            if (it?.data?.Token != null) {

                AppGlobal.writeString(this,AppGlobal.tokenId, it.data.Token)
                AppGlobal.writeString(this,AppGlobal.customerId,it.data.CustomerID)
                AppGlobal.writeString(this,AppGlobal.customerName,it.data.Name)
                AppGlobal.writeString(this,AppGlobal.customerEmail,it.data.Email)
                AppGlobal.writeString(this,AppGlobal.customerMobile,it.data.MobileNumber)
                AppGlobal.startNewActivity(this, CustomerHomeActivity::class.java)
                finishAffinity()
            } else {
                AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it?.data?.description.toString(),
                        this
                )
            }

        })
    }

    /*
   * Send OTP Method
   * */
    private fun sendOTP(){
        progressDialog.setLabel("Please Wait").show()
        val sendOtp= SendOTP(registration.Email, registration.MobileNumber)
        Timber.d(Gson().toJson(sendOtp))
        mViewModel.getOTPResponse(sendOtp).observe(this, {
            progressDialog.dismiss()

            AppGlobal.snackBar(mBinding.layoutParentAcotp, it?.data?.description.toString(), AppGlobal.SHORT)
            reverseTimer(300)


        })
    }


}