package com.teletaleem.rmrs_customer.ui.myorders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.kaopiz.kprogresshud.KProgressHUD
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.adapters.*
import com.teletaleem.rmrs_customer.data_class.myorders.currentorders.CurrentOrderDataClass
import com.teletaleem.rmrs_customer.data_class.myorders.pastorders.PastOrdersDataClass
import com.teletaleem.rmrs_customer.databinding.MyOrdersFragmentBinding
import com.teletaleem.rmrs_customer.ui.orderdetail.OrderDetailActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import com.teletaleem.rmrs_customer.utilities.RecyclerItemClickListener

class MyOrdersFragment : Fragment() {

    private lateinit var mBinding:MyOrdersFragmentBinding
    private lateinit var currentOrderList:ArrayList<CurrentOrderDataClass>
    private lateinit var pastOrderList:ArrayList<PastOrdersDataClass>

    private lateinit var currentOrderAdapter: CurrentOrdersAdapter
    private lateinit var pastOrderAdapter: PastOrdersAdapter

    private lateinit var progressDialog: KProgressHUD

    companion object {
        fun newInstance() = MyOrdersFragment()
    }

    private lateinit var viewModel: MyOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        mBinding=DataBindingUtil.inflate(inflater, R.layout.my_orders_fragment, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyOrdersViewModel::class.java)

        mBinding.myOrdersViewModel=viewModel
        progressDialog=AppGlobal.setProgressDialog(requireActivity())
        setCurrentOrdersAdapter()
        setPastOrdersAdapter()
        getCustomerId()
        
    }

    private fun getCustomerId() {
        getMyOrdersList(AppGlobal.readString(requireActivity(), AppGlobal.customerId, "0"))
    }

//    private fun setCurrentOrderList() {
//        currentOrderList= arrayListOf()
//
//        val currentOrderDataClass=CurrentOrderDataClass("Savour Foods - Blue Area","Zarda, Special Chicken Pulao, Coke","30 Min","720")
//        currentOrderList.add(currentOrderDataClass)
//
//    }
//
//    private fun setPastOrderList() {
//        pastOrderList= arrayListOf()
//
//        val pastOrdersDataClass=PastOrdersDataClass("La Terrazza - Centaurus","Jalapeno Grilled Chicken Burger, Coke","30-03-2021","15:30","560")
//        pastOrderList.add(pastOrdersDataClass)
//
//        val pastOrdersDataClass1=PastOrdersDataClass("Habibi Restaurant - Peshawar","Beef Chappal Kabab, Chicken Karai, Coke","15-02-2021","13:15","1020")
//        pastOrderList.add(pastOrdersDataClass1)
//
//    }


    private fun setCurrentOrdersAdapter() {
        currentOrderList= arrayListOf()
        currentOrderAdapter = CurrentOrdersAdapter(requireContext(), currentOrderList)
        mBinding.rvCurrentOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvCurrentOrdersFmo.adapter = currentOrderAdapter
        setRecyclerViewListener(mBinding.rvCurrentOrdersFmo,"new_order")

    }



    private fun setPastOrdersAdapter() {
        pastOrderList= arrayListOf()
        pastOrderAdapter = PastOrdersAdapter(requireContext(), pastOrderList)
        mBinding.rvPastOrdersFmo.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        mBinding.rvPastOrdersFmo.adapter = pastOrderAdapter
        setRecyclerViewListener(mBinding.rvPastOrdersFmo,"past_order")
    }

    /*
  * Set Click listener on Recycler view
  * */
    private fun setRecyclerViewListener(recyclerView: RecyclerView,orderType:String)
    {
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                requireContext(),
                recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(view: View?, position: Int) {
                        //activity?.startActivity(Intent(activity,OrderDetailActivity::class.java))
                    }

                    override fun onItemClick(view: View, position: Int) {
                        val intent=Intent(requireActivity(),OrderDetailActivity::class.java)
                        if (orderType=="new_order"){
                            intent.putExtra("order_id",currentOrderList[position].OrderID)
                        }
                        else{
                            intent.putExtra("order_id",pastOrderList[position].OrderID)
                        }
                        startActivity(intent)
                    }

                })
        )
    }

    /*
  * Get Restaurants Data API Method
  * */
    private fun getMyOrdersList(customerId: String){
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getMyOrdersResponse(customerId).observe(requireActivity(), {
            progressDialog.dismiss()
            if (it.Message == "Success") {

                currentOrderList = it.data.CurrentOrder
                pastOrderList = it.data.PastOrders
                currentOrderAdapter.updateList(currentOrderList)
                pastOrderAdapter.updateList(pastOrderList)

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