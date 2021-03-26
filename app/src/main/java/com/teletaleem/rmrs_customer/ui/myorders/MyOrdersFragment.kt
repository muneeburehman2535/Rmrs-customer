package com.teletaleem.rmrs_customer.ui.myorders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.CategoriesAdapter
import com.teletaleem.rmrs_customer.adapters.CurrentOrdersAdapter
import com.teletaleem.rmrs_customer.adapters.PastOrdersAdapter
import com.teletaleem.rmrs_customer.adapters.RestaurantAdapter
import com.teletaleem.rmrs_customer.databinding.MyOrdersFragmentBinding

class MyOrdersFragment : Fragment() {

    private lateinit var mBinding:MyOrdersFragmentBinding

    companion object {
        fun newInstance() = MyOrdersFragment()
    }

    private lateinit var viewModel: MyOrdersViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        mBinding=DataBindingUtil.inflate(inflater,R.layout.my_orders_fragment,container,false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyOrdersViewModel::class.java)
       mBinding.myOrdersViewModel=viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentOrdersAdapter()
        setPastOrdersAdapter()
    }


    private fun setCurrentOrdersAdapter() {
        val currentOrderAdapter = CurrentOrdersAdapter(requireContext())
        mBinding.rvCurrentOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvCurrentOrdersFmo.adapter = currentOrderAdapter
        //setRecyclerViewListener(mBinding.rvCategories)

    }

    private fun setPastOrdersAdapter() {
        val pastOrderAdapter = PastOrdersAdapter(requireContext())
        mBinding.rvPastOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvPastOrdersFmo.adapter = pastOrderAdapter
        //setRecyclerViewListener(mBinding.rvRestaurants)
    }

}