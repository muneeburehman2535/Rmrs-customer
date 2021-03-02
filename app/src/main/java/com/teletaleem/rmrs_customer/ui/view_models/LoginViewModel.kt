package com.teletaleem.rmrs_customer.ui.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.data_class.login.LoginResponse
import com.teletaleem.rmrs_customer.repository.LoginRepository

class LoginViewModel(application: Application):AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private var mContext:Context=application.applicationContext
    private var loginRepository: LoginRepository = LoginRepository()


    fun getLoginResponse(login:Login):LiveData<LoginResponse>{
        return loginRepository.getLoginResponseLiveData(login)
    }
}