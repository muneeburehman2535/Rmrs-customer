package com.comcept.rmrscustomer.ui.search.simple_search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.SearchResultAdapter
import com.comcept.rmrscustomer.data_class.home.restaurants.Restaurants
import com.comcept.rmrscustomer.databinding.SimpleSearchFragmentBinding
import com.comcept.rmrscustomer.repository.Response
import com.comcept.rmrscustomer.ui.home.CustomerHomeActivity
import com.comcept.rmrscustomer.ui.restauratntdetail.RestaurantDetailFragment
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.comcept.rmrscustomer.utilities.GPSTracker
import com.comcept.rmrscustomer.utilities.RecyclerItemClickListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kaopiz.kprogresshud.KProgressHUD


class SimpleSearchFragment : Fragment() {
    private lateinit var mBinding:SimpleSearchFragmentBinding
    private lateinit var searchResultList:ArrayList<Restaurants>
    private lateinit var progressDialog: KProgressHUD
    private lateinit var searchResultAdapter:SearchResultAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mLatitude: Double? = 0.00
    private var mLongitude: Double? = 0.00
    var gps: GPSTracker? = null
    companion object {
        fun newInstance() = SimpleSearchFragment()
    }

    private lateinit var viewModel: SimpleSearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.simple_search_fragment, container, false)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchResultList= arrayListOf()

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog=AppGlobal.setProgressDialog(requireActivity())

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()

    }


    override fun onResume() {
        super.onResume()
        (activity as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.hint_search_food), isProfileMenuVisible = false, locationVisibility = true,isMenuVisibility = false)
    }


    /**************************************************************************************************************************/
    //                                          Get Location Section
    /**************************************************************************************************************************/

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location!=null) {

                    mLatitude = location.latitude.toString().toDouble()
                    mLongitude = location.longitude.toString().toDouble()


                }

                else{
                    //AppGlobal.showToast("Location Error",this)
                    gps = GPSTracker(requireActivity())

                    // Check if GPS enabled

                    // Check if GPS enabled
                    if (gps!!.canGetLocation()) {
                        val latitude = gps!!.latitude
                        val longitude = gps!!.longitude

                        mLatitude = latitude
                        mLongitude = longitude

                        // \n is for new line
                        Toast.makeText(
                            requireActivity(),
                            "Your Location is - \nLat: $latitude\nLong: $longitude", Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        gps!!.showSettingsAlert()
                    }
                }


            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SimpleSearchViewModel::class.java)
        mBinding.simpleSearchViewModel=viewModel
        setSearchResultAdapter()
        mBinding.edtxtSearchFragment?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                if (v.text.toString().length >= 3) {
                    if (AppGlobal.isInternetAvailable(requireActivity()))
                    {
                        getCurrentLocation()

                        if(mLatitude!! <=0.00 && mLongitude!!<=0.00){
                            AppGlobal.showToast("Please enable your location",requireActivity())
                        }
                        else{
                            getSearchResult(v.text.toString(),mLatitude!!, mLongitude!!)
                        }

                    }
                    else{
                        AppGlobal.snackBar(mBinding.layoutParentSsf,getString(R.string.err_no_internet),AppGlobal.SHORT)
                    }

                } else {
                    //no string
                }
            }
            false
        })

    }

    private fun setSearchResultAdapter() {

        searchResultAdapter = SearchResultAdapter(requireContext(), searchResultList)
        mBinding.rvSearch.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
        )
        mBinding.rvSearch.adapter = searchResultAdapter
        setRecyclerViewListener(mBinding.rvSearch)
    }

    /*
   * Set Click listener on Recycler view
   * */
    private fun setRecyclerViewListener(recyclerView: RecyclerView)
    {
        recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                        requireContext(),
                        recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
                        (activity as CustomerHomeActivity).mModel.updateRestaurantId(searchResultList[position].RestaurantID)
                        AppGlobal.writeString(requireActivity(),AppGlobal.restaurantId,searchResultList[position].RestaurantID)
                        (activity as CustomerHomeActivity).mModel.updateRestaurantName(searchResultList[position].RestaurantName)
                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants), isProfileMenuVisible = false,
                            locationVisibility = false,
                            isMenuVisibility = true)
                        (context as CustomerHomeActivity?)?.loadNewFragment(
                                RestaurantDetailFragment(),
                                "restaurant_detail"
                        )
                    }

                })
        )
    }


    //*************************************************************************************************************************************************/
    //                                                                API Calls Section:
    //************************************************************************************************************************************************/

    /*
    * Search API Method
    * */
    private fun getSearchResult(searchQuery: String,Latitude:Double,Longitude:Double){

        viewModel.getSearchResponse(searchQuery,Latitude,Longitude).observe(requireActivity()) {

            when (it) {


                is Response.Loading -> {
                    progressDialog.setLabel("Please Wait")
                    progressDialog.show()
                }

                is Response.Success -> {

                    it.data?.let {
                        progressDialog.dismiss()
                        if (it != null && it.Message == "Success") {
                            searchResultList = it.data.result
                            if (searchResultList.size > 0) {
                                mBinding.rvSearch.visibility = View.VISIBLE
                                mBinding.layoutNotFoundSsf.visibility = View.GONE
                                searchResultAdapter.updateSearchList(searchResultList)
                            } else {
                                mBinding.rvSearch.visibility = View.GONE
                                mBinding.layoutNotFoundSsf.visibility = View.VISIBLE
                            }

                        } else {
                            AppGlobal.showDialog(
                                getString(R.string.title_alert),
                                it.data.description,
                                requireActivity()
                            )
                        }
                    }
                }


                is Response.Error -> {
                    AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it.message.toString(),
                        requireActivity()
                    )
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()

                    }
                }

            }


        }
    }

}