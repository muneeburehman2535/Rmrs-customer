package com.teletaleem.rmrs_customer.ui.orderdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.OrderDetailIItemsAdapter
import com.teletaleem.rmrs_customer.data_class.login.Login
import com.teletaleem.rmrs_customer.databinding.ActivityOrderDetailBinding
import com.teletaleem.rmrs_customer.ui.home.CustomerHomeActivity
import com.teletaleem.rmrs_customer.ui.restauratntdetail.RestaurantDetailFragment
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener

class OrderDetailActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mBinding:ActivityOrderDetailBinding
    private lateinit var mViewModel: OrderDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_order_detail)
        mViewModel=ViewModelProvider(this).get(OrderDetailViewModel::class.java)
        mBinding.orderDetailViewModel=mViewModel
        setRestaurantAdapter()
        setClickListeners()
        //setContentView(R.layout.activity_order_detail)
    }

    private fun setClickListeners() {
        mBinding.imgBackOrderDetail.setOnClickListener(this)
    }

    private fun setRestaurantAdapter() {
        val orderDetailIItemsAdapter = OrderDetailIItemsAdapter(this)
        mBinding.rvItemsOrderDetail.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvItemsOrderDetail.adapter = orderDetailIItemsAdapter
        //setRecyclerViewListener(mBinding.rvItemsOrderDetail)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.img_back_order_detail->
            {
                this.finish()
            }
        }
    }





}