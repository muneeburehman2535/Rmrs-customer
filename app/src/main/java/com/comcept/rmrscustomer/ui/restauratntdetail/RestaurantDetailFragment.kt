package com.comcept.rmrscustomer.ui.restauratntdetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.TabsAdapter
import com.comcept.rmrscustomer.data_class.restaurantdetail.Menu
import com.comcept.rmrscustomer.data_class.restaurantdetail.RestaurantDetailResponse
import com.comcept.rmrscustomer.databinding.RestaurantDetailFragmentBinding
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.reservation.ReservationActivity
import com.comcept.rmrscustomer.ui.review.restaurantreviews.ReviewsListFragment
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.comcept.rmrscustomer.utilities.BlurBuilder
import com.comcept.rmrscustomer.utilities.GPSTracker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() ,TabsAdapter.ViewClickListener,View.OnClickListener{
    private lateinit var mBinding:RestaurantDetailFragmentBinding
    private lateinit var menuList:ArrayList<Menu>

    companion object {
        fun newInstance() = RestaurantDetailFragment()
    }

    private lateinit var viewModel: RestaurantDetailViewModel
    private lateinit var progressDialog: KProgressHUD
    private lateinit var restaurantId:String
    private var gps: GPSTracker? = null
    private lateinit var restaurantDetailResponse: RestaurantDetailResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(
            inflater,
            R.layout.restaurant_detail_fragment,
            container,
            false
        )
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RestaurantDetailViewModel::class.java)

        mBinding.restaurantDetailViewModel=viewModel
        mBinding.fabRestaurantDetail.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireActivity(),ReservationActivity::class.java))
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        setBlurBackgroundImage()
        mBinding.imgRestaurantFrd.clipToOutline=true
        //setTabLayout()
        getRestaurantId()
        setViewClickListener()

    }

    private fun setViewClickListener() {
        mBinding.imgRestaurantFrd.setOnClickListener(this)
        mBinding.txtDistanceFrd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.txt_distance_frd->{

                getLatLong()
            }
            R.id.img_restaurant_frd->{
                (activity as CustomerHomeActivity).changeToolbarName(
                    getString(R.string.title_reviews),
                    isProfileMenuVisible = false,
                    locationVisibility = false,isMenuVisibility = false
                )
                (activity as CustomerHomeActivity).loadNewFragment(
                    ReviewsListFragment(),
                    "review_list"
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as CustomerHomeActivity).changeToolbarName(
            getString(R.string.title_restaurants),
            isProfileMenuVisible = false,
            locationVisibility = false,isMenuVisibility = true
        )
        (requireActivity()as CustomerHomeActivity).infoMenu.isVisible=true
    }

    private fun setTabLayout() {
        val tabList= arrayListOf<String>()
        for (index in 0 until menuList.size)
        {
            if (index==0)
            {
                tabList.add(menuList[index].MenuCategory)
            }
            else{
                var isMenuFound=false
                for (ind in 0 until tabList.size)
                {
                    if (tabList[ind] == menuList[index].MenuCategory)
                    {
                       isMenuFound=true
                    }
                }
                if (!isMenuFound)
                {
                    tabList.add(menuList[index].MenuCategory)
                }

            }

        }
        for( index in 0 until tabList.size)
        {
            mBinding.layoutTabRdf.addTab(mBinding.layoutTabRdf.newTab().setText(tabList[index]));
        }

        mBinding.layoutTabRdf.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabsAdapter( childFragmentManager,requireActivity(), mBinding.layoutTabRdf.tabCount,menuList,tabList,true)
        adapter.setViewClickListener(this)
        mBinding.viewpagerRdf.adapter = adapter
        mBinding.viewpagerRdf.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mBinding.layoutTabRdf))
        mBinding.layoutTabRdf.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mBinding.viewpagerRdf.currentItem = tab.position
                val text=tab.text.toString()

                val updatedMenuList= arrayListOf<Menu>()
                for (index in 0 until menuList.size)
                {

                    if (menuList[index].MenuCategory==text)
                    {
                        updatedMenuList.add(menuList[index])
                    }

                }

                (activity as CustomerHomeActivity).mModel.updateMenuList(updatedMenuList)

                Timber.d(text.toString())
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        adapter.updateNewKey(false)
    }

    private fun setBlurBackgroundImage() {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.photo_1552566626_52f8b828add9)
        val blurredBitmap = BlurBuilder.blur(context, originalBitmap)
        mBinding.layoutBackgroungFrd.background = BitmapDrawable(resources, blurredBitmap)

    }
    private fun getRestaurantId()
    {
        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
            this.restaurantId=it
            if (AppGlobal.isInternetAvailable(requireActivity()))
            {
                getRestaurantDetail()
            }
            else{
                AppGlobal.snackBar(mBinding.layoutParentRdf,getString(R.string.err_no_internet),AppGlobal.SHORT)
            }
        })
    }

    /*
    * Method to open Restaurant Address in Google Map.
    * */
    private fun getLatLong() {
        gps = GPSTracker(context)
        // Check if GPS enabled
        if (gps!!.canGetLocation()) {
            val latitude: Double = gps!!.latitude
            val longitude: Double = gps!!.longitude
            AppGlobal.startGMapIntent(requireActivity(), restaurantDetailResponse.data.profile[0].Address, latitude, longitude)

            // \n is for new line
            //Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps!!.showSettingsAlert()
        }
    }

    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Login API Method
    * */
    private fun getRestaurantDetail(){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getRestaurantDetailResponse(restaurantId).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message=="Success")
            {
                menuList=it.data.menu
                if (menuList.size>0){
                    setTabLayout()
                    (activity as CustomerHomeActivity).mModel.updateRatingCount(it.data.profile[0].RatingCount)
                    (activity as CustomerHomeActivity).mModel.updateSumOfRating(it.data.profile[0].SumofRating)
                    AppGlobal.writeString(requireActivity(),AppGlobal.ownerId,it.data.profile[0].OwnerID)
                    AppGlobal.writeString(requireActivity(),AppGlobal.restaurantName,it.data.profile[0].RestaurantName)
                    AppGlobal.writeString(requireActivity(),AppGlobal.restaurantAddress,it.data.profile[0].Address)
                    AppGlobal.writeString(requireActivity(),AppGlobal.restaurantImage,it.data.profile[0].Image)
                    restaurantDetailResponse=it
                    setViews()
                    //(activity as CustomerHomeActivity).mModel.updateMenuList(menuList)
                }

            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        val mProfile=restaurantDetailResponse.data.profile[0]
        mBinding.txtRestaurantNameFrd.text=mProfile.RestaurantName
        mBinding.txtRestaurantRatingFrd.text=mProfile.Rating.toString()
        mBinding.rbRatingFrd.rating=mProfile.Rating.toFloat()
        mBinding.rbRatingFrd.numStars=5
        mBinding.txtTotalRestaurantRatingFrd.text="(${mProfile.RatingCount})"
        AppGlobal.loadImageIntoGlide(mProfile.Image,mBinding.imgRestaurantFrd,requireActivity())
        (requireActivity() as CustomerHomeActivity).mModel.updateSalesTax(mProfile.SalesTax.toDouble())
        (requireActivity() as CustomerHomeActivity).mModel.updateServiceCharges(mProfile.ServiceCharges.toDouble())
    }

    override fun onViewClicked(updatedMenuList: ArrayList<Menu>) {
        (requireActivity() as CustomerHomeActivity).mModel.updateMenuList(updatedMenuList)
    }



}