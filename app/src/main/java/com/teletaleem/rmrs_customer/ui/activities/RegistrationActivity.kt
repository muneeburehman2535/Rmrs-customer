package com.teletaleem.rmrs_customer.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.gson.Gson
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.send_otp.SendOTP
import com.teletaleem.rmrs_customer.data_class.email_mobile.Data
import com.teletaleem.rmrs_customer.data_class.email_mobile.EmailMobileVerification
import com.teletaleem.rmrs_customer.databinding.ActivityRegistrationBinding
import com.teletaleem.rmrs_customer.ui.view_models.RegistrationViewModel
import timber.log.Timber

class RegistrationActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mBinding:ActivityRegistrationBinding
    private lateinit var mViewModel:RegistrationViewModel
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_registration)
        mViewModel=ViewModelProvider(this).get(RegistrationViewModel::class.java)
        mBinding.registrationViewmodel=mViewModel

        setClickListeners()

    }

    private fun setClickListeners() {
        mBinding.btnSignupAl.setOnClickListener(this)
    }

    private fun checkCredentials(){
       // mAwesomeValidation.addValidation(mBinding.edtxtNameAr,"^[a-zA-Z\\\\s]*\$\"",getString(R.string.err_full_name))
        mAwesomeValidation.addValidation(mBinding.edtxtEmailAr,Patterns.EMAIL_ADDRESS,getString(R.string.err_email))
        mAwesomeValidation.addValidation(mBinding.edtxtMobileAl,Patterns.PHONE,getString(R.string.err_mobile))
        mAwesomeValidation.addValidation(mBinding.edtxtCnicAr, "^[0-9]{5}-[0-9]{7}-[0-9]{1}$",getString(R.string.err_cnic))
        mAwesomeValidation.addValidation(mBinding.edtxtPasswordAl,"(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",getString(R.string.err_password))
        mAwesomeValidation.addValidation(mBinding.edtxtConfirmPasswordAl,mBinding.edtxtPasswordAl,getString(R.string.err_password_confirmation))
        //mAwesomeValidation.addValidation(mBinding.edtxtAddressAl,"\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.",getString(R.string.err_address))
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_signup_al->
            {
                checkCredentials()
                if (mAwesomeValidation.validate())
                {
                    verifyEmailMobile()
                }
            }
        }
    }

    /****************************************************************************API Calls*********************************************************************/

    /*
    * Login API Method
    * */
    private fun verifyEmailMobile(){
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        val emailMobileVerification=EmailMobileVerification(mBinding.edtxtEmailAr.text.trim().toString(), mBinding.edtxtMobileAl.text.trim().toString())
        Timber.d(Gson().toJson(emailMobileVerification))
        mViewModel.getEmailMobileResponse(emailMobileVerification).observe(this, {
            if (it!=null)
            {
                checkEmailMobileValidation(it.data)
            }

        })
    }

    private fun checkEmailMobileValidation(data: Data) {
        if (!data.Email&&!data.MobileNumber)
        {
            sendOTP()
        }
        else {

        }
    }

    private fun sendOTP(){
        Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
        val sendOtp=SendOTP(mBinding.edtxtEmailAr.text.trim().toString(), mBinding.edtxtMobileAl.text.trim().toString())
        Timber.d(Gson().toJson(sendOtp))
        mViewModel.getOTPResponse(sendOtp).observe(this, {
            if (it!=null)
            {

            }

        })
    }
}