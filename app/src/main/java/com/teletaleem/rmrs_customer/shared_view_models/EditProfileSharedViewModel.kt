package com.teletaleem.rmrs_customer.shared_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditProfileSharedViewModel: ViewModel() {

    var mEditProfile = MutableLiveData<Boolean>()

    fun updateEditProfileData(editProfile: Boolean) {
        mEditProfile.value = editProfile
    }
}