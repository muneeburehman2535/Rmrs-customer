package com.teletaleem.rmrs_customer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CategoriesAdapter
import com.teletaleem.rmrs_customer.adapters.DealsAdapter
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.data_class.home.category.Categories
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Deals
import com.teletaleem.rmrs_customer.data_class.home.restaurants.Restaurants
import com.teletaleem.rmrs_customer.databinding.FragmentHomeBinding
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.ui.search.filter_search.FilterSearchFragment
import com.teletaleem.rmrs_customer.ui.search.simple_search.SimpleSearchFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener
import kotlin.collections.ArrayList

class HomeFragment : Fragment() ,View.OnClickListener{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var progressDialog: KProgressHUD

    private lateinit var categoryAdapter:CategoriesAdapter
    private lateinit var restaurantsAdapter:RestaurantAdapter
    private lateinit var dealsAdapter: DealsAdapter

    private lateinit var categoriesList:ArrayList<Categories>
    private lateinit var restaurantsList:ArrayList<Restaurants>
    private lateinit var dealsList:ArrayList<Deals>

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
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        mBinding.homeViewModel=homeViewModel
        progressDialog=AppGlobal.setProgressDialog(requireContext())

        setCategoryAdapter()
        setRestaurantAdapter()
        setDealsAdapter()
        setClickListeners()
        getCategoriesList()
        mBinding.searchBarHome.inputType = 0x00000000
    }

    private fun setClickListeners() {
        mBinding.imgFilterHome.setOnClickListener(this)
        mBinding.cardSearchHome.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.img_filter_home->
            {
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_filter))
                (context as CustomerHomeActivity?)?.loadNewFragment(
                        FilterSearchFragment(),
                        "filter_search"
                )
            }

            R.id.card_search_home->{
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.hint_search_food))
                (context as CustomerHomeActivity?)?.loadNewFragment(
                        SimpleSearchFragment(),
                        "simple_search"
                )
            }
        }
    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setCategoryAdapter() {
        categoriesList= arrayListOf()
        categoryAdapter = CategoriesAdapter(requireContext(),categoriesList)
        mBinding.rvCategories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvCategories.adapter = categoryAdapter
        setRecyclerViewListener(mBinding.rvCategories,"categories")

    }

    private fun setRestaurantAdapter() {
        restaurantsList= arrayListOf()
        restaurantsAdapter = RestaurantAdapter(requireContext(),restaurantsList)
        mBinding.rvRestaurants.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvRestaurants.adapter = restaurantsAdapter
        setRecyclerViewListener(mBinding.rvRestaurants,"restaurants")
    }

    private fun setDealsAdapter() {
        dealsList= arrayListOf()
        dealsAdapter = DealsAdapter(requireContext(),dealsList)
        mBinding.rvDeals.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvDeals.adapter = dealsAdapter
        setRecyclerViewListener(mBinding.rvDeals,"deals")
    }

    /*
    * Set Click listener on Recycler view
    * */
    private fun setRecyclerViewListener(recyclerView: RecyclerView,source:String)
    {
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                requireContext(),
                recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {

                    }

                    override fun onItemClick(view: View, position: Int) {
                        if (source=="categories"){
                            for (index in 0 until categoriesList.size)
                            {
                                categoriesList[index].isClicked=false
                            }
                            categoriesList[position].isClicked=true
                            categoryAdapter.updateCategoryList(categoriesList)

                            getRestaurantsList(categoriesList[position].CategoryID)
                        }
                        else{
                            (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants))
                            (context as CustomerHomeActivity?)?.loadNewFragment(
                                    RestaurantDetailFragment(),
                                    "restaurant_detail"
                            )
                        }

                    }

                })
        )
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
        homeViewModel.getCategoryResponse().observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message=="Success")
            {

                categoriesList=it.data.categories
                categoriesList[0].isClicked=true
                categoryAdapter.updateCategoryList(categoriesList)
                getRestaurantsList(categoriesList[0].CategoryID)

            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
            }
        })
    }

    /*
   * Get Restaurants Data API Method
   * */
    private fun getRestaurantsList(categoryID:String){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        homeViewModel.getRestaurantsResponse(categoryID).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message=="Success")
            {

                restaurantsList=it.data.restaurants
                dealsList=it.data.deals
                restaurantsAdapter.updateRestaurantsList(restaurantsList)
                dealsAdapter.updateList(dealsList)

            }
            else{
                AppGlobal.showDialog(getString(R.string.title_alert),it.data.description,requireContext())
            }
        })
    }
}