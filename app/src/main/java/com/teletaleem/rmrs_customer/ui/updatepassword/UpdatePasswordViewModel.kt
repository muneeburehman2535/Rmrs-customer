package com.teletaleem.rmrs_customer.ui.updatepassword

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.teletaleem.rmrs_customer.data_class.profile.Profile
import com.teletaleem.rmrs_customer.data_class.profile.profileresponse.ProfileResponse
import com.teletaleem.rmrs_customer.data_class.updatepassword.UpdatePassword
import com.teletaleem.rmrs_customer.data_class.updatepassword.UpdatePasswordResponse
import com.teletaleem.rmrs_customer.repository.UpdatePasswordRepository

class UpdatePasswordViewModel(application: Application): AndroidViewModel(application){
    private var updatePasswordRepository: UpdatePasswordRepository = UpdatePasswordRepository()


    fun updatePasswordResponse(updatePassword: UpdatePassword): LiveData<UpdatePasswordResponse> {
        return updatePasswordRepository.getUpdatePasswordResponseLiveData(updatePassword)
    }
}