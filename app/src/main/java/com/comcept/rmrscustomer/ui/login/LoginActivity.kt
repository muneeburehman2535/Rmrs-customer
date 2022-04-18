package com.comcept.rmrscustomer.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.kaopiz.kprogresshud.KProgressHUD

import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.databinding.ActivityLoginBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.ui.forgotpassword.ForgotPasswordActivity
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.registration.RegistrationActivity
import com.comcept.rmrscustomer.utilities.AppGlobal


class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding:ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel
    var mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)
    private lateinit var progressDialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_login)
        mViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding.loginViewModel=mViewModel

        progressDialog=AppGlobal.setProgressDialog(this)
        setClickListeners()

//        if (AppGlobal.BUILD=="STAGING")
//        {
//            mBinding.edtxtEmailAl.setText(AppGlobal.testEmail)
//            mBinding.edtxtPasswordAl.setText(AppGlobal.testPassword)
//        }

    }

    /*
    * Set click listeners on views
    * */
    private fun setClickListeners() {
        mBinding.btnLoginAl.setOnClickListener(this)
        mBinding.txtSignupAl.setOnClickListener(this)
        mBinding.txtForgotPassAl.setOnClickListener(this)
    }

    /*
    * Check Email & Password Validation
    * */
    private fun checkCredentials() {

        mAwesomeValidation.addValidation(this, R.id.edtxt_email_al, Patterns.EMAIL_ADDRESS, R.string.err_email)
        mAwesomeValidation.addValidation(this, R.id.edtxt_password_al, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.err_password)



    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_al -> {
                checkCredentials()
                if (mAwesomeValidation.validate())
                {
                    if (AppGlobal.isInternetAvailable(this))
                    {
                        loginUser()
                    }
                    else{
                        AppGlobal.snackBar(mBinding.layoutParentLogin,getString(R.string.err_no_internet),AppGlobal.LONG)
                    }
                }

            }
            R.id.txt_signup_al->
            {
                AppGlobal.startNewActivity(this, RegistrationActivity::class.java)
            }
            R.id.txt_forgot_pass_al->
            {
                AppGlobal.startNewActivity(this, ForgotPasswordActivity::class.java)
            }
        }
    }


    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Login API Method
    * */
    private fun loginUser(){

        val login=Login(mBinding.edtxtEmailAl.text.trim().toString(), mBinding.edtxtPasswordAl.text?.trim().toString())
        mViewModel.getLoginResponse(login).observe(this) {

            when (it) {

                is Response.Loading -> {

                    progressDialog.setLabel("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {

                    it.data?.let {
                        progressDialog.dismiss()
                        if (it != null && it.Message == "Success") {
                            AppGlobal.writeString(this, AppGlobal.tokenId, it.data.Token)
                            AppGlobal.writeString(this, AppGlobal.customerId, it.data.CustomerID)
                            AppGlobal.writeString(this, AppGlobal.customerName, it.data.Name)
                            AppGlobal.writeString(this, AppGlobal.customerEmail, it.data.Email)
                            AppGlobal.writeString(
                                this,
                                AppGlobal.customerMobile,
                                it.data.MobileNumber
                            )
                            AppGlobal.startNewActivity(this, CustomerHomeActivity::class.java)

                            //AppGlobal.writeString(this,AppGlobal.customerId,"ai0anPGypI")
                            // AppGlobal.startNewActivity(this, CustomerHomeActivity::class.java)
                            finishAffinity()
                        } else {
                            AppGlobal.showDialog(
                                getString(R.string.title_alert),
                                it.Message,
                                this
                            )
                        }

                    }
                }


                is Response.Error -> {

                    AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it.message.toString(),
                        this
                    )

                    if (progressDialog.isShowing) {

                        progressDialog.dismiss()

                    }
                }


            }


        }
    }

}