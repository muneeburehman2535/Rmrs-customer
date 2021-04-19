package com.teletaleem.rmrs_customer.shared_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teletaleem.rmrs_customer.data_class.checkout.Checkout
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Menu

class SharedViewModel: ViewModel() {

    var mEditProfile = MutableLiveData<Boolean>()
    var restaurantID=MutableLiveData<String>()
    var menuList= MutableLiveData<ArrayList<Menu>>()
    var restaurantName=MutableLiveData<String>()
    var checkout=MutableLiveData<Checkout>()




    /*
    * Allow Edit Profile
    * */
    fun updateEditProfileData(editProfile: Boolean) {
        mEditProfile.value = editProfile
    }

    /*
    * Update Restaurant Id
    * */
    fun updateRestaurantId(restaurantId:String){
        restaurantID.value=restaurantId
    }

    /*
   * Update Restaurant Id
   * */
    fun updateMenuList(menuList:ArrayList<Menu>){
        this.menuList.value=menuList
    }

    /*
   * Update Restaurant Id
   * */
    fun updateRestaurantName(restaurantName:String){
        this.restaurantName.value=restaurantName
    }

    /*
  * Update Restaurant Id
  * */
    fun updateCheckoutOrder(checkout:Checkout){
        this.checkout.value=checkout
    }

}