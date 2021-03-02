package com.teletaleem.rmrs_customer.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.databinding.ActivityLoginBinding
import com.teletaleem.rmrs_customer.ui.view_models.LoginViewModel
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding:ActivityLoginBinding
    private lateinit var mViewModel:LoginViewModel
    var mAwesomeValidation = AwesomeValidation(ValidationStyle.BASIC)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bind Layout with data binding
        mBinding=DataBindingUtil.setContentView(this, R.layout.activity_login)
        mViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        mBinding.loginViewModel=mViewModel

        setClickListeners()



    }

    private fun setClickListeners() {
        mBinding.btnLoginAl.setOnClickListener(this)
        mBinding.txtSignupAl.setOnClickListener(this)
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_al -> {
                checkCredentials()
                if (mAwesomeValidation.validate())
                {
                    loginUser()
                }
            }
            R.id.txt_signup_al->
            {
                val intent=Intent(this,RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkCredentials() {
       // val password="(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}"
        val password = Pattern.compile(
                "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}"
        )

        mAwesomeValidation.addValidation(this, R.id.edtxt_email_al, Patterns.EMAIL_ADDRESS, R.string.err_email)
        mAwesomeValidation.addValidation(this, R.id.edtxt_password_al, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.err_password)



    }


    /****************************************************************************API Calls*********************************************************************/

    /*
    * Login API Method
    * */
    private fun loginUser(){
        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
        val login=Login(mBinding.edtxtEmailAl.text.trim().toString(), mBinding.edtxtPasswordAl.text.trim().toString())
        mViewModel.getLoginResponse(login).observe(this, {
        })
    }

}