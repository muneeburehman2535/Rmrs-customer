package com.teletaleem.rmrs_customer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CategoriesAdapter
import com.teletaleem.rmrs_customer.adapters.DealsAdapter
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mBinding:FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        mBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCategoryAdapter()
        setRestaurantAdapter()
        setDealsAdapter()
    }

    /**************************************************************************************************************************/
    //                                          Recyclerview Adapters
    /**************************************************************************************************************************/

    private fun setCategoryAdapter() {
        val categoryAdapter=CategoriesAdapter(requireContext())
        mBinding.rvCategories.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        mBinding.rvCategories.adapter=categoryAdapter
    }

    private fun setRestaurantAdapter() {
        val restaurantAdapter=RestaurantAdapter(requireContext())
        mBinding.rvRestaurants.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        mBinding.rvRestaurants.adapter=restaurantAdapter
    }
    private fun setDealsAdapter() {
        val dealsAdapter=DealsAdapter(requireContext())
        mBinding.rvDeals.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        mBinding.rvDeals.adapter=dealsAdapter
    }
}