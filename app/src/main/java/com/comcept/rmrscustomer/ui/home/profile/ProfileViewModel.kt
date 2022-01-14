package com.comcept.rmrscustomer.ui.home.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.comcept.rmrscustomer.data_class.login.Login
import com.comcept.rmrscustomer.data_class.login.LoginResponse
import com.comcept.rmrscustomer.data_class.profile.Profile
import com.comcept.rmrscustomer.data_class.profile.profileresponse.ProfileResponse
import com.comcept.rmrscustomer.repository.ProfileRepository

class ProfileViewModel(application: Application): AndroidViewModel(application){
    private var profileRepository: ProfileRepository = ProfileRepository()


    fun updateProfileResponse(profile: Profile): LiveData<ProfileResponse> {
        return profileRepository.getProfileResponseLiveData(profile)
    }
}