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
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CategoriesAdapter
import com.teletaleem.rmrs_customer.adapters.DealsAdapter
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.databinding.FragmentHomeBinding
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.ui.search.filter_search.FilterSearchFragment
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener
import java.util.*

class HomeFragment : Fragment() ,View.OnClickListener{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryAdapter()
        setRestaurantAdapter()
        setDealsAdapter()
        setClickListeners()

    }

    private fun setClickListeners() {
        mBinding.imgFilterHome.setOnClickListener(this)
    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setCategoryAdapter() {
        val categoryAdapter = CategoriesAdapter(requireContext())
        mBinding.rvCategories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvCategories.adapter = categoryAdapter
        setRecyclerViewListener(mBinding.rvCategories)

    }

    private fun setRestaurantAdapter() {
        val restaurantAdapter = RestaurantAdapter(requireContext())
        mBinding.rvRestaurants.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvRestaurants.adapter = restaurantAdapter
        setRecyclerViewListener(mBinding.rvRestaurants)
    }

    private fun setDealsAdapter() {
        val dealsAdapter = DealsAdapter(requireContext())
        mBinding.rvDeals.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        mBinding.rvDeals.adapter = dealsAdapter
        setRecyclerViewListener(mBinding.rvDeals)
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
                        (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants))
                        (context as CustomerHomeActivity?)?.loadNewFragment(
                            RestaurantDetailFragment(),
                            "restaurant_detail"
                        )
                    }

                })
        )
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.img_filter_home->
            {
                (context as CustomerHomeActivity?)?.changeToolbarName(getString(R.string.title_restaurants))
                (context as CustomerHomeActivity?)?.loadNewFragment(
                    FilterSearchFragment(),
                    "filter_search"
                )
            }
        }
    }
}