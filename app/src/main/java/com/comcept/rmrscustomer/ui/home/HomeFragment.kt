package com.comcept.rmrscustomer.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.kaopiz.kprogresshud.KProgressHUD
import com.comcept.rmrscustomer.R
import com.comcept.rmrscustomer.adapters.CategoriesAdapter
import com.comcept.rmrscustomer.adapters.DealsAdapter
import com.comcept.rmrscustomer.adapters.RestaurantAdapter
import com.comcept.rmrscustomer.data_class.cart.Cart
import com.comcept.rmrscustomer.data_class.home.category.Categories
import com.comcept.rmrscustomer.data_class.home.restaurants.Deals
import com.comcept.rmrscustomer.data_class.home.restaurants.Restaurants
import com.comcept.rmrscustomer.databinding.FragmentHomeBinding
import com.comcept.rmrscustomer.db.CustomerDatabase
import com.comcept.rmrscustomer.db.dataclass.Favourite
import com.comcept.rmrscustomer.ui.restauratntdetail.RestaurantDetailFragment
import com.comcept.rmrscustomer.ui.search.filter_search.FilterSearchFragment
import com.comcept.rmrscustomer.ui.search.simple_search.SimpleSearchFragment
import com.comcept.rmrscustomer.utilities.AppGlobal
import com.comcept.rmrscustomer.utilities.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() ,View.OnClickListener,RestaurantAdapter.AddToFavouriteListener,RestaurantAdapter.ViewClickListener,DealsListener{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var progressDialog: KProgressHUD

    private lateinit var categoryAdapter:CategoriesAdapter
    private lateinit var restaurantsAdapter:RestaurantAdapter
    private lateinit var dealsAdapter: DealsAdapter

    private lateinit var categoriesList:ArrayList<Categories>
    private lateinit var restaurantsList:ArrayList<Restaurants>
    private lateinit var dealsList:ArrayList<Deals>

    private lateinit var  databaseCreator: CustomerDatabase


    private  var restaurantId:String="0"
    private lateinit var mActivity:Activity
    private var mLatitude:Double? =33.6624748
    private var mLongitude:Double? = 73.0560211




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mBinding.homeViewModel=homeViewModel
        progressDialog=AppGlobal.setProgressDialog(requireActivity())

        mActivity=requireActivity()

        setCategoryAdapter()
        setRestaurantAdapter()
        setDealsAdapter()
        setClickListeners()
        getLocation()
        autoImageSlider()
        getCategoriesList()

        mBinding.searchBarHome.inputType = 0x00000000

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCartRecord()
    }
    private fun getLocation(){
        (activity as CustomerHomeActivity?)?.mModel?.mLatitude?.observe(viewLifecycleOwner, Observer {
            mLatitude=it
            getLongitude(mLatitude!!)
        })
    }
    private fun getLongitude(mLatitude: Double) {
        (activity as CustomerHomeActivity?)?.mModel?.mLongitude?.observe(viewLifecycleOwner, Observer {
            mLongitude=it
            getCategoriesList()
        })
    }

    override fun onResume() {
        super.onResume()
//        (activity as CustomerHomeActivity).setHomeToolbarTitle(
//            "",
//            false,
//            View.VISIBLE,
//            true,
//            false
//        )
        (requireActivity() as CustomerHomeActivity).getLuckyDrawPoints(AppGlobal.readString(requireActivity(), AppGlobal.customerId, "0"))
    }


    private fun setClickListeners() {
        mBinding.imgFilterHome.setOnClickListener(this)
        mBinding.cardSearchHome.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.img_filter_home -> {
                (activity as CustomerHomeActivity?)?.changeToolbarName(
                    getString(R.string.title_filter),
                    isProfileMenuVisible = false,
                    locationVisibility = false,
                    isMenuVisibility = false
                )
                (activity as CustomerHomeActivity?)?.loadNewFragment(
                    FilterSearchFragment(),
                    "filter_search"
                )
            }

            R.id.card_search_home -> {
                (activity as CustomerHomeActivity?)?.changeToolbarName(
                    getString(R.string.hint_search_food),
                    isProfileMenuVisible = false,
                    locationVisibility = false,
                    isMenuVisibility = false
                )
                (activity as CustomerHomeActivity?)?.loadNewFragment(
                    SimpleSearchFragment(),
                    "simple_search"
                )
            }
        }
    }

    /**************************************************************************************************************************/
    //                                          Auto Image Slider Method
    /**************************************************************************************************************************/


    fun autoImageSlider(){
        val imageSlider = mBinding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.imageone))
        imageList.add(SlideModel(R.drawable.imagetwo))
        imageList.add(SlideModel(R.drawable.imagethree))
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setCategoryAdapter() {
        categoriesList= arrayListOf()
        categoryAdapter = CategoriesAdapter(requireContext(), categoriesList)
        mBinding.rvCategories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvCategories.adapter = categoryAdapter
        setRecyclerViewListener(mBinding.rvCategories, "categories")

    }

    private fun setRestaurantAdapter() {
        restaurantsList= arrayListOf()
        restaurantsAdapter = RestaurantAdapter(requireContext(), restaurantsList)
        mBinding.rvRestaurants.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvRestaurants.adapter = restaurantsAdapter
        restaurantsAdapter.setAddToFavouriteListener(this)
        restaurantsAdapter.setViewClickListener(this)
        //setRecyclerViewListener(mBinding.rvRestaurants,"restaurants")
    }

    private fun setDealsAdapter() {
        dealsList= arrayListOf()
        dealsAdapter = DealsAdapter(requireContext(), dealsList)
        mBinding.rvDeals.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvDeals.adapter = dealsAdapter
        dealsAdapter.setDealClickListener(this)
        //setRecyclerViewListener(mBinding.rvDeals, "deals")
    }

    /*
    * Set Click listener on Recycler view
    * */
    private fun setRecyclerViewListener(recyclerView: RecyclerView, source: String)
    {
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                requireContext(),
                recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
                        if (source == "categories") {
                            for (index in 0 until categoriesList.size) {
                                categoriesList[index].isClicked = false
                            }
                            categoriesList[position].isClicked = true
                            categoryAdapter.updateCategoryList(categoriesList)

                            getRestaurantsList(categoriesList[position].CategoryID,
                                mLatitude!!, mLongitude!!
                            )
                        }

                    }

                })
        )
    }

    /**************************************************************************************************************************/
    //                                          Interfaces Section
    /**************************************************************************************************************************/

    override fun onAddToFavouriteClick(position: Int) {

        val favouriteLiveData=databaseCreator.favouriteDao.fetchFavouriteRecord(restaurantsList[position].RestaurantID)

        favouriteLiveData.observe(requireActivity(), Observer {

            val favouriteList = it as ArrayList<Favourite>

            if (favouriteList.size > 0) {
                deleteFavourite(position, favouriteLiveData)
            } else {
                addToFavourite(position, favouriteLiveData)
            }

        })

    }

    override fun onViewClicked(position: Int) {
        (activity as CustomerHomeActivity).mModel.updateRestaurantId(restaurantsList[position].RestaurantID)
        AppGlobal.writeString(
            requireActivity(),
            AppGlobal.restaurantId,
            restaurantsList[position].RestaurantID
        )
        (activity as CustomerHomeActivity).mModel.updateRestaurantName(restaurantsList[position].RestaurantName)
        (activity as CustomerHomeActivity).changeToolbarName(
            getString(R.string.title_restaurants),
            isProfileMenuVisible = false,
            locationVisibility = false,
            isMenuVisibility = true
        )
        (activity as CustomerHomeActivity).loadNewFragment(
            RestaurantDetailFragment(),
            "restaurant_detail"
        )



    }

    override fun onDealClickListener(position: Int) {
        (activity as CustomerHomeActivity).mModel.updateRestaurantId(dealsList[position].RestaurantID)
        AppGlobal.writeString(
            requireActivity(),
            AppGlobal.restaurantId,
            dealsList[position].RestaurantID
        )
        (activity as CustomerHomeActivity).mModel.updateRestaurantName(dealsList[position].name)
        (activity as CustomerHomeActivity).changeToolbarName(
            getString(R.string.title_restaurants),
            isProfileMenuVisible = false,
            locationVisibility = false,
            isMenuVisibility = true
        )
        (activity as CustomerHomeActivity).loadNewFragment(
            RestaurantDetailFragment(),
            "restaurant_detail"
        )
    }

    /**************************************************************************************************************************/
    //                                          Room Database Section
    /**************************************************************************************************************************/

    private fun checkRestaurantById(){
        if (activity!=null){
            val favouriteLiveData=databaseCreator.favouriteDao.findRestaurants()

            favouriteLiveData.observe(requireActivity(), Observer {

                val favouriteList = it as ArrayList<Favourite>

                if (favouriteList.size>0) {
                    for (index in 0 until restaurantsList.size) {
                        for (ind in 0 until favouriteList.size) {
                            if (restaurantsList[index].RestaurantID == favouriteList[ind].restaurant_id) {
                                restaurantsList[index].isFavourite = true
                                break
                            }
                        }
                    }

                }
                //(requireActivity() as CustomerHomeActivity).getNewCallerFragment()
                restaurantsAdapter.updateRestaurantsList(restaurantsList)

            })
        }

    }


    private fun addToFavourite(position: Int, favouriteLiveData: LiveData<MutableList<Favourite>>)
    {
        favouriteLiveData.removeObservers(requireActivity())
        val mFavourite=Favourite(
            restaurantsList[position].RestaurantID,
            restaurantsList[position].RestaurantName,
            restaurantsList[position].Address,
            restaurantsList[position].Rating,
            restaurantsList[position].RatingCount,
            restaurantsList[position].Image
        )
        homeViewModel.insertFavourite(mFavourite)
        restaurantsList[position].isFavourite=true
        restaurantsAdapter.updateRestaurantsList(restaurantsList)
    }

    private fun deleteFavourite(position: Int, favouriteLiveData: LiveData<MutableList<Favourite>>)
    {
        favouriteLiveData.removeObservers(requireActivity())
        val mFavourite=Favourite(
            restaurantsList[position].RestaurantID,
            restaurantsList[position].RestaurantName,
            restaurantsList[position].Address,
            restaurantsList[position].Rating,
            restaurantsList[position].RatingCount,
            restaurantsList[position].Image
        )
        homeViewModel.deleteFavouriteRecord(mFavourite)
        restaurantsList[position].isFavourite=false
        restaurantsAdapter.updateRestaurantsList(restaurantsList)

    }


    /*****************************************************************************************************************************/
    //                                      Room Database Section
    /*****************************************************************************************************************************/
    //                                    1:- Get Cart Record from Room Database
    //                                    2:- Update Cart in Room Database
    //                                    3:- Delete Item from Room Database
    /*****************************************************************************************************************************/

    private fun getCartRecord(){
        databaseCreator= CustomerDatabase.getInstance(requireActivity())
        val cartLiveData=databaseCreator.cartDao.fetch(AppGlobal.readString(requireActivity(),AppGlobal.restaurantId,"0"))

        cartLiveData.observe(requireActivity(), Observer {

           val cartList= it as ArrayList<Cart>
            (requireActivity() as CustomerHomeActivity).updateBottomNavigationCount(cartList.size)
            cartLiveData.removeObservers(requireActivity())


        })
    }

    /**************************************************************************************************************************/
    //                                          API's Calling Section
    /**************************************************************************************************************************/

    /*
   * Get Categories API Method
   * */
    private fun getCategoriesList(){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        activity?.let {
            homeViewModel.getCategoryResponse().observe(it, {
                //progressDialog.dismiss()

                if (it.Message == "Success") {
                    categoriesList = it.data.categories
                    if (categoriesList.size>0){

                        categoriesList[0].isClicked = true
                        categoryAdapter.updateCategoryList(categoriesList)
                        if (categoriesList.size > 0) {
                            getRestaurantsList(categoriesList[0].CategoryID, mLatitude!!, mLongitude!!)
                        }
                    }
                    else{
                        progressDialog.dismiss()
                    }



                } else {
                    AppGlobal.showDialog(
                        getString(R.string.title_alert),
                        it.data.description,
                        requireContext()
                    )
                }
            })
        }
    }

    /*
   * Get Restaurants Data API Method
   * */
    private fun getRestaurantsList(categoryID: String,Latitude:Double,Longitude:Double){

        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        homeViewModel.getRestaurantsResponse(categoryID,Latitude,Longitude).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message == "Success") {

                restaurantsList = it.data.restaurants
                dealsList = it.data.deals
                checkRestaurantById()
                dealsAdapter.updateList(dealsList)

            } else {
                //AppGlobal.showDialog(getString(R.string.title_alert), it.data.description, requireContext())
//                AppGlobal.showDialog(getString(R.string.title_alert),"Customer ", requireContext())
            }
        })
    }




}