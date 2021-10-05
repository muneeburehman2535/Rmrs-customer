package com.comcept.rmrscustomer.ui.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.google.gson.Gson
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.send_otp.SendOTP
import com.comcept.rmrscustomer.data_class.email_mobile.EmailMobileVerification
import com.comcept.rmrscustomer.data_class.registration.Registration
import com.comcept.rmrscustomer.databinding.ActivityRegistrationBinding
import com.comcept.rmrscustomer.ui.otpverification.ConfirmOtpActivity
import com.comcept.rmrscustomer.utilities.AppGlobal
import timber.log.Timber

class RegistrationActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var mBinding:ActivityRegistrationBinding
    private lateinit var mViewModel: RegistrationViewModel
    private val mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
    private lateinit var progressDialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_registration)
        mViewModel=ViewModelProvider(this).get(RegistrationViewModel::class.java)
        mBinding.registrationViewmodel=mViewModel

        progressDialog=AppGlobal.setProgressDialog(this)
        setClickListeners()

    }

    private fun setClickListeners() {
        mBinding.btnSignupAl.setOnClickListener(this)
    }

    /*
    * Check following credentials:
    * 1:- Full name.
    * 2:- Email Address
    * 3:- CNIC
    * 4:- Password
    * 5:- Confirm Password
    * 6:- Postal Address
    * */
    private fun checkCredentials(){
        mAwesomeValidation.addValidation(mBinding.edtxtNameAr,"[a-zA-Z ]+",getString(R.string.err_full_name))
        mAwesomeValidation.addValidation(mBinding.edtxtEmailAr,Patterns.EMAIL_ADDRESS,getString(R.string.err_email))
        mAwesomeValidation.addValidation(mBinding.edtxtMobileAl,Patterns.PHONE,getString(R.string.err_mobile))
        mAwesomeValidation.addValidation(mBinding.edtxtCnicAr, "^[0-9]{13}$",getString(R.string.err_cnic))
        mAwesomeValidation.addValidation(mBinding.edtxtPasswordAl,"(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",getString(R.string.err_password))
        mAwesomeValidation.addValidation(mBinding.edtxtConfirmPasswordAl,mBinding.edtxtPasswordAl,getString(R.string.err_password_confirmation))
        //mAwesomeValidation.addValidation(mBinding.edtxtAddressAl, "[a-zA-Z0-9 ]+",getString(R.string.err_address))
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

    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Verify email and mobile number API method
    * */
    private fun verifyEmailMobile(){
        progressDialog.setLabel("Please Wait").show()
        val emailMobileVerification=EmailMobileVerification(mBinding.edtxtEmailAr.text.trim().toString(), mBinding.edtxtMobileAl.text.trim().toString())
        Timber.d(Gson().toJson(emailMobileVerification))
        mViewModel.getEmailMobileResponse(emailMobileVerification).observe(this, {
            if (it!=null)
            {
                if (!it.data.Email&&!it.data.MobileNumber)
                {
                    if (AppGlobal.isInternetAvailable(this))
                    {
                        sendOTP()
                    }
                    else{
                        AppGlobal.snackBar(mBinding.layoutParentReg,getString(R.string.err_no_internet),AppGlobal.LONG)
                    }
                }
                else {
                    progressDialog.dismiss()
                    AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,this)
                }
            }

        })
    }

    /*
    * Send OTP Method
    * */
    private fun sendOTP(){
        val sendOtp=SendOTP(mBinding.edtxtEmailAr.text.trim().toString(), mBinding.edtxtMobileAl.text.trim().toString())
        Timber.d(Gson().toJson(sendOtp))
        mViewModel.getOTPResponse(sendOtp).observe(this, {
            progressDialog.dismiss()
            if (it?.data?.status == true)
            {
                startActivity(Intent(this, ConfirmOtpActivity::class.java).putExtra("registration",Registration(
                    mBinding.edtxtNameAr.text.toString().trim(),
                    mBinding.edtxtEmailAr.text.toString().trim(),
                    mBinding.edtxtCnicAr.text.toString().trim(),
                    mBinding.edtxtMobileAl.text.toString().trim(),
                    mBinding.edtxtPhnAl.text.toString().trim(),
                    mBinding.edtxtPasswordAl.text.toString().trim(),
                    mBinding.edtxtAddressAl.text.toString().trim())))
            }
            else
            {
                AppGlobal.showDialog(getString(R.string.title_alert), it?.data?.description.toString(),this)
            }

        })
    }
}