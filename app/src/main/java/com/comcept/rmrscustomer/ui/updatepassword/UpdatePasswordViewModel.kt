package com.comcept.rmrscustomer.ui.updatepassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.profile.Profile
import com.comcept.rmrscustomer.data_class.profile.profileresponse.ProfileResponse
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePassword
import com.comcept.rmrscustomer.data_class.updatepassword.UpdatePasswordResponse
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.repository.UpdatePasswordRepository

class UpdatePasswordViewModel(application: Application): AndroidViewModel(application){
    private var updatePasswordRepository: UpdatePasswordRepository = UpdatePasswordRepository()


    fun updatePasswordResponse(updatePassword: UpdatePassword): LiveData<Response<UpdatePasswordResponse>> {
        return updatePasswordRepository.getUpdatePasswordResponseLiveData(updatePassword)
    }
}