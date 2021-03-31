package com.teletaleem.rmrs_customer.ui.myorders

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.*
import com.teletaleem.rmrs_customer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.teletaleem.rmrs_customer.data_class.myorders.pastorders.PastOrdersDataClass
import com.teletaleem.rmrs_customer.databinding.MyOrdersFragmentBinding
import com.teletaleem.rmrs_customer.ui.orderdetail.OrderDetailActivity
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener

class MyOrdersFragment : Fragment() {

    private lateinit var mBinding:MyOrdersFragmentBinding
    private lateinit var currentOrderList:ArrayList<CurrentOrderDataClass>
    private lateinit var pastOrderList:ArrayList<PastOrdersDataClass>

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
        setCurrentOrderList()
        setPastOrderList()
        setCurrentOrdersAdapter()
        setPastOrdersAdapter()
    }

    private fun setCurrentOrderList() {
        currentOrderList= arrayListOf()

        val currentOrderDataClass=CurrentOrderDataClass("Savour Foods - Blue Area","Zarda, Special Chicken Pulao, Coke","30 Min","720")
        currentOrderList.add(currentOrderDataClass)

    }

    private fun setPastOrderList() {
        pastOrderList= arrayListOf()

        val pastOrdersDataClass=PastOrdersDataClass("La Terrazza - Centaurus","Jalapeno Grilled Chicken Burger, Coke","30-03-2021","15:30","560")
        pastOrderList.add(pastOrdersDataClass)

        val pastOrdersDataClass1=PastOrdersDataClass("Habibi Restaurant - Peshawar","Beef Chappal Kabab, Chicken Karai, Coke","15-02-2021","13:15","1020")
        pastOrderList.add(pastOrdersDataClass1)

    }


    private fun setCurrentOrdersAdapter() {
        val currentOrderAdapter = CurrentOrdersAdapter(requireContext(),currentOrderList)
        mBinding.rvCurrentOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvCurrentOrdersFmo.adapter = currentOrderAdapter
        setRecyclerViewListener(mBinding.rvCurrentOrdersFmo)

    }



    private fun setPastOrdersAdapter() {
        val pastOrderAdapter = PastOrdersAdapter(requireContext(),pastOrderList)
        mBinding.rvPastOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvPastOrdersFmo.adapter = pastOrderAdapter
        setRecyclerViewListener(mBinding.rvPastOrdersFmo)
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
                        startActivity(Intent(requireContext(),OrderDetailActivity::class.java))
                    }

                })
        )
    }

}