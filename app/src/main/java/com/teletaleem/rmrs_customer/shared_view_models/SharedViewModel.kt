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
    var ratingCount=MutableLiveData<Int>()
    var sumOfRating=MutableLiveData<Double>()
    var ownerId= MutableLiveData<String>()
    var menuItem=MutableLiveData<Menu>()



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
    fun updateCheckout(checkout: Checkout){
        this.checkout.value=checkout
    }

    /*
  * Update Rating Count
  * */
    fun updateRatingCount(ratingCount:Int){
        this.ratingCount.value=ratingCount
    }

    /*
    * Update Sum Of Rating
    * */
    fun updateSumOfRating(sumOfRating:Double){
        this.sumOfRating.value=sumOfRating
    }

    /*
    * Update Sum Of Rating
    * */
    fun updateOwnerId(ownerId:String){
        this.ownerId.value=ownerId
    }

    /*
    * Update Menu Item
    * */
    fun updateMenuItem(menu:Menu){
        this.menuItem.value=menu
    }

}