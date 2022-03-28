package com.comcept.rmrscustomer.ui.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.data_class.login.LoginResponse
import com.comcept.rmrscustomer.repository.LoginRepository
import com.comcept.rmrscustomer.repository.Response

class LoginViewModel(application: Application):AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private var mContext:Context=application.applicationContext
    private var loginRepository: LoginRepository = LoginRepository()


    fun getLoginResponse(login:Login):LiveData<Response<LoginResponse>>{
        return loginRepository.getLoginResponseLiveData(login)
    }
}