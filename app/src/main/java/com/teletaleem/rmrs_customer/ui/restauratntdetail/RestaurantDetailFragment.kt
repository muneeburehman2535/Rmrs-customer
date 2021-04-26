package com.teletaleem.rmrs_customer.ui.restauratntdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.TabsAdapter
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.Menu
import com.teletaleem.rmrs_customer.data_class.restaurantdetail.RestaurantDetailResponse
import com.teletaleem.rmrs_customer.databinding.RestaurantDetailFragmentBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.reservation.ReservationActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.BlurBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailFragment : Fragment() {
    private lateinit var mBinding:RestaurantDetailFragmentBinding
    private lateinit var menuList:ArrayList<Menu>

    companion object {
        fun newInstance() = RestaurantDetailFragment()
    }

    private lateinit var viewModel: RestaurantDetailViewModel
    private lateinit var progressDialog: KProgressHUD
    private lateinit var restaurantId:String

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
        //setTabLayout()
        getRestaurantId()

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
                for (ind in 0 until tabList.size)
                {
                    if (tabList[ind] != menuList[index].MenuCategory)
                    {
                        tabList.add(menuList[index].MenuCategory)
                        break
                    }
                }

            }

        }
        for( index in 0 until tabList.size)
        {
            mBinding.layoutTabRdf.addTab(mBinding.layoutTabRdf.newTab().setText(tabList[index]));
        }

        mBinding.layoutTabRdf.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabsAdapter( childFragmentManager,requireContext(), mBinding.layoutTabRdf.tabCount)
        mBinding.viewpagerRdf.adapter = adapter
        mBinding.viewpagerRdf.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mBinding.layoutTabRdf))
        mBinding.layoutTabRdf.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mBinding.viewpagerRdf.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setBlurBackgroundImage() {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.background)
        val blurredBitmap = BlurBuilder.blur(context, originalBitmap)
        mBinding.layoutBackgroungFrd.background = BitmapDrawable(resources, blurredBitmap)

    }
    private fun getRestaurantId()
    {
        (activity as CustomerHomeActivity?)?.mModel?.restaurantID?.observe(viewLifecycleOwner, Observer {
            this.restaurantId=it
            getRestaurantDetail()
        })
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
                setTabLayout()
                (activity as CustomerHomeActivity).mModel.updateRatingCount(it.data.profile[0].RatingCount)
                (activity as CustomerHomeActivity).mModel.updateSumOfRating(it.data.profile[0].SumofRating)
                AppGlobal.writeString(requireActivity(),AppGlobal.ownerId,it.data.profile[0].OwnerID)
                AppGlobal.writeString(requireActivity(),AppGlobal.restaurantName,it.data.profile[0].RestaurantName)
                AppGlobal.writeString(requireActivity(),AppGlobal.restaurantAddress,it.data.profile[0].Address)
                AppGlobal.writeString(requireActivity(),AppGlobal.restaurantImage,it.data.profile[0].Image)

                setViews(it)
                (activity as CustomerHomeActivity).mModel.updateMenuList(menuList)
            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setViews(restaurantDetailResponse: RestaurantDetailResponse) {
        val mProfile=restaurantDetailResponse.data.profile[0]
        mBinding.txtRestaurantNameFrd.text=mProfile.RestaurantName
        mBinding.txtRestaurantRatingFrd.text=mProfile.Rating.toString()
        mBinding.rbRatingFrd.rating=mProfile.Rating.toFloat()
        mBinding.rbRatingFrd.numStars=5
        mBinding.txtTotalRestaurantRatingFrd.text="(${mProfile.RatingCount})"
        AppGlobal.loadImageIntoGlide(mProfile.Image,mBinding.imgRestaurantFrd,requireActivity())
    }

}